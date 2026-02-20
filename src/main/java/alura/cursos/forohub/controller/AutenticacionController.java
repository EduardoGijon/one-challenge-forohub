package alura.cursos.forohub.controller;

import alura.cursos.forohub.domain.usuario.DatosAutenticacionUsuario;
import alura.cursos.forohub.domain.usuario.Usuario;
import alura.cursos.forohub.infra.security.DatosTokenJWT;
import alura.cursos.forohub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutenticacionController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

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

        var principal = (Usuario) usuarioAutenticado.getPrincipal();

        var tokenJWT = tokenService.generarToken(principal);

        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }
}
