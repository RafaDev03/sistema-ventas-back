package sysventa.sistema_ventas_back.controller.dto;

import java.util.List;

import lombok.Builder;
import sysventa.sistema_ventas_back.entities.Producto;

@Builder
public record CategoriaDTO(Long id, String nombre, List<Producto> productoList) {

}
