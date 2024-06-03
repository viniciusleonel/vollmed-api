package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.DadosListagemMedico;
import med.voll.api.domain.medico.*;
import med.voll.api.infra.exception.ErrorDTO;
import med.voll.api.services.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("medicos")
@SecurityRequirement(name = "bearer-key")
@CrossOrigin(origins = {"*"})
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){

        if (medicoService.medicoExisteByEmail(dados.email()) || medicoService.medicoExisteByCrm(dados.crm())) {
            return ResponseEntity.badRequest().body(new ErrorDTO("Médico já cadastrado!"));
        }
        return medicoService.cadastrarNovoMedico(dados, uriBuilder);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        Page<DadosListagemMedico> page = medicoService.listarMedicos(paginacao);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados, @PathVariable Long id){

        if (medicoService.medicoExisteById(id)) {
            return medicoService.atualizarMedico(dados, id);
        } else {
            return ResponseEntity.badRequest().body(new ErrorDTO("Médico não encontrado!"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable Long id){

        if (medicoService.medicoExisteById(id)) {
            return medicoService.excluirMedico(id);
        } else {
            return ResponseEntity.badRequest().body(new ErrorDTO("Médico não encontrado!"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> detalhar(@PathVariable Long id){

        return medicoService.detalharMedico(id);
    }
}
