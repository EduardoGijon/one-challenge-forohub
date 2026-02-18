package alura.cursos.forohub.domain.topico.dto;

import alura.cursos.forohub.domain.topico.StatusTopico;
import alura.cursos.forohub.domain.topico.Topico;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        StatusTopico status,
        Long autorId,
        String autorNombre,
        Long cursoId,
        String cursoNombre
) {
    public DatosRespuestaTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor().getId(),
                topico.getAutor().getNombre(),
                topico.getCurso().getId(),
                topico.getCurso().getNombre()
        );
    }
}

