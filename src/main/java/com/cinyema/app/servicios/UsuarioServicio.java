package com.cinyema.app.servicios;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.cinyema.app.entidades.Usuario;
import com.cinyema.app.enumeraciones.Rol;
import com.cinyema.app.repositorios.UsuarioRepositorio;

import net.bytebuddy.utility.RandomString;

@Service
public class UsuarioServicio implements UserDetailsService, ServicioBase<Usuario> {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Usuario registrar(Usuario usuario) throws Exception {
		validar(usuario);
//		validarMayoriaEdad(usuario);
		usuario.setAlta(false);
		usuario.setRol(Rol.USUARIO);
		BCryptPasswordEncoder encriptada = new BCryptPasswordEncoder();
		usuario.setContrasenia(encriptada.encode(usuario.getContrasenia()));
		
		String codigoRandom = RandomString.make(64);
		usuario.setCodigoVerificacion(codigoRandom);
		
		return usuarioRepositorio.save(usuario);
	}
	
	public void enviarMailVerificacion(Usuario usuario, String siteURL) throws UnsupportedEncodingException, MessagingException{
		String tema = "[VERIFICAR CUENTA DE CINYEMA]";
		String remitente = "Equipo de Cinyema";
		String contenido = "<p>Querido "+usuario.getNombre()+"</p>";
		contenido += "<p>Por favor, haga click en el siguiente enlace para verificar su registro a Cinyema:</p>";
		String verificarURL = siteURL + "/verify?code=" + usuario.getCodigoVerificacion();
		contenido += "<h3><a href=\"" + verificarURL + "\">VERIFICAR CUENTA ✔</a></h3>";
		contenido += "<p>Gracias! <br>Equipo de Cinyema</p>";
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("cinyema@gmail.com", remitente);
		helper.setTo(usuario.getMail());
		helper.setSubject(tema);
		helper.setText(contenido, true);
		
		mailSender.send(message);
	}
	
	public boolean validarCodigo(String codigoVerificacion) {
		Usuario usuario = usuarioRepositorio.buscarPorCodigoVerificacion(codigoVerificacion);
		if (usuario.getAlta()) {
			return false;
		}else {
			usuario.setAlta(true);
			usuarioRepositorio.save(usuario);
			return true;
		}
	}

