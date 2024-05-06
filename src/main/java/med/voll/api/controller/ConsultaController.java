package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import med.voll.api.domain.paciente.DadosDetalhamentoPaciente;
import med.voll.api.domain.paciente.DadosListagemPaciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.services.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
@CrossOrigin(origins = {"*"})
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        return consultaService.agendarConsulta(dados);
    }

    @DeleteMapping
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        return consultaService.cancelarConsulta(dados);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){

        return consultaService.detalharConsulta(id);
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoConsulta>> listar(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao){
        Page<DadosDetalhamentoConsulta> page = consultaService.listarConsultas(paginacao);
        return ResponseEntity.ok(page);
    }
}
