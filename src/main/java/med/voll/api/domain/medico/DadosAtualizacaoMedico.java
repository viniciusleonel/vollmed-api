package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.infra.validation.MustBeNull;

public record DadosAtualizacaoMedico(

        String nome,

        @MustBeNull(message = "Email não pode ser modificado!")
        String email,

        @MustBeNull(message = "CRM não pode ser modificado!")
        String crm,

        @MustBeNull(message = "Especialidade não pode ser modificada!")
        Especialidade especialidade,

        @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "Insira um número de telefone válido no formato (xx) xxxxx-xxxx!")
        String telefone,

        @Valid
        DadosEndereco endereco) {

}
