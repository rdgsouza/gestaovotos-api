package br.com.gestaovotos.domain.service;

import br.com.gereciamentovotacao.domain.model.Pauta;
import br.com.gereciamentovotacao.domain.repository.PautaRepository;
import br.com.gereciamentovotacao.exception.EntidadeEmUsoException;
import br.com.gereciamentovotacao.exception.PautaNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PautaService {
	
	@Autowired
	private PautaRepository pautaRepository;
	private static final String MSG_PAUTA_EM_USO = "Pauta de id %d não pode ser removida, pois está em uso";

	@Transactional
	public Pauta salvar(Pauta pauta) {

		return pautaRepository.save(pauta);
	}

	public Pauta buscarOuFalhar(Long pautaId) {
		return pautaRepository.findById(pautaId)
				.orElseThrow(() -> new PautaNaoEncontradaException(
						pautaId));
	}

	@Transactional
	public void excluir(Long pautaId) {
		try {
			pautaRepository.deleteById(pautaId);
			pautaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new PautaNaoEncontradaException(pautaId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_PAUTA_EM_USO,
					pautaId));
		}
	}
	
}