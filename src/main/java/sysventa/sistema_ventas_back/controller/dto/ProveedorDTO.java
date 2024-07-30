package sysventa.sistema_ventas_back.controller.dto;

import lombok.Builder;

@Builder
public record ProveedorDTO(
        Long id, String nombre,
        String correo, String ruc,
        String direccion, String telefono,
        String imagen,
        boolean estado) {

}
