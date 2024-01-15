package med.voll.api.domain.paciente;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PacienteRepositoryTest {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Paciente não pode ter duas consultas no mesmo dia")
    void pacienteSemOutraConsultaNoDia() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.Cardiologia);
        var paciente = cadastrarPaciente("Paciente", "paciente@gmail.com", "12345678910");
        var consulta = cadastrarEObterConsulta(medico, paciente, proximaSegundaAs10);

        var primeiroHorario = consulta.getData().withHour(7);
        var ultimoHorario = consulta.getData().withHour(18);

        var pacientePossuiOutraConsultaNoDia = consultaRepository.existsByPacienteIdAndDataBetween(
                paciente.getId(),
                primeiroHorario,
                ultimoHorario);

        assertThat(pacientePossuiOutraConsultaNoDia).isTrue();
    }

    @Test
    @DisplayName("Verifica se paciente está desativado")
    void pacienteDesativado() {
        var paciente = cadastrarPaciente("Paciente", "paciente@gmail.com", "12345678910");

        paciente.excluir();

        assertThat(pacienteRepository.findAtivoById(paciente.getId())).isFalse();
    }

    @Test
    @DisplayName("Verifica se paciente está ativo")
    void pacienteAtivo() {
        var paciente = cadastrarPaciente("Paciente", "paciente@gmail.com", "12345678910");

        assertThat(pacienteRepository.findAtivoById(paciente.getId())).isTrue();
    }

    private Medico cadastrarMedico (String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data, null));
    }

    private Consulta cadastrarEObterConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        var consulta = new Consulta(null, medico, paciente, data, null);
        em.persist(consulta);
        return consulta;
    }

    private Paciente cadastrarPaciente (String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosMedico (String nome, String email, String crm, Especialidade especialidade){
        return new DadosCadastroMedico(
                nome,
                email,
                "119933107760",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "11993310776",
                cpf,
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "São Paulo",
                "SP",
                null,
                null
        );
    }
}
