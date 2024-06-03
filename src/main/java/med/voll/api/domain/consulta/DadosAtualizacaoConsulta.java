package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.infra.validation.MustBeNull;

import java.time.LocalDateTime;

public record DadosAtualizacaoConsulta(

        @MustBeNull(message = "Médico não pode ser alterado!")
        Long idMedico,

        @MustBeNull(message = "Paciente não pode ser alterado!")
        Long idPaciente,

        @Future
        LocalDateTime data,

        ConsultaStatus status

) {
}
