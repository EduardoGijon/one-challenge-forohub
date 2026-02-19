package alura.cursos.forohub.controller;

import alura.cursos.forohub.domain.usuario.DatosAutenticacionUsuario;
import alura.cursos.forohub.domain.usuario.Usuario;
import alura.cursos.forohub.infra.security.DatosTokenJWT;
import alura.cursos.forohub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    /**
     * Endpoint POST /login
     * Autentica al usuario y retorna un token JWT
     *
     * @param datos Credenciales del usuario (correoElectronico, contrasena)
     * @return ResponseEntity con el token JWT
     */
    @PostMapping
    public ResponseEntity<DatosTokenJWT> autenticarUsuario(
            @RequestBody @Valid DatosAutenticacionUsuario datos) {

        var authToken = new UsernamePasswordAuthenticationToken(
                datos.correoElectronico(), datos.contrasena());

        var usuarioAutenticado = authenticationManager.authenticate(authToken);

        var tokenJWT = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }
}

