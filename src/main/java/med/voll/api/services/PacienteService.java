package med.voll.api.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public boolean PacienteExiste(String email) {
        return pacienteRepository.existsByEmail(email);
    }

    @Transactional
    public ResponseEntity cadastrarPaciente(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
        var paciente = new Paciente(dados);
        pacienteRepository.save(paciente);
        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    public Page<DadosListagemPaciente> listarPacientes(Pageable paginacao){
        return pacienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);

    }

    public ResponseEntity detalharPaciente(@PathVariable Long id){

        try {
            var paciente = pacienteRepository.getReferenceById(id);

            return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
        } catch (EntityNotFoundException erro) {
            throw new EntityNotFoundException("Paciente não encontrado!");
        }

    }
    @Transactional
    public ResponseEntity excluirPaciente(@PathVariable Long id){


        var paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir();

        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity atualizarPaciente(@RequestBody @Valid DadosAtualizacaoPaciente dados){
        var paciente = pacienteRepository.getReferenceById(dados.id());

        if (dadosContemCamposInvalidos(dados)) {
            return ResponseEntity.badRequest().body("Os campos informados para atualização não são permitidos.");
        }

        paciente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    private boolean dadosContemCamposInvalidos(DadosAtualizacaoPaciente dados) {
        if (dados.nome() != null || dados.telefone() != null || dados.endereco() != null) {
            return false;
        } else {
            return true;
        }
    }

}
