package sysventa.sistema_ventas_back.service.impl;

import java.util.ArrayList;
import java.util.Optional;
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

import sysventa.sistema_ventas_back.controller.dto.AuthRequestDTO;
import sysventa.sistema_ventas_back.controller.dto.AuthResponseDTO;
import sysventa.sistema_ventas_back.entities.Usuario;
import sysventa.sistema_ventas_back.repository.UsuarioRepository;
import sysventa.sistema_ventas_back.util.JwtUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

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
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(username, "Usuario legeado correctamente", accessToken,
                true);
        return authResponseDTO;
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

}
