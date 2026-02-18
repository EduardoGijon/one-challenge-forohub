package alura.cursos.forohub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Validar que no exista un tópico con el mismo título y mensaje
    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    // Buscar tópico por título y mensaje
    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);

    // Buscar tópicos por nombre de curso
    @Query("SELECT t FROM Topico t WHERE t.curso.nombre = :nombreCurso")
    Page<Topico> findByCursoNombre(@Param("nombreCurso") String nombreCurso, Pageable pageable);

    // Buscar tópicos por año de creación
    @Query("SELECT t FROM Topico t WHERE YEAR(t.fechaCreacion) = :anio")
    Page<Topico> findByAnio(@Param("anio") Integer anio, Pageable pageable);

    // Buscar tópicos por curso y año
    @Query("SELECT t FROM Topico t WHERE t.curso.nombre = :nombreCurso AND YEAR(t.fechaCreacion) = :anio")
    Page<Topico> findByCursoNombreAndAnio(@Param("nombreCurso") String nombreCurso, @Param("anio") Integer anio, Pageable pageable);
}

