package med.voll.api.services;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.medico.*;
import med.voll.api.infra.exception.ErrorDTO;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public boolean medicoExisteByEmail(String email) {

        return medicoRepository.existsByEmail(email);
    }

    public boolean medicoExisteById(Long id) {
        return medicoRepository.existsById(id);
    }

    public boolean medicoExisteByCrm(String crm) {
        return medicoRepository.existsByCrm(crm);
    }

    @Transactional
    public ResponseEntity<Object> cadastrarNovoMedico(DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){
        var medico = new Medico(dados);
        medicoRepository.save(medico);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    public Page<DadosListagemMedico> listarMedicos(Pageable paginacao) {
        return medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }

    public ResponseEntity<Object> detalharMedico(Long id){

        try {
            var medico = medicoRepository.getReferenceById(id);

            return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
        } catch (EntityNotFoundException erro) {
            throw new EntityNotFoundException("Médico não encontrado!");
        }

    }

    @Transactional
    public ResponseEntity<Object> excluirMedico(Long id){
        var medico = medicoRepository.getReferenceById(id);
        if (medico.getAtivo()) {
            medico.excluir();
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body(new ErrorDTO("Médico não encontrado!"));
        }

    }

    @Transactional
    public ResponseEntity<Object> atualizarMedico(DadosAtualizacaoMedico dados, Long id){

        var medico = medicoRepository.getReferenceById(id);

        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

}
