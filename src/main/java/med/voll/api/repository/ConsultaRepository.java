package med.voll.api.repository;

import med.voll.api.domain.consulta.Consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);

    boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

    Page<Consulta> findAll (Pageable paginacao);

    boolean existsById(Long id);

    @Query("SELECT c FROM Consulta c WHERE c.data < :dataHoraHoje AND c.consultaStatus = 'CONSULTA_ATIVA'")
    List<Consulta> findConsultasAtivasComDatasNoPassado(LocalDateTime dataHoraHoje);
}
