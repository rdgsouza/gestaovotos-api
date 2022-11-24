package br.com.gestaovotos.domain.service;

import br.com.gestaovotos.domain.model.Pauta;
import br.com.gestaovotos.domain.model.Voto;
import br.com.gestaovotos.domain.repository.SessaoRepository;
import br.com.gestaovotos.domain.repository.VotoRepository;
import br.com.gestaovotos.exception.VotosNaoEncontradoPorPautaException;
import br.com.gestaovotos.api.v1.dto.ResultadoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultadoService {

	@Autowired
	private VotoRepository votoRepository;
	@Autowired
	private SessaoRepository sessaoRepository;

	private static final String MSG_VOTO_NAO_ENCONTRADO_POR_PAUTA = "Não existem votos pelo id %d da pauta informada";


	public ResultadoDto getResultadoVotacao(Long pautaId) {
		ResultadoDto resultadoPauta = buildVotosPauta(pautaId);
		return resultadoPauta;
	}

	public ResultadoDto buildVotosPauta(Long pautaId) {
		Optional<List<Voto>> votosPorPauta = votoRepository.findByPautaId(pautaId);
		if (!votosPorPauta.isPresent() || votosPorPauta.get().isEmpty()) {
			throw new VotosNaoEncontradoPorPautaException(String.format(MSG_VOTO_NAO_ENCONTRADO_POR_PAUTA, pautaId));
		}

		Pauta pauta = votosPorPauta.get().iterator().next().getPauta();

		Long totalSessoes = sessaoRepository.countByPautaId(pauta.getId());

		Integer total = votosPorPauta.get().size();

		Integer totalSim = (int) votosPorPauta.get().stream().filter(
				votoSim -> Boolean.TRUE.equals(votoSim.getOpcaoVoto())).count();

		Integer totalNao = total - totalSim;

		return ResultadoDto.builder()
				.pauta(pauta)
				.totalVotos(total)
				.totalSessoes(totalSessoes.intValue())
				.totalSim(totalSim)
				.totalNao(totalNao).build();
	}

}
