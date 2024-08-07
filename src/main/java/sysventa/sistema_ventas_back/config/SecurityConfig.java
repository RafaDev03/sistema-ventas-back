package sysventa.sistema_ventas_back.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import sysventa.sistema_ventas_back.config.filter.JwtTokenValitador;
import sysventa.sistema_ventas_back.util.JwtUtils;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {

                    // Api para la autenticacion
                    http.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll();

                    // Api proveedores
                    http.requestMatchers(HttpMethod.GET, "/api/proveedor/**").hasAnyRole("ADMIN", "USER");
                    http.requestMatchers(HttpMethod.POST, "/api/proveedor/**").hasAnyRole("ADMIN", "USER");
                    http.requestMatchers(HttpMethod.PUT, "/api/proveedor/**").hasAnyRole("ADMIN", "USER");
                    http.requestMatchers(HttpMethod.DELETE, "/api/proveedor/**").hasRole("ADMIN");

                    // Api productos
                    http.requestMatchers(HttpMethod.GET, "/api/producto/**").hasAnyRole("ADMIN", "USER");
                    http.requestMatchers(HttpMethod.POST, "/api/producto/**").hasAnyRole("ADMIN", "USER");
                    http.requestMatchers(HttpMethod.PUT, "/api/producto/**").hasAnyRole("ADMIN", "USER");
                    http.requestMatchers(HttpMethod.DELETE, "/api/producto/**").hasRole("ADMIN");

                    // Api categorías
                    http.requestMatchers(HttpMethod.GET, "/api/categoria/**").hasAnyRole("ADMIN", "USER");
                    http.requestMatchers(HttpMethod.POST, "/api/categoria/**").hasAnyRole("ADMIN", "USER");
                    http.requestMatchers(HttpMethod.PUT, "/api/categoria/**").hasAnyRole("ADMIN", "USER");
                    http.requestMatchers(HttpMethod.DELETE, "/api/categoria/**").hasRole("ADMIN");

                    http.anyRequest().denyAll();
                })
                .addFilterBefore(new JwtTokenValitador(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return null;
    }

}
