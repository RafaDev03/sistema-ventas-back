package sysventa.sistema_ventas_back.controller.dto;

import java.util.Date;
import java.util.List;

public record ProductoDTO(Long id, String nombre, Double precio, Integer stock, String imagen, Long categoriaId,
                List<Long> proveedoresId, Long usuarioAltaId, Date fechaCreacion, Date fechaModi) {

}
