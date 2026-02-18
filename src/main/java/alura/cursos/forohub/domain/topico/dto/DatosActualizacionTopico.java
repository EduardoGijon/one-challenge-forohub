package alura.cursos.forohub.domain.topico.dto;

import alura.cursos.forohub.domain.topico.StatusTopico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para la actualización de un tópico existente
 * Todos los campos son obligatorios (mismas reglas que el registro)
 */
public record DatosActualizacionTopico(

        @NotBlank(message = "El título es obligatorio")
        String titulo,

        @NotBlank(message = "El mensaje es obligatorio")
        String mensaje,

        @NotNull(message = "El estado es obligatorio")
        StatusTopico status,

        @NotBlank(message = "El nombre del curso es obligatorio")
        String curso
) {
}

