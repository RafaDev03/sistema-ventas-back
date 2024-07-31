package sysventa.sistema_ventas_back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sysventa.sistema_ventas_back.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findUsuarioByUsername(String username);
}
