package sysventa.sistema_ventas_back.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sysventa.sistema_ventas_back.entities.Proveedor;
import sysventa.sistema_ventas_back.persistence.IProveedorDAO;
import sysventa.sistema_ventas_back.repository.ProveedorRepository;

@Component
public class ProveedorDAOImpl implements IProveedorDAO {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public List<Proveedor> findAll() {
        return (List<Proveedor>) proveedorRepository.findByEstadoTrue();
    }

    @Override
    public Optional<Proveedor> findById(Long id) {
        return proveedorRepository.findByIdAndEstadoTrue(id);
    }

    @Override
    public void save(Proveedor proveedor) {
        proveedorRepository.save(proveedor);
    }

    @Override
    public void deleteById(Long id) {
        proveedorRepository.deleteById(id);
    }

    @Override
    public List<Proveedor> findAllById(List<Long> ids) {
        return (List<Proveedor>) proveedorRepository.findAllById(ids);
    }

}
