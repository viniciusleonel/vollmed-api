package med.voll.api.services;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosCadastroUsuario;
import med.voll.api.domain.usuario.DadosDetalhamentoUsuario;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public ResponseEntity<?> cadastrarNovoUsuario(DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder) {
        Usuario usuario = new Usuario(dados, passwordEncoder);
        usuarioRepository.save(usuario);
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
    }

}
