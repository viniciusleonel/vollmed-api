package med.voll.api.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private AgendaDeConsultas agenda;

    @Autowired
    private ConsultaRepository consultaRepository;

    public boolean consultaExisteById(Long id) {
        return consultaRepository.existsById(id);
    }

    @Transactional
    public void atualizarConsultasAtivasNoPassado() {
        LocalDateTime dataHoraHoje = LocalDateTime.now();
        List<Consulta> consultasAtivasNoPassado = consultaRepository.findConsultasAtivasComDatasNoPassado(dataHoraHoje);

        for (Consulta consulta : consultasAtivasNoPassado) {
            consulta.atualizarStatusConsulta(ConsultaStatus.CONSULTA_EFETUADA);
            consultaRepository.save(consulta);
        }
    }

    @Transactional
    public ResponseEntity<Object> agendarConsulta(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @Transactional
    public ResponseEntity<Object> cancelarConsulta(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity<Object> detalharConsulta(@PathVariable Long id){

        try {
            Consulta consulta = consultaRepository.getReferenceById(id);

            atualizarConsultasAtivasNoPassado();

            return ResponseEntity.ok(new DadosDetalhamentoConsulta(consulta));
        } catch (EntityNotFoundException erro) {
            throw new EntityNotFoundException("Consulta não encontrada!");
        }

    }

    @Transactional
    public ResponseEntity<Object> atualizarConsulta(DadosAtualizacaoConsulta dados, Long id){

        Consulta consulta = consultaRepository.getReferenceById(id);

        atualizarConsultasAtivasNoPassado();

        consulta.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(consulta));
    }

    @Transactional
    public Page<DadosDetalhamentoConsulta> listarConsultas(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao){
        atualizarConsultasAtivasNoPassado();
        return consultaRepository.findAll(paginacao).map(DadosDetalhamentoConsulta::new);

    }
}
