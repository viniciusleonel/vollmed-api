package med.voll.api.domain.consulta;

import lombok.*;
import jakarta.persistence.*;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

@Table(name = "consultas")
@Entity(name = "Consulta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime data;

    @Column(name = "consulta_status")
    @Enumerated(EnumType.STRING)
    private ConsultaStatus consultaStatus;

    public void atualizarStatusConsulta(ConsultaStatus motivo) {

        this.consultaStatus = motivo;
    }

    public void atualizarInformacoes(DadosAtualizacaoConsulta dados) {

        if (dados.data() != null) {
            this.data = dados.data();
        }
        if (consultaStatus != null) {
            this.consultaStatus = dados.status();
        }

    }

}
