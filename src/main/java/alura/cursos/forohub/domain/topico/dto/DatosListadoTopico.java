package alura.cursos.forohub.domain.topico.dto;

import alura.cursos.forohub.domain.topico.StatusTopico;
import alura.cursos.forohub.domain.topico.Topico;

import java.time.LocalDateTime;

/**
 * DTO simplificado para el listado de tópicos
 * Contiene solo los datos esenciales para la vista de lista
 */
public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        StatusTopico status,
        String autorNombre,
        String cursoNombre
) {
    public DatosListadoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre()
        );
    }
}

