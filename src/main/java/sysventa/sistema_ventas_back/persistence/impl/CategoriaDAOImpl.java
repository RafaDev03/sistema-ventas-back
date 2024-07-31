package sysventa.sistema_ventas_back.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sysventa.sistema_ventas_back.entities.Categoria;
import sysventa.sistema_ventas_back.persistence.ICategoriaDAO;
import sysventa.sistema_ventas_back.repository.CategoriaRepository;

@Component
public class CategoriaDAOImpl implements ICategoriaDAO {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> findAll() {
        return (List<Categoria>) categoriaRepository.findByAndEstadoTrue();
    }

    @Override
    public Optional<Categoria> findById(Long id) {
        return categoriaRepository.findByIdAndEstadoTrue(id);
    }

    @Override
    public void save(Categoria categoria) {
        categoriaRepository.save(categoria);
    }

    @Override
    public void deleteById(Long id) {
        categoriaRepository.deleteById(id);
    }

}
