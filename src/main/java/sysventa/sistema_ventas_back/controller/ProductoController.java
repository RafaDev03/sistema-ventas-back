package sysventa.sistema_ventas_back.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import sysventa.sistema_ventas_back.controller.dto.CategoriaDTO;
import sysventa.sistema_ventas_back.controller.dto.MensajeResponse;
import sysventa.sistema_ventas_back.controller.dto.ProductoDTO;
import sysventa.sistema_ventas_back.entities.Categoria;
import sysventa.sistema_ventas_back.entities.Producto;
import sysventa.sistema_ventas_back.service.ICategoriaService;
import sysventa.sistema_ventas_back.service.IProductoService;

@Controller
@RequestMapping("/api/producto")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @Autowired
    private ICategoriaService categoriaService;

    // @GetMapping("/findAll")
    // public ResponseEntity<?> findAll() {
    // List<Producto> listProducto = productoService.findAll();

    // List<ProductoDTO> productoDTOs = listProducto.stream()
    // .map(producto -> new ProductoDTO(producto.getId(), producto.getNombre(),
    // producto.getPrecio(),
    // producto.getStock(), producto.getImagen(), producto.getCategoria().getId()))
    // .collect(Collectors.toList());
    // MensajeResponse mensajeResponse = new MensajeResponse(true, "Lista de
    // productos", productoDTOs);
    // return ResponseEntity.ok().body(mensajeResponse);

    // }

    // @PostMapping("/save")
    // public ResponseEntity<?> createPructo(@RequestBody ProductoDTO productoDTO) {

    // Optional<Categoria> categoriaOptional =
    // categoriaService.findById(productoDTO.categoriaId());
    // if (!categoriaOptional.isPresent()) {
    // throw new RuntimeException("Categoría no encontrada");
    // }

    // Producto producto = new Producto()
    // .builder()
    // .nombre(productoDTO.nombre())
    // .precio(productoDTO.precio())
    // .stock(productoDTO.stock())
    // .imagen(productoDTO.imagen())
    // .categoria(categoriaOptional.get())
    // .build();

    // return null;
    // }

}