	@Transactional
	public Usuario registrarVacio() {
		return new Usuario();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Usuario editar(Usuario usuario) throws Exception {
		validar(usuario);
//		validarMayoriaEdad(usuario);
		usuario.setAlta(true);
		usuario.setRol(Rol.USUARIO);
		BCryptPasswordEncoder encriptada = new BCryptPasswordEncoder();
		usuario.setContrasenia(encriptada.encode(usuario.getContrasenia()));
		return usuarioRepositorio.save(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> listar() {
		return usuarioRepositorio.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Usuario obtenerPorId(Long id) throws Exception {
		Optional<Usuario> result = usuarioRepositorio.findById(id);
		if (!result.isPresent()) {
			throw new Exception("No se encontró");
		} else {
			Usuario usuario = result.get();
			return usuario;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Usuario obtenerUsuarioPorNombre(String nombreDeUsuario) throws Exception {
		Usuario result = usuarioRepositorio.buscarPorNombreDeUsuario(nombreDeUsuario);
		if (result == null) {
			throw new Exception("No se encontró");
		} else {
			return result;
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Usuario darBaja(Long idUsuario) {
		Usuario usuario = usuarioRepositorio.getById(idUsuario);
		usuario.setAlta(false);
		return usuario;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Usuario darAlta(Long idUsuario) {
		Usuario usuario = usuarioRepositorio.getById(idUsuario);
		usuario.setAlta(true);
		return usuario;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminar(Long idUsuario) {
		usuarioRepositorio.deleteById(idUsuario);
	}
	
	public long totalUsuario() throws Exception {
		return usuarioRepositorio.count();
	}
	
	public int totalAlta() throws Exception {
		double total = usuarioRepositorio.totalAlta() * 100 / totalUsuario();
		return (int) Math.round(total);
	}
	
	public int totalBaja() throws Exception {
		return 100 - totalAlta();
	}

	@Transactional(readOnly = true)
	public List<Usuario> usuariosActivos() {

		return usuarioRepositorio.buscarUsuarioActivos();
	}

	@Transactional(readOnly = true)
	public List<Usuario> usuariosInactivos() {

		return usuarioRepositorio.buscarUsuarioInactivos();
	}

	@Transactional(readOnly = true)
	public Integer cantidadDeUsuario() {

		return usuarioRepositorio.cantidadUsuario();
	}

	@Transactional(readOnly = true)
	public Double porcentajeUsuariosActivos() {

		return (double) (usuarioRepositorio.cantidadUsuario() / usuarioRepositorio.buscarUsuarioActivos().size());
	}

	@Transactional(readOnly = true)
	public Double porcentajeUsuariosInactivos() {

		return (double) (usuarioRepositorio.cantidadUsuario() / usuarioRepositorio.buscarUsuarioInactivos().size());
	}

	public void validar(Usuario usuario) throws Exception {
		
		
		if (StringUtils.isBlank(usuario.getNombreDeUsuario()) && StringUtils.isBlank(usuario.getNombre())
				&& usuario.getMail().isEmpty() && usuario.getContrasenia().isEmpty()) {
			throw new Exception("*Los campos estan vacíos");
		}

		if (usuario.getNombreDeUsuario() == null || StringUtils.isBlank(usuario.getNombreDeUsuario())) {
			throw new Exception("*El nombre de usuario es inválido");
		}
		
		if (usuarioRepositorio.buscarPorNombreDeUsuario(usuario.getNombreDeUsuario()) != null) {
			throw new Exception("*El nombre de usuario ya existe");
		}
				
		if (usuario.getNombre() == null || StringUtils.isBlank(usuario.getNombre())) {
			throw new Exception("*El nombre y apellido es inválido");
		}

		// codigo de validacion de mail sacado de internet :D
		if (usuario.getMail() == null || usuario.getMail().isEmpty() || !usuario.getMail().contains("@")) {
			throw new Exception("*El mail es inválido");
		}

		if (usuario.getContrasenia() == null || usuario.getContrasenia().isEmpty() || usuario.getContrasenia().contains("  ")) {
			throw new Exception("*La contraseña es inválida");
		}
		
		if (usuario.getContrasenia().length() <= 6) {
			throw new Exception("*La contraseña debe tener al menos 6 caracteres");
		}
		
		Date hoy = new Date();
		// conversion de fecha tipo string a tipo date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaNacimiento = sdf.parse(usuario.getFechaNacimiento());

		if (usuario.getFechaNacimiento() == null || fechaNacimiento.after(hoy)) {
			throw new Exception("*La fecha de nacimiento es inválida");
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public UserDetails loadUserByUsername(String nombreDeUsuario) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepositorio.buscarPorNombreDeUsuario(nombreDeUsuario);
		User user = null;
		if (usuario != null && usuario.getAlta() == true) {
			List<GrantedAuthority> permisos = new ArrayList<>();
			GrantedAuthority permiso = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
			permisos.add(permiso);
			// Se extraen atributos de contexto del navegador
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			// Se crea la sesion y se agrega el cliente a la misma
			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("usuariosession", usuario);
			user = new User(nombreDeUsuario, usuario.getContrasenia(), permisos);
		} else {
			throw new UsernameNotFoundException("El usuario no se encontró");
		}
		return user;
	}

//	public Boolean validarMayoriaEdad(Usuario usuario) {
//		DateTimeFormatter farmateo = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		LocalDate fechaDeNacimiento = LocalDate.parse(usuario.getFechaNacimiento(), farmateo);
//		Period edad = Period.between(fechaDeNacimiento, LocalDate.now());
//		if (edad.getYears() < 18) {
//			return false;
//		} else {
//			return true;
//		}
//	}
}
