package com.cinyema.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
			modelo.addAttribute("usuario", usuarioServicio.buscarUsuarios());
			return "vistas/usuario";
		} catch (Exception e) {
			e.printStackTrace();
			return "vistas/usuario";
		}
	}

	@GetMapping("/login")
	public String login(ModelMap modelo, @RequestParam(required = false) String error) {
		if (error != null) {
			modelo.put("Error", "Error al ingresar datos");
		}
		return "/login";
	}

	@GetMapping("/registrar")
	public String registrar(ModelMap modelo) {
		modelo.addAttribute("registrar", "Registrar usuarios");
		return "vistas/usuario";
	}

	@PostMapping("/registrar")
	public String registrar(ModelMap modelo, @RequestParam("nombre") String nombre, @RequestParam("mail") String mail,
			@RequestParam("nombreDeUsuario") String nombreDeUsuario, @RequestParam("contrasenia") String contrasenia,
			@RequestParam("fechaNacimiento") String fechaNacimiento) throws Exception {
		try {
			modelo.put("usuario",
					usuarioServicio.registroUsuario(nombre, mail, nombreDeUsuario, contrasenia, fechaNacimiento));
			return "redirect:/usuario";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("Error", "Error al ingresar datos");
			return "vistas/usuario";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/editar/{id}")
	public String editar(ModelMap modelo, @PathVariable Long id) throws Exception {
		try {
			modelo.addAttribute("editar", "Editar usuarios");
			modelo.addAttribute("usuario", usuarioServicio.obtenerUsuario(id));
			return "vistas/usuario";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", "Error al ingresar datos");
			return "vistas/usuario";
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/editar/{id}")
	public String editar(ModelMap modelo, @PathVariable Long id, @RequestParam String nombre, @RequestParam String mail,
			@RequestParam String nombreDeUsuario, @RequestParam String contrasenia,
			@RequestParam String fechaNacimiento) throws Exception {
		try {
			usuarioServicio.modificarUsuario(id, nombre, mail, nombreDeUsuario, contrasenia, fechaNacimiento);
			return "redirect:/usuario";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", "Error al ingresar datos");
			return "redirect:/usuario";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
		try {
			usuarioServicio.eliminarUsuario(id);
			return "redirect:/usuario";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/usuario";
		}

	}

}
