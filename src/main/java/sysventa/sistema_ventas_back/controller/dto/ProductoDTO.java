package sysventa.sistema_ventas_back.controller.dto;

public record ProductoDTO(Long id, String nombre, Double precio, Integer stock, String imagen, CategoriaDTO categoria) {

}
