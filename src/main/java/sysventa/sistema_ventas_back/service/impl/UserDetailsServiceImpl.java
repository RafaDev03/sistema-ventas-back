package sysventa.sistema_ventas_back.service.impl;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sysventa.sistema_ventas_back.controller.dto.AuthRequestDTO;
import sysventa.sistema_ventas_back.controller.dto.AuthResponseDTO;
import sysventa.sistema_ventas_back.entities.Usuario;
import sysventa.sistema_ventas_back.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

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
        return null;
    }

}
