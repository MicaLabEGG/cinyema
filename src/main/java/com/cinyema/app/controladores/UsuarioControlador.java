package com.cinyema.app.controladores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinyema.app.Utility;
import com.cinyema.app.entidades.Usuario;
import com.cinyema.app.servicios.UsuarioServicio;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

	@Autowired
	private UsuarioServicio usuarioServicio;

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("")
	public String listar(ModelMap modelo) throws Exception {
		try {
			modelo.addAttribute("listar", "Lista Usuarios");
			modelo.addAttribute("usuario", usuarioServicio.listar());
			return "vistas/admin/usuario";
		} catch (Exception e) {
			e.printStackTrace();
			return "vistas/admin/usuario";
		}
	}

	@GetMapping("/login")
	public String login(ModelMap modelo, @RequestParam(required = false) String error) {
		return (error != null) ? (String) modelo.put("Error", "Error al ingresar datos") : "/login";
	}

	@GetMapping("/registrar")
	public String registrar(ModelMap modelo) {
		try {
		    modelo.addAttribute("registrar", "Registrar usuarios");
		    modelo.addAttribute("usuario", usuarioServicio.registrarVacio());
		    return "vistas/admin/usuario";
		}catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			return "vistas/admin/usuario";
		}
	}
	
	@PostMapping("/registrar")
	public String registrar(ModelMap modelo, Usuario usuario) throws Exception {
		try {
			modelo.put("usuario", usuarioServicio.registrar(usuario));
			return "redirect:/usuario";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("registrar", "Registrar Usuario");
			modelo.addAttribute("usuario", usuario);
			modelo.put("error", e.getMessage());
			return "redirect:/usuario";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/editar/{idUsuario}")
	public String editar(ModelMap modelo, @PathVariable Long idUsuario) throws Exception {
		try {
			modelo.addAttribute("editar", "Editar usuarios");
			modelo.addAttribute("usuario", usuarioServicio.obtenerPorId(idUsuario));
			return "vistas/admin/usuario";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Usuario");
			modelo.addAttribute("usuario", usuarioServicio.obtenerPorId(idUsuario));
			modelo.put("error", e.getMessage());
			return "vistas/admin/usuario";
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/editar/{idUsuario}")
	public String editar(ModelMap modelo, Usuario usuario) throws Exception {
		try {
			usuarioServicio.editar(usuario);
			return "redirect:/usuario";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Usuario");
			modelo.addAttribute("usuario", usuario);
			modelo.put("error", e.getMessage());
			return "redirect:/usuario";
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/baja/{idUsuario}")
	public String darBaja(@PathVariable Long idUsuario) {
		try {
		     usuarioServicio.darBaja(idUsuario);
		     return "redirect:/usuario";
	    }catch(Exception e) {
	    	e.printStackTrace();
		    return "redirect:/usuario"; 
	    }
    }
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/alta/{idUsuario}")
	public String darAlta(@PathVariable Long idUsuario) {
		try {
		     usuarioServicio.darAlta(idUsuario);
		     return "redirect:/usuario";
	    }catch(Exception e) {
	    	e.printStackTrace();
		    return "redirect:/usuario"; 
	    }
    }

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/eliminar/{idUsuario}")
	public String eliminar(@PathVariable Long idUsuario) {
		try {
			usuarioServicio.eliminar(idUsuario);
			return "redirect:/usuario";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/usuario";
		}

	}

}
