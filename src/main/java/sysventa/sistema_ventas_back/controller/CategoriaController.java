package sysventa.sistema_ventas_back.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTVerificationException;

import sysventa.sistema_ventas_back.controller.dto.CategoriaDTO;
import sysventa.sistema_ventas_back.controller.dto.MensajeResponse;
import sysventa.sistema_ventas_back.entities.Categoria;
import sysventa.sistema_ventas_back.service.ICategoriaService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/categoria/")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Categoria> categoriaOptional = categoriaService.findById(id);
        if (categoriaOptional.isPresent()) {
            Categoria categoria = categoriaOptional.get();
            CategoriaDTO categoriaDTO = CategoriaDTO.builder()
                    .id(categoria.getId())
                    .nombre(categoria.getNombre())
                    .build();
            MensajeResponse mensajeResponse = new MensajeResponse(true, "Categoria encontrada", categoriaDTO);
            return ResponseEntity.ok(mensajeResponse);
        }
        MensajeResponse mensajeResponse = new MensajeResponse(false, "La categoría no se encuentra disponible", null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensajeResponse);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> createCategoria() {
        try {
            List<Categoria> categoriaList = categoriaService.findAll();

            List<CategoriaDTO> categoriaDTOs = categoriaList.stream()
                    .map(categoria -> new CategoriaDTO(categoria.getId(), categoria.getNombre()))
                    .collect(Collectors.toList());
            MensajeResponse mensajeResponse = new MensajeResponse(true, "Lista de categorias", categoriaDTOs);
            return ResponseEntity.ok(mensajeResponse);
        } catch (JWTVerificationException e) {
            return ResponseEntity.badRequest().body(new MensajeResponse(false, e.getMessage(), null));
        }

    }

    @PostMapping("/save")
    public ResponseEntity<?> saveCategoria(@RequestBody CategoriaDTO categoriaDTO) throws URISyntaxException {
        if (categoriaDTO.nombre().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Categoria nuevaCategoria = Categoria.builder()
                .nombre(categoriaDTO.nombre())
                .estado(true)
                .build();
        categoriaService.save(nuevaCategoria);

        CategoriaDTO nuevaCategoriaDTO = new CategoriaDTO(nuevaCategoria.getId(), nuevaCategoria.getNombre());

        MensajeResponse mensajeResponse = new MensajeResponse(true, "Categoría Creada", nuevaCategoriaDTO);

        return ResponseEntity.created(new URI("/api/categoria/save"))
                .body(mensajeResponse);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        Optional<Categoria> categoriaOptional = categoriaService.findById(id);
        if (categoriaOptional.isPresent()) {
            Categoria categoria = categoriaOptional.get();
            categoria.setNombre(categoriaDTO.nombre());
            categoriaService.save(categoria);
            MensajeResponse mensajeResponse = new MensajeResponse(true, "Registro Actualizado", categoria);
            return ResponseEntity.ok(mensajeResponse);
        }
        MensajeResponse mensajeResponse = new MensajeResponse(true,
                "El registro que quiere actualizar no se encuentra disponible", null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensajeResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable Long id) {
        if (id == null) {
            MensajeResponse mensajeResponse = new MensajeResponse(false,
                    "Agregar el id  del registro en el parametro", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensajeResponse);
        }
        Optional<Categoria> categorOptional = categoriaService.findById(id);
        if (categorOptional.isPresent()) {
            Categoria categoria = categorOptional.get();
            categoria.setEstado(false);
            categoriaService.save(categoria);
            MensajeResponse mensajeResponse = new MensajeResponse(true, "Registro eliminado", null);
            return ResponseEntity.ok(mensajeResponse);
        }

        MensajeResponse mensajeResponse = new MensajeResponse(false,
                "El registro que quiere eliminar no se encuentra disponible", null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensajeResponse);
    }

}
