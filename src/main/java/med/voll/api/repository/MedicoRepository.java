package med.voll.api.repository;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query(value = """
            select m from Medico m
                where m.ativo = true and m.especialidade = :especialidade
                    and m.id not in (
                                    select c.medico.id from Consulta c
                                        where c.data = :data)
                order by function('random')
            """)
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

    @Query("""
            select m.ativo from Medico m
                where m.id = :id
            """)
    Boolean findAtivoById(Long id);

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    boolean existsByCrm(String crm);
}
