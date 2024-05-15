package med.voll.api.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ConsultaService {

    @Autowired
    private AgendaDeConsultas agenda;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Transactional
    public ResponseEntity agendarConsulta(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @Transactional
    public ResponseEntity cancelarConsulta(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity detalharConsulta(@PathVariable Long id){

        try {
            var consulta = consultaRepository.getReferenceById(id);

            return ResponseEntity.ok(new DadosDetalhamentoConsulta(consulta));
        } catch (EntityNotFoundException erro) {
            throw new EntityNotFoundException("Consulta não encontrada!");
        }

    }

    public Page<DadosDetalhamentoConsulta> listarConsultas(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao){
        return consultaRepository.findAll(paginacao).map(DadosDetalhamentoConsulta::new);

    }
}
