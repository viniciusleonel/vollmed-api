package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.ValidacaoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class TratadorDeErros {

    public String removerAspas(String texto) {
        // Substitua todas as aspas simples e duplas na string por uma string vazia
        return texto.replaceAll("'", "").replaceAll("\"", "");
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<DadosErroValidacao> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        String mensagemErro = "Erro de violação de integridade de dados: " ;
        String mensagemCompleta = ex.getMessage();
        int indiceKeyCampo = mensagemCompleta.indexOf("medicos");
        String campo = removerAspas(mensagemCompleta.substring(indiceKeyCampo + 8));
        int indiceKeyValor = mensagemCompleta.indexOf("entry");
        int indiceFimValor = mensagemCompleta.indexOf("for key");
        String valor = removerAspas(mensagemCompleta.substring(indiceKeyValor + 7, indiceFimValor).trim());

        return ResponseEntity.badRequest().body(new DadosErroValidacao(campo,valor, mensagemErro + mensagemCompleta));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        var err = new ValidacaoException("Os dados não foram encontrados!" );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(err.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    private record DadosErroValidacao(String campo,String valor, String mensagem) {
        public DadosErroValidacao(FieldError erro){
            this(erro.getField(),erro.getField() ,erro.getDefaultMessage());
        }
    }
}
