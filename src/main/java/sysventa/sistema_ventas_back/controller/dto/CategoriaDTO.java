package sysventa.sistema_ventas_back.controller.dto;

import lombok.Builder;

@Builder
public record CategoriaDTO(Long id, String nombre) {

}
