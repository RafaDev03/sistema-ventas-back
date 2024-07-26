package sysventa.sistema_ventas_back.persistence.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import sysventa.sistema_ventas_back.entities.Producto;
import sysventa.sistema_ventas_back.persistence.IProductoDAO;

public class ProductoDAOImpl implements IProductoDAO {

    @Override
    public List<Producto> findAll() {
        return null;
    }

    @Override
    public List<Producto> findByPreceInRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return null;
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return null;
    }

    @Override
    public void save(Producto producto) {

    }

    @Override
    public void deleteById(Long id) {

    }

}
