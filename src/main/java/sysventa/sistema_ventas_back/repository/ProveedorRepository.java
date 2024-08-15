package sysventa.sistema_ventas_back.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import sysventa.sistema_ventas_back.entities.Proveedor;

public interface ProveedorRepository extends CrudRepository<Proveedor, Long> {

    Optional<Proveedor> findByIdAndEstadoTrue(Long id);

    List<Proveedor> findByEstadoTrue();

}
