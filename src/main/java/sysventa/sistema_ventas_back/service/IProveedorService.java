package sysventa.sistema_ventas_back.service;

import java.util.List;
import java.util.Optional;

import sysventa.sistema_ventas_back.entities.Proveedor;

public interface IProveedorService {
    List<Proveedor> findAll();

    Optional<Proveedor> findById(Long id);

    void save(Proveedor proveedor);

    void deleteById(Long id);
}
