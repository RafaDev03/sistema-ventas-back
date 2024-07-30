package sysventa.sistema_ventas_back.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return ResponseEntity.ok(categoriaDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> createCategoria() {
        List<Categoria> categoriaList = categoriaService.findAll();

        List<CategoriaDTO> categoriaDTOs = categoriaList.stream()
                .map(categoria -> new CategoriaDTO(categoria.getId(), categoria.getNombre()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoriaDTOs);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveCategoria(@RequestBody CategoriaDTO categoriaDTO) throws URISyntaxException {
        if (categoriaDTO.nombre().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Categoria nuevaCategoria = Categoria.builder()
                .nombre(categoriaDTO.nombre())
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
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable Long id) {
        if (id != null) {
            categoriaService.deleteById(id);
            MensajeResponse mensajeResponse = new MensajeResponse(true, "Registro eliminado", null);
            return ResponseEntity.ok(mensajeResponse);
        }
        return ResponseEntity.badRequest().build();
    }

}
