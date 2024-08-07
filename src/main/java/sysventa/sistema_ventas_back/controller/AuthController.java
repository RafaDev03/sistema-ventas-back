package sysventa.sistema_ventas_back.controller;

import java.util.Map;

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
            return new ResponseEntity<>(new AuthResponseDTO(null, e.getMessage(), null, null, false),
                    HttpStatus.UNAUTHORIZED);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new AuthResponseDTO(null, e.getMessage(), null, null, false),
                    HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        try {
            AuthResponseDTO authResponseDTO = this.userDetailsServiceImpl.refreshToken(refreshToken);
            return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            AuthResponseDTO authResponseDTO = new AuthResponseDTO(null, e.getMessage(), null, null, false);
            return new ResponseEntity<>(authResponseDTO, HttpStatus.UNAUTHORIZED);
        }

    }

}
