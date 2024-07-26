package sysventa.sistema_ventas_back.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sysventa.sistema_ventas_back.entities.Categoria;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, Long> {

}
