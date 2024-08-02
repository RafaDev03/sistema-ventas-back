package sysventa.sistema_ventas_back.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sysventa.sistema_ventas_back.controller.dto.CategoriaDTO;
import sysventa.sistema_ventas_back.controller.dto.MensajeResponse;
import sysventa.sistema_ventas_back.controller.dto.ProductoDTO;
import sysventa.sistema_ventas_back.entities.Producto;
import sysventa.sistema_ventas_back.service.IProductoService;

@Controller
@RequestMapping("/api/producto")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<Producto> listProducto = productoService.findAll();

        List<ProductoDTO> productoDTOs = listProducto.stream()
                .map(producto -> new ProductoDTO(producto.getId(), producto.getNombre(), producto.getPrecio(),
                        producto.getStock(), producto.getImagen(),
                        new CategoriaDTO(producto.getCategoria().getId(), producto.getNombre())))
                .collect(Collectors.toList());
        MensajeResponse mensajeResponse = new MensajeResponse(true, "Lista de productos", productoDTOs);
        return ResponseEntity.ok().body(mensajeResponse);

    }
}
