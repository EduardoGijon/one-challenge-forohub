package alura.cursos.forohub.controller;

import alura.cursos.forohub.domain.topico.TopicoService;
import alura.cursos.forohub.domain.topico.dto.DatosActualizacionTopico;
import alura.cursos.forohub.domain.topico.dto.DatosRegistroTopico;
import alura.cursos.forohub.domain.topico.dto.DatosRespuestaTopico;
import alura.cursos.forohub.domain.topico.dto.DatosListadoTopico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    /**
     * Endpoint POST /topicos
     * Crea un nuevo tópico con validaciones:
     * - Todos los campos son obligatorios
     * - No permite tópicos duplicados (mismo título y mensaje)
     *
     * @param datos Datos del tópico a crear (título, mensaje, autorId, curso)
     * @param uriBuilder Constructor de URI para la respuesta
     * @return ResponseEntity con el tópico creado y status 201 (CREATED)
     */
    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> crearTopico(
            @RequestBody @Valid DatosRegistroTopico datos,
            UriComponentsBuilder uriBuilder) {

        DatosRespuestaTopico topicoCreado = topicoService.crearTopico(datos);

        // Construir la URI del recurso creado
        URI url = uriBuilder.path("/topicos/{id}")
                .buildAndExpand(topicoCreado.id())
                .toUri();

        // Retornar 201 CREATED con el Location header
        return ResponseEntity.created(url).body(topicoCreado);
    }

    /**
     * Endpoint GET /topicos
     * Lista todos los tópicos con paginación y ordenamiento
     * Por defecto: 10 elementos por página, ordenados por fecha de creación ASC
     *
     * Parámetros opcionales:
     * - page: número de página (default: 0)
     * - size: tamaño de página (default: 10)
     * - sort: campo de ordenamiento (default: fechaCreacion,asc)
     *
     * @param paginacion Parámetros de paginación y ordenamiento
     * @return Page con los tópicos encontrados
     */
    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC)
            Pageable paginacion) {

        Page<DatosListadoTopico> topicos = topicoService.listarTopicos(paginacion);
        return ResponseEntity.ok(topicos);
    }

    /**
     * Endpoint GET /topicos/buscar
     * Busca tópicos por nombre de curso y/o año específico
     *
     * Parámetros de consulta:
     * - curso: nombre del curso (opcional)
     * - anio: año de creación (opcional)
     *
     * Ejemplos:
     * - /topicos/buscar?curso=Spring Boot
     * - /topicos/buscar?anio=2026
     * - /topicos/buscar?curso=Java&anio=2026
     *
     * @param curso Nombre del curso para filtrar
     * @param anio Año de creación para filtrar
     * @param paginacion Parámetros de paginación
     * @return Page con los tópicos filtrados
     */
    @GetMapping("/buscar")
    public ResponseEntity<Page<DatosListadoTopico>> buscarTopicos(
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer anio,
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC)
            Pageable paginacion) {

        Page<DatosListadoTopico> topicos = topicoService.buscarTopicos(curso, anio, paginacion);
        return ResponseEntity.ok(topicos);
    }

    /**
     * Endpoint GET /topicos/{id}
     * Obtiene un tópico específico por su ID
     *
     * @param id ID del tópico
     * @return ResponseEntity con el tópico encontrado o 404 NOT FOUND
     */
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> obtenerTopico(@PathVariable Long id) {
        DatosRespuestaTopico topico = topicoService.obtenerTopicoPorId(id);
        return ResponseEntity.ok(topico);
    }

    /**
     * Endpoint PUT /topicos/{id}
     * Actualiza los datos de un tópico existente
     *
     * Reglas de negocio (mismas que el registro):
     * - Todos los campos son obligatorios
     * - No permite tópicos duplicados (mismo título y mensaje)
     * - Verifica que el curso exista
     * - Verifica que el tópico a actualizar exista (usa isPresent())
     *
     * @param id ID del tópico a actualizar (obligatorio, capturado con @PathVariable)
     * @param datos Datos actualizados del tópico (título, mensaje, status, curso)
     * @return ResponseEntity con el tópico actualizado y status 200 (OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid DatosActualizacionTopico datos) {

        DatosRespuestaTopico topicoActualizado = topicoService.actualizarTopico(id, datos);
        return ResponseEntity.ok(topicoActualizado);
    }

    /**
     * Endpoint DELETE /topicos/{id}
     * Elimina un tópico específico de la base de datos
     *
     * Reglas de negocio:
     * - Verifica que el tópico exista antes de eliminarlo (usa isPresent())
     * - Campo ID obligatorio en la URL (@PathVariable)
     * - Usa deleteById() de JpaRepository
     *
     * @param id ID del tópico a eliminar (obligatorio, capturado con @PathVariable)
     * @return ResponseEntity con status 204 (NO CONTENT) si se eliminó exitosamente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }
}
