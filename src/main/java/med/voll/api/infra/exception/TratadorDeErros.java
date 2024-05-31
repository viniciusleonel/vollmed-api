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
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TratadorDeErros {

    public String removerAspas(String texto) {
        // Substitua todas as aspas simples e duplas na string por uma string vazia
        return texto.replaceAll("'", "").replaceAll("\"", "");
    }

//    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
//    public ResponseEntity<DadosErroValidacao> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
//        String mensagemErro = "Erro de violação de integridade de dados: " ;
//        String mensagemCompleta = ex.getMessage();
//        int indiceKeyCampo = mensagemCompleta.indexOf("medicos");
//        String campo = removerAspas(mensagemCompleta.substring(indiceKeyCampo + 8));
//        int indiceKeyValor = mensagemCompleta.indexOf("entry");
//        int indiceFimValor = mensagemCompleta.indexOf("for key");
//        String valor = removerAspas(mensagemCompleta.substring(indiceKeyValor + 7, indiceFimValor).trim());
//
//        return ResponseEntity.badRequest().body(new DadosErroValidacao(campo,valor, mensagemErro + mensagemCompleta));
//    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(EntityNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
//        var erros = ex.getFieldErrors();
//        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
//    }

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
