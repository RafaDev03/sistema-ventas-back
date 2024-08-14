package sysventa.sistema_ventas_back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sysventa.sistema_ventas_back.entities.RefreshToken;
import sysventa.sistema_ventas_back.entities.Usuario;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUsername(String username);

    void deleteByUsername(String username);

    void deleteByUser(Usuario user);
}