package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.DadosListagemPaciente;
import med.voll.api.domain.paciente.*;
import med.voll.api.infra.exception.ErrorDTO;
import med.voll.api.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
@CrossOrigin(origins = {"*"})
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {

        if(pacienteService.pacienteExisteByEmail(dados.email()) || pacienteService.pacienteExisteByCpf(dados.cpf())) {
            return ResponseEntity.badRequest().body(new ErrorDTO("Paciente já cadastrado!"));
        }
        return pacienteService.cadastrarPaciente(dados, uriBuilder);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        Page<DadosListagemPaciente> page = pacienteService.listarPacientes(paginacao);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object>atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados, @PathVariable Long id){

        if (pacienteService.pacienteExisteById(id)){
            return pacienteService.atualizarPaciente(dados, id);
        } else {
            return ResponseEntity.badRequest().body(new ErrorDTO("Paciente não encontrado!"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable Long id){

        if (pacienteService.pacienteExisteById(id)) {
            return pacienteService.excluirPaciente(id);
        } else {
            return ResponseEntity.badRequest().body(new ErrorDTO("Paciente não encontrado!"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> detalhar(@PathVariable Long id){

        return pacienteService.detalharPaciente(id);
    }

}
