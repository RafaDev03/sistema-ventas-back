package sysventa.sistema_ventas_back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sysventa.sistema_ventas_back.controller.dto.AuthRequestDTO;
import sysventa.sistema_ventas_back.controller.dto.AuthResponseDTO;
import sysventa.sistema_ventas_back.service.impl.UserDetailsServiceImpl;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            AuthResponseDTO authResponseDTO = this.userDetailsServiceImpl.loginUser(authRequestDTO);
            return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AuthResponseDTO(null, e.getMessage(), null, false),
                    HttpStatus.UNAUTHORIZED);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new AuthResponseDTO(null, e.getMessage(), null, false),
                    HttpStatus.UNAUTHORIZED);
        }
    }

}
