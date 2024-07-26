package sysventa.sistema_ventas_back.persistence;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import sysventa.sistema_ventas_back.entities.Producto;

public interface IProductoDAO {
    List<Producto> findAll();

    List<Producto> findByPreceInRange(BigDecimal minPrice, BigDecimal maxPrice);

    Optional<Producto> findById(Long id);

    void save(Producto producto);

    void deleteById(Long id);
}
