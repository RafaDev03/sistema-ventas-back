package sysventa.sistema_ventas_back.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javax.management.RuntimeMBeanException;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.DecodedJWT;

import sysventa.sistema_ventas_back.controller.dto.AuthRequestDTO;
import sysventa.sistema_ventas_back.controller.dto.AuthResponseDTO;
import sysventa.sistema_ventas_back.entities.RefreshToken;
import sysventa.sistema_ventas_back.entities.Usuario;
import sysventa.sistema_ventas_back.repository.RefreshTokenRepository;
import sysventa.sistema_ventas_back.repository.UsuarioRepository;
import sysventa.sistema_ventas_back.util.JwtUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarOptional = this.usuarioRepository.findUsuarioByUsername(username);
        if (!usuarOptional.isPresent()) {
            throw new UsernameNotFoundException("El usuario " + username + " no existe");
        }
        Usuario usuario = usuarOptional.get();

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        usuario.getRoleList()
                .forEach(rol -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(rol.getERole().name()))));
        usuario.getRoleList().stream()
                .flatMap(role -> role.getPermisosList().stream())
                .forEach(permiso -> authorityList.add(new SimpleGrantedAuthority(permiso.getNombre())));
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnable(),
                usuario.isCredentialsNonExpired(), usuario.isAcountNonExpired(), usuario.isAcountNonLocked(),
                authorityList);
    }

    public AuthResponseDTO loginUser(AuthRequestDTO authRequestDTO) {
        String username = authRequestDTO.username();
        String password = authRequestDTO.password();
        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);
        String refeshToken = jwtUtils.createRefreshToken(authRequestDTO.username());
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(username, "Usuario legeado correctamente", accessToken,
                refeshToken,
                true);
        return authResponseDTO;
    }

    public AuthResponseDTO refreshToken(String refreshToken) {
        // Valida y decodifica el refresh token
        DecodedJWT decodedJWT = jwtUtils.validateToken(refreshToken);
        String username = jwtUtils.extracUsername(decodedJWT);

        // Verifica el token de refresh en la base de datos
        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token inválido"));

        if (storedToken.getExpiryDate().before(new Date())) {
            throw new RuntimeException("Refresh token ha expirado");
        }
        UserDetails userDetails = loadUserByUsername(username);
        // Creando el nuevo access token
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(), userDetails.getAuthorities());
        String newAccessToken = jwtUtils.createToken(authentication);

        String newRefreshToken = jwtUtils.createRefreshToken(username);

        // Actualiza el refresh token en la base de datos

        return new AuthResponseDTO(username, "Token renovado correctamente", newAccessToken, newRefreshToken, true);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("El usuario o password son invalidos");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("El password es invalido");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(),
                userDetails.getAuthorities());

    }

    public void logout(String username) {
        jwtUtils.deleteRefreshToken(username);
    }

}
