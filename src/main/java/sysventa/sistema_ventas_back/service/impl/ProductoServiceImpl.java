package sysventa.sistema_ventas_back.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sysventa.sistema_ventas_back.entities.Producto;
import sysventa.sistema_ventas_back.persistence.IProductoDAO;
import sysventa.sistema_ventas_back.service.IProductoService;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    IProductoDAO productoDAO;

    @Override
    public List<Producto> findAll() {
        return productoDAO.findAll();
    }

    @Override
    public List<Producto> findByPreceInRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productoDAO.findByPreceInRange(minPrice, maxPrice);
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return productoDAO.findById(id);
    }

    @Override
    public void save(Producto producto) {
        productoDAO.save(producto);
    }

    @Override
    public void deleteById(Long id) {
        productoDAO.deleteById(id);
    }

}
