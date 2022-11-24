package br.com.gestaovotos.domain.repository;

import br.com.gestaovotos.domain.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

    @Override
    @Query("from Voto v join fetch v.pauta join fetch v.associado")
    List<Voto> findAll();

    @Query("from Voto v join fetch v.pauta join fetch v.associado")
    Optional<List<Voto>> findByPautaId(Long id);
    Optional<Voto> findByCpfAndPautaId(String cpf, Long id);

}
