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
@RequestMapping({ "/cinyema", "/" })
public class MainControlador {

	@Autowired
	private UsuarioServicio usuarioServicio;

	@GetMapping()
	public String index() {
		return "index";
	}

	@GetMapping("/login")
	public String login(ModelMap modelo, @RequestParam(required = false) String error,
			@RequestParam(required = false) String nombreDeUsuario, @RequestParam(required = false) String logout) {
		if (error != null) {
			modelo.addAttribute("error", "El usuario o la contraseña son inválidos. Vuelva a intentar");
		}
		if (nombreDeUsuario != null) {
			modelo.addAttribute("nombreDeUsuario", nombreDeUsuario);
		}
		return "vistas/login";
	}

	@GetMapping("/registrar")
	public String registrar(ModelMap modelo) {
		modelo.addAttribute("registrar", "Registrar Usuario");
		return "vistas/registroUsuario";
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
			modelo.put("error", "Error al ingresar los datos");
			return "vistas/usuario";
		}
	}
}
