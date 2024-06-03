package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.infra.validation.MustBeNull;

public record DadosAtualizacaoPaciente(

        String nome,

        @MustBeNull(message = "Email não pode ser modificado!")
        String email,

        @MustBeNull(message = "CPF não pode ser modificado!")
        String cpf,

        @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "Insira um número de telefone válido no formato (xx) xxxxx-xxxx!")
        String telefone,

        @Valid
        DadosEndereco endereco) {
}
