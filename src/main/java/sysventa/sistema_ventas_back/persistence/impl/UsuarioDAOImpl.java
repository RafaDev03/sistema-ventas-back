package sysventa.sistema_ventas_back.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import sysventa.sistema_ventas_back.entities.Usuario;
import sysventa.sistema_ventas_back.persistence.IUsuarioDAO;
import sysventa.sistema_ventas_back.repository.UsuarioRepository;

public class UsuarioDAOImpl implements IUsuarioDAO {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

}
