package sysventa.sistema_ventas_back.persistence;

import java.util.List;
import java.util.Optional;

import sysventa.sistema_ventas_back.entities.Categoria;

public interface ICategoriaDAO {
    List<Categoria> findAll();

    Optional<Categoria> findById(Long id);

    void save(Categoria Categoria);

    void deleteById(Long id);
}
