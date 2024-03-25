package med.voll.api.domain.consulta;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles("test")
class ConsultaRepositoryTest {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Consulta não pode ser agendada com menos de 30 minutos de antecedência ")
    void horarioAntecedencia() {
        var horaComMaisDe30MinDeAntecedencia = LocalDateTime.now();
        var horaComMenosDe30MinDeAntecedencia = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(9,40);
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var diferencaEntreMinutos = Duration.between(horaComMaisDe30MinDeAntecedencia, proximaSegundaAs10).toMinutes();
        var diferencaEntreMinutos2 = Duration.between(horaComMenosDe30MinDeAntecedencia, proximaSegundaAs10).toMinutes();

        assertThat(diferencaEntreMinutos).isGreaterThan(30);
        assertThat(diferencaEntreMinutos2).isLessThan(30);
    }

    @Test
    @DisplayName("Não cadastrar consulta fora do horário de funcionamento")
    void horarioFuncionamentoClinica() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.Cardiologia);
        var paciente = cadastrarPaciente("Paciente", "paciente@gmail.com", "12345678810");

        var consulta = cadastrarEObterConsulta(medico, paciente, proximaSegundaAs10);

        assertThat(consulta.getData().getDayOfWeek()).as("A consulta não deve ser agendada em um sábado ou domingo").isNotIn(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        assertThat(consulta.getData().getHour()).as("A consulta não deve ser agendada antes das 7 horas").isGreaterThanOrEqualTo(7);
        assertThat(consulta.getData().getHour()).as("A consulta não deve ser agendada após as 18 horas").isLessThanOrEqualTo(18);

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
