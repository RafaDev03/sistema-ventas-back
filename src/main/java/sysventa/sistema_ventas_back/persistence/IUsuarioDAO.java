package sysventa.sistema_ventas_back.persistence;

import java.util.List;
import java.util.Optional;

import sysventa.sistema_ventas_back.entities.Usuario;

public interface IUsuarioDAO {
    Optional<Usuario> findById(Long id);

    List<Usuario> findAll();

    void save(Usuario usuario);

    void deleteById(Long id);
}
