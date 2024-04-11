package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, String nomeMedico, String nomePaciente, LocalDateTime data, Especialidade especialidade, ConsultaStatus status){

    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getNome(), consulta.getPaciente().getNome(), consulta.getData(), consulta.getMedico().getEspecialidade(), consulta.getConsultaStatus());
    }
}
