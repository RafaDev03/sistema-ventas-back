package sysventa.sistema_ventas_back.controller.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "username", "message", "jwt", "status" })
public record AuthResponseDTO(String username, String mensaje, String jwt, boolean status) {

}
