
package com.ejercicio.service;

import com.ejercicio.domain.Rol;
import com.ejercicio.domain.Rol;
import com.ejercicio.domain.Usuario;
import com.ejercicio.repository.RolRepository;
import com.ejercicio.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ever1
 */

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private RolRepository rolRepository;
    
    public List<Usuario> getAllUsuarios(){
        return usuarioRepository.findAll();
    }
    
    public Optional<Usuario> getUsuarioByCedula(long id) {
        return usuarioRepository.findById(id);
    }
    
    public Usuario saveUsuario(Usuario usuario, Long rolId){
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("El rol con ID "+ rolId + "no existe."));
        usuario.setRol(rol);
        return usuarioRepository.save(usuario);
    }
    
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
    
    public List<Usuario> buscarPorRol(Optional<Rol> rol) {
        return usuarioRepository.findByRol(rol);
    }

    public List<Usuario> buscarPorNombreOEmail(String texto) {
        return usuarioRepository
                .findByNombreContainingIgnoreCaseOrEmailContainingIgnoreCase(texto, texto);
    }

    public long contarUsuariosActivos() {
        return usuarioRepository.countByActivo(true);
    }

    public long contarUsuariosInactivos() {
        return usuarioRepository.countByActivo(false);
    }
}
