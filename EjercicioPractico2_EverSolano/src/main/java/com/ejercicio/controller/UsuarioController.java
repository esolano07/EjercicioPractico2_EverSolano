package com.ejercicio.controller;

import com.ejercicio.domain.Rol;
import com.ejercicio.domain.Usuario;
import com.ejercicio.service.RolService;
import com.ejercicio.service.UsuarioService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ever1
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());
        return "usuario/lista";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable("id") Long id, Model model) {
        Usuario usuario = usuarioService.getUsuarioByCedula(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrada: " + id));
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.getAllRoles());
        return "usuario/formNuevo";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolService.getAllRoles());
        return "usuario/formNuevo";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario, @RequestParam("rolId") Long rolId) {
        usuarioService.saveUsuario(usuario, rolId);
        return "redirect:/usuario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return "redirect:/usuario";
    }

    @GetMapping("/consultas")
    public String consultas(Model model,
                        @RequestParam(required = false) Long idRol) {

    List<Usuario> usuarios;

    if (idRol != null) {
        Optional<Rol> rol = rolService.getRolById(idRol);
        usuarios = usuarioService.buscarPorRol(rol);
    }
    else {
        usuarios = usuarioService.getAllUsuarios();
    }

    model.addAttribute("usuarios", usuarios);
    model.addAttribute("roles", rolService.getAllRoles());
    model.addAttribute("activos", usuarioService.contarUsuariosActivos());
    model.addAttribute("inactivos", usuarioService.contarUsuariosInactivos());

    return "usuario/consultas";
}
    
}
