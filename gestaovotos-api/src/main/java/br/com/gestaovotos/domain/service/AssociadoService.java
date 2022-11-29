package br.com.gestaovotos.domain.service;

import br.com.gestaovotos.api.v1.dto.StatusCpfDto;
import br.com.gestaovotos.domain.model.Associado;
import br.com.gestaovotos.domain.repository.AssociadoRepository;
import br.com.gestaovotos.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Service
public class AssociadoService {
	
	@Autowired
	private AssociadoRepository associadoRepository;

	private static final String MSG_ASSOCIADO_EM_USO = "Associado de id %d não pode ser removida, pois está em uso";
	private static final String MSG_CPF_EM_USO = "Já existe um cadastro de CPF com o CPF informado %s";
	private static final String MSG_VOTO_NEGADO = "Este CPF %s não está apto para votar";
	private static final String MSG_ERROR_CLIENT = "Erro inesperado ao fazer a solicitação ao cliente";
	private static final String UNABLE_TO_VOTE = "UNABLE_TO_VOTE";


	@Value("${url.validacao.cpf}")
	private String urlValidacaoCpf = "";

	@Autowired
	private RestTemplate restTemplate;

	@Transactional
	public Associado salvar(Associado associado) {

			validarCpf(associado);
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

	private void validarCpf(Associado associado) throws HttpClientErrorException {

		try {
			ResponseEntity<StatusCpfDto> cpfValidacao = consultarCpf(associado);
			if (HttpStatus.OK.equals(cpfValidacao.getStatusCode())) {
				if (UNABLE_TO_VOTE.equalsIgnoreCase(cpfValidacao.getBody().getStatus())) {
					throw new VotoNegadoException(String.format(MSG_VOTO_NEGADO, associado.getCpf()));
				}
			}
		} catch (org.springframework.web.client.HttpClientErrorException e) {
			throw new HttpClientErrorException(String.format(MSG_ERROR_CLIENT, associado.getCpf()));
		}
	}

	private ResponseEntity<StatusCpfDto> consultarCpf(Associado associado) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(urlValidacaoCpf.concat("/").concat(associado.getCpf()), HttpMethod.GET, entity,
				StatusCpfDto.class);
	}
	
}