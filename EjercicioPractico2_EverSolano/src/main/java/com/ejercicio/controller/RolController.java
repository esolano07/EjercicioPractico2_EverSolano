
package com.ejercicio.controller;

import com.ejercicio.domain.Rol;
import com.ejercicio.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ever1
 */

@Controller
@RequestMapping("/rol")
public class RolController {
    
    @Autowired
    private RolService rolService;

    @GetMapping
    public String listarRoles(Model model) {
        model.addAttribute("roles", rolService.getAllRoles());
        return "rol/lista"; 
    }

    @GetMapping("/nuevo")
    public String nuevoRol(Model model) {
        model.addAttribute("rol", new Rol());
        return "rol/formNuevo";
    }

    @GetMapping("/editar/{id}")
    public String editarRol(@PathVariable Long id, Model model) {
        Rol rol = rolService.getRolById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + id));
        model.addAttribute("rol", rol);
        return "rol/formNuevo";
    }

    @PostMapping("/guardar")
    public String guardarRol(@ModelAttribute("rol") Rol rol) {
        rolService.saveRol(rol);
        return "redirect:/rol";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarRol(@PathVariable Long id) {
        rolService.deleteRol(id);
        return "redirect:/rol";
    }
}
