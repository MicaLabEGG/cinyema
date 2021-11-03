package com.cinyema.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinyema.app.entidades.Usuario;
import com.cinyema.app.servicios.UsuarioServicio;

@Controller
@RequestMapping({"/cinyema","/"})
public class MainControlador {
	
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@GetMapping()
	public String index() {			
		return "admin/vistas/indexAdmin";
	}
	
	@GetMapping("/login")
	public String login() {
		return "visitante/vistas/login";
	}
	
	@GetMapping("/registrar")
	public String guardar(ModelMap modelo) {
		modelo.addAttribute("registrar", "Registrar usuarios");
		return "visitante/vistas/registroUsuario";
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
}
