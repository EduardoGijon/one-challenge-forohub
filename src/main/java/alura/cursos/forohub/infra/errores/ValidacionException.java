package alura.cursos.forohub.infra.errores;

public class ValidacionException extends RuntimeException {

    public ValidacionException(String mensaje) {
        super(mensaje);
    }
}

