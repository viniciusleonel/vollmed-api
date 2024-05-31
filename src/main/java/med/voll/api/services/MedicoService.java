package med.voll.api.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public boolean medicoExiste(String email) {
        return medicoRepository.existsByEmail(email);
    }

    @Transactional
    public ResponseEntity<?> cadastrarNovoMedico(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){
        var medico = new Medico(dados);
        medicoRepository.save(medico);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    public Page<DadosListagemMedico> listarMedicos(Pageable paginacao) {
        return medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }

    public ResponseEntity detalharMedico(@PathVariable Long id){

        try {
            var medico = medicoRepository.getReferenceById(id);

            return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
        } catch (EntityNotFoundException erro) {
            throw new EntityNotFoundException("Médico não encontrado!");
        }

    }

    @Transactional
    public ResponseEntity excluirMedico(@PathVariable Long id){
        var medico = medicoRepository.getReferenceById(id);
        if (medico.getAtivo()) {
            medico.excluir();
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("Médico não encontrado!");
        }

    }

    private boolean dadosContemCamposInvalidos(DadosAtualizacaoMedico dados) {
        if (dados.nome() != null || dados.telefone() != null || dados.endereco() != null) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional
    public ResponseEntity<?> atualizarMedico(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = medicoRepository.getReferenceById(dados.id());

        if (dadosContemCamposInvalidos(dados)) {
            return ResponseEntity.badRequest().body("Os campos informados para atualização não são permitidos.");
        }

        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

}
