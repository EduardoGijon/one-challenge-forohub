package alura.cursos.forohub.controller;

import alura.cursos.forohub.domain.topico.TopicoService;
import alura.cursos.forohub.domain.topico.dto.DatosActualizacionTopico;
import alura.cursos.forohub.domain.topico.dto.DatosListadoTopico;
import alura.cursos.forohub.domain.topico.dto.DatosRegistroTopico;
import alura.cursos.forohub.domain.topico.dto.DatosRespuestaTopico;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService topicoService;

    public TopicoController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> crearTopico(
            @RequestBody @Valid DatosRegistroTopico datos,
            UriComponentsBuilder uriBuilder) {

        DatosRespuestaTopico topicoCreado = topicoService.crearTopico(datos);
        var url = uriBuilder.path("/topicos/{id}").buildAndExpand(topicoCreado.id()).toUri();
        return ResponseEntity.created(url).body(topicoCreado);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC)
            Pageable paginacion) {
        return ResponseEntity.ok(topicoService.listarTopicos(paginacion));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<DatosListadoTopico>> buscarTopicos(
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer anio,
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC)
            Pageable paginacion) {
        return ResponseEntity.ok(topicoService.buscarTopicos(curso, anio, paginacion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> obtenerTopico(@PathVariable Long id) {
        return ResponseEntity.ok(topicoService.obtenerTopicoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid DatosActualizacionTopico datos) {
        return ResponseEntity.ok(topicoService.actualizarTopico(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }
}
