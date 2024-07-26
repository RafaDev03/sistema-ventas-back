package sysventa.sistema_ventas_back.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sysventa.sistema_ventas_back.entities.Proveedor;
import sysventa.sistema_ventas_back.persistence.IProveedorDAO;
import sysventa.sistema_ventas_back.service.IProveedorService;

@Service
public class ProveedorServiceImpl implements IProveedorService {

    @Autowired
    private IProveedorDAO proveedorDAO;

    @Override
    public List<Proveedor> findAll() {
        return proveedorDAO.findAll();
    }

    @Override
    public Optional<Proveedor> findById(Long id) {
        return proveedorDAO.findById(id);
    }

    @Override
    public void save(Proveedor proveedor) {
        proveedorDAO.save(proveedor);
    }

    @Override
    public void deleteById(Long id) {
        proveedorDAO.deleteById(id);
    }

}
