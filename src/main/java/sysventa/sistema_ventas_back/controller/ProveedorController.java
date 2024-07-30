package sysventa.sistema_ventas_back.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sysventa.sistema_ventas_back.controller.dto.MensajeResponse;
import sysventa.sistema_ventas_back.controller.dto.ProveedorDTO;
import sysventa.sistema_ventas_back.entities.Proveedor;
import sysventa.sistema_ventas_back.service.IProveedorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/proveedor")
public class ProveedorController {

    @Autowired
    private IProveedorService proveedorService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<Proveedor> listaProveedor = proveedorService.findAll();
        List<ProveedorDTO> listaProveedorDTO = listaProveedor.stream()
                .map(proveedor -> new ProveedorDTO(proveedor.getId(), proveedor.getNombre(), proveedor.getCorreo(),
                        proveedor.getRuc(), proveedor.getDireccion(), proveedor.getTelefono(), proveedor.getImagen(),
                        proveedor.isEstado()))
                .collect(Collectors.toList());
        MensajeResponse mensajeResponse = new MensajeResponse(true, "Lista de proveedores", listaProveedorDTO);
        return ResponseEntity.ok(mensajeResponse);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Proveedor> proveedorOptional = proveedorService.findById(id);
        if (proveedorOptional.isPresent()) {
            Proveedor proveedor = proveedorOptional.get();
            ProveedorDTO proveedorDTO = new ProveedorDTO(
                    proveedor.getId(),
                    proveedor.getNombre(),
                    proveedor.getCorreo(),
                    proveedor.getRuc(),
                    proveedor.getDireccion(),
                    proveedor.getTelefono(),
                    proveedor.getImagen(),
                    proveedor.isEstado());

            MensajeResponse mensajeResponse = new MensajeResponse(true, "Proveedor encontrado", proveedorDTO);
            return ResponseEntity.ok(mensajeResponse);

        }
        MensajeResponse mensajeResponse = new MensajeResponse(false, "El proveedor no se encuentra disponible",
                null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensajeResponse);

    }

    @PostMapping("/save")
    public ResponseEntity<MensajeResponse> createProveedor(@RequestBody ProveedorDTO proveedorDTO) {
        if (proveedorDTO == null) {
            return ResponseEntity.badRequest()
                    .body(new MensajeResponse(false, "ProveedorDTO no puede ser nulo", null));
        }
        Proveedor proveedor = Proveedor.builder()
                .nombre(proveedorDTO.nombre())
                .correo(proveedorDTO.correo())
                .direccion(proveedorDTO.direccion())
                .estado(true)
                .imagen(proveedorDTO.imagen())
                .telefono(proveedorDTO.telefono())
                .ruc(proveedorDTO.ruc())
                .build();

        proveedorService.save(proveedor);

        MensajeResponse mensajeResponse = new MensajeResponse(true, "Proveedor creado correctamente", proveedor);
        URI location = URI.create(String.format("/api/proveedor/%d", proveedor.getId()));
        return ResponseEntity.created(location).body(mensajeResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProveedor(@PathVariable Long id, @RequestBody ProveedorDTO proveedorDTO) {
        Optional<Proveedor> proveedorOptional = proveedorService.findById(id);

        if (!proveedorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MensajeResponse(false, "El proveedor que desea actualiaar no se encuentra disponible", null));
        }
        Proveedor proveedor = proveedorOptional.get();
        proveedor.setNombre(proveedorDTO.nombre());
        proveedor.setCorreo(proveedorDTO.correo());
        proveedor.setDireccion(proveedorDTO.direccion());
        proveedor.setEstado(true);
        proveedor.setImagen(proveedorDTO.imagen());
        proveedor.setRuc(proveedorDTO.ruc());
        proveedor.setTelefono(proveedorDTO.telefono());
        proveedorService.save(proveedor);
        MensajeResponse mensajeResponse = new MensajeResponse(true, "Proveedor actualizado correctamente", proveedor);
        return ResponseEntity.ok().body(mensajeResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProveedor(@PathVariable Long id) {
        Optional<Proveedor> proveedorOptional = proveedorService.findById(id);
        if (!proveedorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MensajeResponse(false, "El proveedor que quiere eliminar no se encuentra disponible", null));
        }
        Proveedor proveedor = proveedorOptional.get();
        proveedor.setEstado(false);
        proveedorService.save(proveedor);
        MensajeResponse mensajeResponse = new MensajeResponse(true, "Proveedor eliminado", null);
        return ResponseEntity.ok().body(mensajeResponse);
    }

}
