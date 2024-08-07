package sysventa.sistema_ventas_back.persistence.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sysventa.sistema_ventas_back.entities.Producto;
import sysventa.sistema_ventas_back.persistence.IProductoDAO;
import sysventa.sistema_ventas_back.repository.ProductoRepository;

@Component
public class ProductoDAOImpl implements IProductoDAO {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> findAll() {
        return (List<Producto>) productoRepository.findAll();
    }

    @Override
    public List<Producto> findByPreceInRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productoRepository.findProductoByPrecioInRange(minPrice, maxPrice);
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public void save(Producto producto) {
        productoRepository.save(producto);
    }

    @Override
    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }

}
