package sysventa.sistema_ventas_back.service;

import java.util.List;
import java.util.Optional;

import sysventa.sistema_ventas_back.entities.Categoria;

public interface ICategoriaService {
    List<Categoria> findAll();

    Optional<Categoria> findById(Long id);

    void save(Categoria Categoria);

    void deleteById(Long id);
}
