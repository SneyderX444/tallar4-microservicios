package co.edu.udistrital.backend.repository;

import co.edu.udistrital.backend.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repositorio JPA de Actividad. Solo CRUD heredado, sin consultas personalizadas. */
@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {
}
