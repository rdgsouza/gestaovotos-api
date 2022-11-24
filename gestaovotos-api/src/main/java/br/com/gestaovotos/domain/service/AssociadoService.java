package br.com.gestaovotos.domain.service;

import br.com.gereciamentovotacao.domain.model.Associado;
import br.com.gereciamentovotacao.domain.repository.AssociadoRepository;
import br.com.gereciamentovotacao.exception.AssociadoNaoEncontradaException;
import br.com.gereciamentovotacao.exception.CpfEmUsoException;
import br.com.gereciamentovotacao.exception.EntidadeEmUsoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AssociadoService {
	
	@Autowired
	private AssociadoRepository associadoRepository;
	private static final String MSG_ASSOCIADO_EM_USO = "Associado de id %d não pode ser removida, pois está em uso";

	private static final String MSG_CPF_EM_USO = "Já existe um cadastro de CPF com o CPF informado %s";


	@Transactional
	public Associado salvar(Associado associado) {

		verificaCpfExistente(associado);

		return associadoRepository.save(associado);
	}

	private void verificaCpfExistente(Associado associado) {

	 Optional <Associado> associadoAtual = associadoRepository.findByCpf(associado.getCpf());

		if(associadoAtual.isPresent()) {
			throw new CpfEmUsoException(String.format(MSG_CPF_EM_USO, associado.getCpf()));
		}

	}

	public Associado buscarOuFalhar(Long associadoId) {
		return associadoRepository.findById(associadoId)
				.orElseThrow(() -> new AssociadoNaoEncontradaException(
						associadoId));
	}

	@Transactional
	public void excluir(Long associadoId) {
		try {
			associadoRepository.deleteById(associadoId);
			associadoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new AssociadoNaoEncontradaException(associadoId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ASSOCIADO_EM_USO,
					associadoId));
		}
	}
	
}