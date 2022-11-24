package br.com.gestaovotos.domain.repository;

import br.com.gestaovotos.domain.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    @Override
    @Query("from Sessao s join fetch s.pauta")
    List<Sessao> findAll();

    Long countByPautaId(Long id);

    Optional<Sessao> findByIdAndPautaId(Long sessaoId, Long pautaId);

}
