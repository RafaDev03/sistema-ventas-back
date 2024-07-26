package sysventa.sistema_ventas_back.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sysventa.sistema_ventas_back.entities.Producto;

@Repository
public interface IProductoRepository extends CrudRepository<Producto, Long> {

}
