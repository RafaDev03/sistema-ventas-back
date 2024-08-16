package sysventa.sistema_ventas_back.controller.dto;

import java.sql.Date;
import java.util.List;

public record ProductoPostDTO(Long id, String nombre, Double precio, Integer stock, String imagen,
        Long categoriaId, List<Long> proveedorIds, Long usuarioId,
        Date fechaCreacion, Date fechaModi) {

}
