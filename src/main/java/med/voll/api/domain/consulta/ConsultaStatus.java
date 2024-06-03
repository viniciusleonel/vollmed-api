package med.voll.api.domain.consulta;

public enum ConsultaStatus {

    PACIENTE_DESISTIU("Paciente desistiu!"),
    MEDICO_CANCELOU("Médico cancelou!"),

    CONSULTA_ATIVA("Consulta ativa!"),
    CONSULTA_EFETUADA("Consulta efetuada!"),
    OUTROS("Outros");

    private String descricao;
    ConsultaStatus(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }

}
