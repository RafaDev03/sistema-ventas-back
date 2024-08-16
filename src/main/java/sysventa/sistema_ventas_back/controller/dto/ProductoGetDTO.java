package sysventa.sistema_ventas_back.controller.dto;

import java.util.Date;
import java.util.List;

public record ProductoGetDTO(Long id, String nombre, Double precio, Integer stock, String imagen,
                CategoriaDTO categoriaDTO,
                List<ProveedorDTO> proveedores, UsuarioDTO usuarioAlta, UsuarioDTO usuarioModi, Date fechaCreacion,
                Date fechaModi) {

}
