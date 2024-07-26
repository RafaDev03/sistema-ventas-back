package sysventa.sistema_ventas_back.repository;

import org.springframework.data.repository.CrudRepository;

import sysventa.sistema_ventas_back.entities.Proveedor;

public interface IProveedorRepository extends CrudRepository<Proveedor, Long> {

}
