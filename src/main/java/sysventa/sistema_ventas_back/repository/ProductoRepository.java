package sysventa.sistema_ventas_back.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sysventa.sistema_ventas_back.entities.Producto;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN ?1 AND ?2")
    List<Producto> findProductoByPrecioInRange(BigDecimal minPrice, BigDecimal maxPrice);

    // Query Methods
    List<Producto> findProductoByPrecioBetween(BigDecimal minPrice, BigDecimal maxPrice);

}
