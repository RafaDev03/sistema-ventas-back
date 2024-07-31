package sysventa.sistema_ventas_back.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sysventa.sistema_ventas_back.entities.Categoria;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, Long> {
    Optional<Categoria> findByIdAndEstadoTrue(Long id);

    List<Categoria> findByAndEstadoTrue();
}
