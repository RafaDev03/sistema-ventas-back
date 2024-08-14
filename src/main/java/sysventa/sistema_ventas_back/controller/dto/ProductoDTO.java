package sysventa.sistema_ventas_back.controller.dto;

import java.util.Date;
import java.util.List;

public record ProductoDTO(Long id, String nombre, Double precio, Integer stock, String imagen, String categoria,
        List<String> proveedores, String usuarioAlta, Date fechaCreacion, Date fechaModi) {

}
