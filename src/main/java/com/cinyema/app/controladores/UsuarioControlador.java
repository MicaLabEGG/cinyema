package com.cinyema.app.controladores;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinyema.app.entidades.Usuario;
import com.cinyema.app.enumeraciones.Rol;
import com.cinyema.app.servicios.UsuarioServicio;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

	@Autowired
	UsuarioServicio usuarioServicio;

	@GetMapping("")
	public String mostrarUsuarios(ModelMap modelo) throws Exception {

		try {
			List<Usuario> listaUsuario = usuarioServicio.buscarUsuarios();
			modelo.addAttribute("listar", "Listar usuarios");
			modelo.addAttribute("usuario", listaUsuario);
			return "admin/vistas/usuario";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "admin/vistas/usuario";
		}
	}

	// required = false porque puede no venir el dato (en caso de login exitoso)
	@GetMapping("/login")
	public String login(ModelMap modelo, @RequestParam(required = false) String error) {

		if (error != null) {
			modelo.put("Error", "*Los datos son incorrectos");
		}

		return "/login";
	}

	@GetMapping("/registrar")
	public String guardar(ModelMap modelo) {
		modelo.addAttribute("registrar", "Registrar usuarios");
		return "admin/vistas/usuario";
	}

	@PostMapping("/registrar")
	public String guardarUsuario(ModelMap modelo, @RequestParam("nombre") String nombre,
			@RequestParam("mail") String mail, @RequestParam("nombreDeUsuario") String nombreDeUsuario,
			@RequestParam("contrasenia") String contrasenia, @RequestParam("fechaNacimiento") String fechaNacimiento)
			throws Exception {

		try {
			Usuario usuario = usuarioServicio.registroUsuario(nombre, mail, nombreDeUsuario, contrasenia,
					fechaNacimiento);
			modelo.put("usuario", usuario);
			return "redirect:/usuario";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			modelo.put("error", "Error al ingresar los datos del usuario");
			modelo.addAttribute("registrar", "Registrar usuarios");
			return "admin/vistas/usuario";
		}
	}

	@GetMapping("/editar/{id}")
	public String modificar(ModelMap modelo, @PathVariable Long id) throws Exception {
		try {
			Usuario usuario = usuarioServicio.obtenerUsuario(id);
			modelo.addAttribute("editar", "Editar usuarios");
			modelo.addAttribute("usuario", usuario);
			return "admin/vistas/usuario";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			modelo.put("error", "Falta algun dato");
			return "admin/vistas/usuario";
		}
		
	}

	@PostMapping("/editar/{id}")
	public String modificarUsuario(ModelMap modelo, @PathVariable Long id, @RequestParam String nombre,
			@RequestParam String mail, @RequestParam String nombreDeUsuario, @RequestParam String contrasenia,
			@RequestParam String fechaNacimiento) throws Exception {

		try {
			usuarioServicio.modificarUsuario(id, nombre, mail, nombreDeUsuario, contrasenia, fechaNacimiento);
			modelo.put("exito", "Modificacion exitosa");
			return "redirect:/usuario";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			modelo.put("error", "Falta algun dato al ingresar usuario");
			return "admin/vistas/usuario";
		}
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
		try {
			usuarioServicio.eliminarUsuario(id);
			return "redirect:/usuario";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/usuario";
		}

	}

}
