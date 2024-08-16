package sysventa.sistema_ventas_back.controller;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.auth0.jwt.exceptions.JWTVerificationException;

import sysventa.sistema_ventas_back.controller.dto.CategoriaDTO;
import sysventa.sistema_ventas_back.controller.dto.MensajeResponse;
import sysventa.sistema_ventas_back.controller.dto.ProductoGetDTO;
import sysventa.sistema_ventas_back.controller.dto.ProductoPostDTO;
import sysventa.sistema_ventas_back.controller.dto.UsuarioDTO;
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
    public ResponseEntity<MensajeResponse> findAll() {
        try {
            List<Producto> listaProducto = productoService.findAll();
            List<ProductoGetDTO> listaProductoDTO = listaProducto.stream()
                    .map(producto -> {
                        UsuarioDTO usuarioAlta = producto.getUsuarioAltaId() != null ? new UsuarioDTO(
                                producto.getUsuarioAltaId().getId(), producto.getUsuarioAltaId().getUsername()) : null;
                        UsuarioDTO usuarioModi = producto.getUsuarioModiId() != null ? new UsuarioDTO(
                                producto.getUsuarioModiId().getId(), producto.getUsuarioModiId().getUsername()) : null;
                        return new ProductoGetDTO(producto.getId(),
                                producto.getNombre(), producto.getPrecio(), producto.getStock(), producto.getImagen(),
                                new CategoriaDTO(producto.getCategoria().getId(), producto.getCategoria().getNombre()),
                                null,
                                usuarioAlta,
                                usuarioModi,
                                producto.getFechaCreacion(),
                                producto.getFechaMofi());
                    })
                    .collect(Collectors.toList());

            MensajeResponse mensajeResponse = new MensajeResponse(true, "Lista de Productos", listaProductoDTO);
            return ResponseEntity.ok(mensajeResponse);
        } catch (JWTVerificationException e) {
            return ResponseEntity.badRequest().body(new MensajeResponse(false, e.getMessage(), null));
        }
    }

    @PostMapping("/save")
    public ResponseEntity<MensajeResponse> guardarProducto(@RequestBody ProductoPostDTO productoPostDTO) {
        try {
            if (productoPostDTO != null) {
                // Obteniendo el usuario del token

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String username = authentication.getName();

                Optional<Usuario> usuarioAlta = usuarioRepository.findUsuarioByUsername(username);

                // Buscando la categoría
                Optional<Categoria> categoriaOpt = categoriaService.findById(productoPostDTO.categoriaId());
                if (!categoriaOpt.isPresent()) {
                    return ResponseEntity.badRequest()
                            .body(new MensajeResponse(false, "Categoría no encontrada", null));
                }
                // Buscando los proveeedores|
                List<Proveedor> proveedores = proveedorService.findAllById(productoPostDTO.proveedorIds());

                Producto producto = Producto
                        .builder()
                        .estado(true)
                        .nombre(productoPostDTO.nombre())
                        .precio(productoPostDTO.precio())
                        .stock(productoPostDTO.stock())
                        .imagen(productoPostDTO.imagen())
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MensajeResponse(false, e.getMessage(), null));
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MensajeResponse> deleteProducto(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest()
                    .body(new MensajeResponse(false, "ID del producto no proporcionado", null));
        }
        try {
            Optional<Producto> productoOpt = productoService.findById(id);
            if (productoOpt.isPresent()) {
                Producto producto = productoOpt.get();
                producto.setEstado(false);
                productoService.save(producto);
                MensajeResponse mensajeResponse = new MensajeResponse(true, "Producto Eliminado", null);
                return ResponseEntity.ok().body(mensajeResponse);
            } else {
                MensajeResponse mensajeResponse = new MensajeResponse(false,
                        "El registro que quiere eliminar no se encuentra disponible", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensajeResponse);
            }

        } catch (Exception e) {
        }

        return null;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MensajeResponse> updateProducto(@PathVariable Long id,
            @RequestBody ProductoPostDTO productoPostDTO) {
        // Validación del ID
        if (id == null) {
            return ResponseEntity.badRequest()
                    .body(new MensajeResponse(false, "No se está proporcionando el ID", null));
        }

        // Buscar producto
        Optional<Producto> productoOpt = productoService.findById(id);
        if (productoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new MensajeResponse(false, "El producto no está disponible", null));
        }

        // Validación de la categoría
        if (productoPostDTO.categoriaId() == null) {
            return ResponseEntity.badRequest()
                    .body(new MensajeResponse(false, "El ID de la categoría no puede ser nulo", null));
        }
        Optional<Categoria> categoriaOpt = categoriaService.findById(productoPostDTO.categoriaId());
        if (categoriaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new MensajeResponse(false, "Categoría no encontrada", null));
        }

        // Validación de proveedores
        if (productoPostDTO.proveedorIds() == null || productoPostDTO.proveedorIds().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new MensajeResponse(false, "Debe proporcionar al menos un proveedor", null));
        }
        List<Long> proveedorIds = productoPostDTO.proveedorIds();
        if (proveedorIds.stream().anyMatch(Objects::isNull)) {
            return ResponseEntity.badRequest()
                    .body(new MensajeResponse(false, "La lista de IDs de proveedores contiene un valor nulo", null));
        }
        List<Proveedor> listaProveedor = proveedorService.findAllById(proveedorIds);
        if (listaProveedor.size() != proveedorIds.size()) {
            return ResponseEntity.badRequest()
                    .body(new MensajeResponse(false, "Uno o más proveedores no fueron encontrados", null));
        }

        // Obtener usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Usuario> usuarioOpt = usuarioRepository.findUsuarioByUsername(username);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new MensajeResponse(false, "Usuario no encontrado", null));
        }

        // Actualización del producto
        Producto producto = productoOpt.get();
        producto.setNombre(productoPostDTO.nombre());
        producto.setPrecio(productoPostDTO.precio());
        producto.setStock(productoPostDTO.stock());
        producto.setImagen(productoPostDTO.imagen());
        producto.setCategoria(categoriaOpt.get());
        producto.setProvedores(listaProveedor);
        producto.setUsuarioModiId(usuarioOpt.get());
        producto.setFechaMofi(new Date());

        productoService.save(producto);

        MensajeResponse mensajeResponse = new MensajeResponse(true, "Registro Actualizado", productoPostDTO);
        return ResponseEntity.ok(mensajeResponse);
    }

}
