package sysventa.sistema_ventas_back.persistence;

import java.util.List;
import java.util.Optional;

import sysventa.sistema_ventas_back.entities.Proveedor;

public interface IProveedorDAO {

    List<Proveedor> findAll();

    Optional<Proveedor> findById(Long id);

    void save(Proveedor proveedor);

    void deleteById(Long id);

    List<Proveedor> findAllById(List<Long> ids);
}
