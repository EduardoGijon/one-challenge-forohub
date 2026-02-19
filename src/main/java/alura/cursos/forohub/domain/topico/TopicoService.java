package alura.cursos.forohub.domain.topico;

import alura.cursos.forohub.domain.curso.CursoRepository;
import alura.cursos.forohub.domain.respuesta.RespuestaRepository;
import alura.cursos.forohub.domain.topico.dto.DatosActualizacionTopico;
import alura.cursos.forohub.domain.topico.dto.DatosListadoTopico;
import alura.cursos.forohub.domain.topico.dto.DatosRegistroTopico;
import alura.cursos.forohub.domain.topico.dto.DatosRespuestaTopico;
import alura.cursos.forohub.domain.usuario.UsuarioRepository;
import alura.cursos.forohub.infra.errores.ValidacionException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Transactional
    public DatosRespuestaTopico crearTopico(DatosRegistroTopico datos) {
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new ValidacionException("Ya existe un tópico con el mismo título y mensaje");
        }

        var autor = usuarioRepository.findById(datos.autorId())
                .orElseThrow(() -> new ValidacionException("El autor con ID " + datos.autorId() + " no existe"));

        var curso = cursoRepository.findByNombre(datos.curso())
                .orElseThrow(() -> new ValidacionException("El curso '" + datos.curso() + "' no existe"));

        var topico = new Topico();
        topico.setTitulo(datos.titulo());
        topico.setMensaje(datos.mensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);
        topico.setFechaCreacion(LocalDateTime.now());
        topico.setStatus(StatusTopico.ABIERTO);

        return new DatosRespuestaTopico(topicoRepository.save(topico));
    }

    public Page<DatosListadoTopico> listarTopicos(Pageable paginacion) {
        return topicoRepository.findAll(paginacion).map(DatosListadoTopico::new);
    }

    public Page<DatosListadoTopico> buscarTopicos(String curso, Integer anio, Pageable paginacion) {
        Page<Topico> topicos;

        if (curso != null && anio != null) {
            topicos = topicoRepository.findByCursoNombreAndAnio(curso, anio, paginacion);
        } else if (curso != null) {
            topicos = topicoRepository.findByCursoNombre(curso, paginacion);
        } else if (anio != null) {
            topicos = topicoRepository.findByAnio(anio, paginacion);
        } else {
            topicos = topicoRepository.findAll(paginacion);
        }

        return topicos.map(DatosListadoTopico::new);
    }

    public DatosRespuestaTopico obtenerTopicoPorId(Long id) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico con ID " + id + " no encontrado"));
        return new DatosRespuestaTopico(topico);
    }

    @Transactional
    public DatosRespuestaTopico actualizarTopico(Long id, DatosActualizacionTopico datos) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico con ID " + id + " no encontrado"));

        var topicoExistente = topicoRepository.findByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if (topicoExistente.isPresent() && !topicoExistente.get().getId().equals(id)) {
            throw new ValidacionException("Ya existe otro tópico con el mismo título y mensaje");
        }

        var curso = cursoRepository.findByNombre(datos.curso())
                .orElseThrow(() -> new ValidacionException("El curso '" + datos.curso() + "' no existe"));

        topico.setTitulo(datos.titulo());
        topico.setMensaje(datos.mensaje());
        topico.setStatus(datos.status());
        topico.setCurso(curso);

        return new DatosRespuestaTopico(topicoRepository.save(topico));
    }

    @Transactional
    public void eliminarTopico(Long id) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico con ID " + id + " no encontrado"));

        if (respuestaRepository.existsByTopicoId(id)) {
            long cantidad = respuestaRepository.countByTopicoId(id);
            throw new ValidacionException(
                "No se puede eliminar el tópico porque tiene " + cantidad +
                " respuesta(s) asociada(s). Elimínalas primero o cierra el tópico."
            );
        }

        topicoRepository.deleteById(topico.getId());
    }
}
