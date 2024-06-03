package med.voll.api.services;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.paciente.*;
import med.voll.api.infra.exception.ErrorDTO;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public boolean pacienteExisteByEmail(String email) {

        return pacienteRepository.existsByEmail(email);
    }

    public boolean pacienteExisteById(Long id) {

        return pacienteRepository.existsById(id);
    }

    public boolean pacienteExisteByCpf(String cpf) {
        return pacienteRepository.existsByCpf(cpf);
    }

    @Transactional
    public ResponseEntity<Object> cadastrarPaciente(DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
        var paciente = new Paciente(dados);
        pacienteRepository.save(paciente);
        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    public Page<DadosListagemPaciente> listarPacientes(Pageable paginacao){
        return pacienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);

    }

    public ResponseEntity<Object> detalharPaciente(Long id){

        try {
            var paciente = pacienteRepository.getReferenceById(id);

            return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
        } catch (EntityNotFoundException erro) {
            throw new EntityNotFoundException("Paciente não encontrado!");
        }

    }
    @Transactional
    public ResponseEntity<Object> excluirPaciente(Long id){
        var paciente = pacienteRepository.getReferenceById(id);
        if(paciente.getAtivo()) {
            paciente.excluir();
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body(new ErrorDTO("Paciente não encontrado!"));
        }
    }

    @Transactional
    public ResponseEntity<Object> atualizarPaciente(DadosAtualizacaoPaciente dados, Long id){
        var paciente = pacienteRepository.getReferenceById(id);

        paciente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

}
