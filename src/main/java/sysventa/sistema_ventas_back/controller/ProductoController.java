package sysventa.sistema_ventas_back.controller;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.auth0.jwt.exceptions.JWTVerificationException;

import sysventa.sistema_ventas_back.controller.dto.MensajeResponse;
import sysventa.sistema_ventas_back.controller.dto.ProductoDTO;
import sysventa.sistema_ventas_back.entities.Categoria;
import sysventa.sistema_ventas_back.entities.Producto;
import sysventa.sistema_ventas_back.entities.Proveedor;
import sysventa.sistema_ventas_back.entities.Usuario;
import sysventa.sistema_ventas_back.repository.UsuarioRepository;
import sysventa.sistema_ventas_back.service.ICategoriaService;
import sysventa.sistema_ventas_back.service.IProductoService;
import sysventa.sistema_ventas_back.service.IProveedorService;

@Controller
@RequestMapping("/api/producto")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private IProveedorService proveedorService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        try {
            List<Producto> listProducto = productoService.findAll();
            List<ProductoDTO> productoDTOs = listProducto.stream()
                    .map(producto -> {

                        List<Long> proveedorIds = producto.getProvedores().stream()
                                .map(proveedor -> proveedor.getId()).collect(Collectors.toList());
                        return new ProductoDTO(producto.getId(), producto.getNombre(), producto.getPrecio(),
                                producto.getStock(), producto.getImagen(), producto.getCategoria().getId(),
                                proveedorIds,
                                null, producto.getFechaCreacion(),
                                producto.getFechaMofi());
                    })
                    .collect(Collectors.toList());

            MensajeResponse mensajeResponse = new MensajeResponse(true, "Lista de Productos", productoDTOs);
            return ResponseEntity.ok(mensajeResponse);
        } catch (JWTVerificationException e) {
            return ResponseEntity.badRequest().body(new MensajeResponse(false, e.getMessage(), null));
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> guardarProducto(@RequestBody ProductoDTO productoDTO) {

        try {
            if (productoDTO != null) {
                // Obteniendo el usuario del token

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String username = authentication.getName();

                Optional<Usuario> usuarioAlta = usuarioRepository.findUsuarioByUsername(username);

                // Buscando la categoría
                Optional<Categoria> categoriaOpt = categoriaService.findById(productoDTO.categoriaId());
                // Buscando los proveeedores|
                List<Proveedor> proveedores = proveedorService.findAllById(productoDTO.proveedoresId());

                Producto producto = Producto
                        .builder()
                        .estado(true)
                        .nombre(productoDTO.nombre())
                        .precio(productoDTO.precio())
                        .stock(productoDTO.stock())
                        .imagen(productoDTO.imagen())
                        .categoria(categoriaOpt.get())
                        .provedores(proveedores)
                        .usuarioAltaId(usuarioAlta.get())
                        .fechaCreacion(new Date())
                        .fechaMofi(new Date())
                        .usuarioModiId(usuarioAlta.get())
                        .build(); // Buscando el usuario
                productoService.save(producto);
                MensajeResponse mensajeResponse = new MensajeResponse(true, "Producto Creado", null);
                return ResponseEntity.created(new URI("/api/producto/save")).body(mensajeResponse);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {

        }

        return null;
    }

}
