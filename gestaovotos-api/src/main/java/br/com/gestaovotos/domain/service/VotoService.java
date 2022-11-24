package br.com.gestaovotos.domain.service;

import br.com.gereciamentovotacao.api.v1.dto.statusCpfDto;
import br.com.gereciamentovotacao.domain.model.Associado;
import br.com.gereciamentovotacao.domain.model.Pauta;
import br.com.gereciamentovotacao.domain.model.Sessao;
import br.com.gereciamentovotacao.domain.model.Voto;
import br.com.gereciamentovotacao.domain.repository.VotoRepository;
import br.com.gereciamentovotacao.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private PautaService pautaService;
    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private AssociadoService associadoService;

    private static final String MSG_TEMPO_ESGOTADO = "Tempo para votação encerrado";

    private static final String MSG_VOTO_EXISTENTE = "Já existe um voto registrado com este CPF %s";

    private static final String MSG_VOTO_NEGADO = "Este CPF %s não está apto para votar";

    private static final String MSG_CPF_INVALIDO = "O CPF informado não é válido, por favor insir um CPF válido";

    private static final String MSG_VOTO_EM_USO = "Voto de id %d não pode ser removido, pois está em uso";

    private static final String CPF_DIFERENTE_CADASTRO = "O CPF informado não coincide com o CPF do associado cadastrado, por favor informe o CPF correto";

    private static final String UNABLE_TO_VOTE = "UNABLE_TO_VOTE";

    @Value("${url.validacao.cpf}")
    private String urlValidacaoCpf = "";

    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    public Voto enviarVoto(Long pautaId, Long sessaoId, Voto voto) {

        verificaCpfCadastrado(voto);

        Pauta pauta = pautaService.buscarOuFalhar(pautaId);
        voto.setPauta(pauta);

        sessaoService.buscarOuFalhar(sessaoId);

        Sessao sessao = sessaoService.buscarSessaoPorPauta(pautaId, sessaoId);

        return validarEsalvarVoto(sessao, voto);
    }

    private void verificaCpfCadastrado(Voto voto) {

       Associado associado = associadoService.buscarOuFalhar(voto.getAssociado().getId());

        if(!voto.getCpf().equals(associado.getCpf())) {
            throw new CpfDiferenteCadastroException(
                    String.format(CPF_DIFERENTE_CADASTRO, voto.getCpf()));
        }
    }

    @Transactional
    public void excluirVoto(Long votoId) {
        try {
            votoRepository.deleteById(votoId);
            votoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new VotoNaoEncontradaException(votoId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_VOTO_EM_USO,
                    votoId));
        }
    }

    public List<Voto> buscarVotosPorPautaId(Long pautaId) {

        pautaService.buscarOuFalhar(pautaId);

        Optional<List<Voto>> findByPautaId = votoRepository.findByPautaId(pautaId);

        return findByPautaId.get();
    }

    private Voto validarEsalvarVoto(Sessao sessao, Voto voto) {

        validarVoto(sessao, voto);

        return votoRepository.save(voto);
    }

    private void validarVoto(Sessao sessao, Voto voto) {

        validarTempoSessao(sessao);
        verificarVotoExistente(voto);
        validarCpf(voto);
    }

    private void validarTempoSessao(Sessao sessao) {

        LocalDateTime dataLimite = sessao.getDataInicio().plusMinutes(sessao.getTempoAbertura());

        if (LocalDateTime.now().isAfter(dataLimite)) {
            throw new TempoEsgotadoException(String.format(MSG_TEMPO_ESGOTADO));
        }
    }

    private void validarCpf(Voto voto) {

        try {
        ResponseEntity<statusCpfDto> cpfValidacao = consultarCpf(voto);
        if (HttpStatus.OK.equals(cpfValidacao.getStatusCode())) {
            if (UNABLE_TO_VOTE.equalsIgnoreCase(cpfValidacao.getBody().getStatus())) {
                throw new VotoNegadoException(String.format(MSG_VOTO_NEGADO, voto.getCpf()));
            }
          }
        } catch (HttpClientErrorException e) {
                throw new CpfInvalidoException(String.format(MSG_CPF_INVALIDO, voto.getCpf()));
        }
    }

    private void verificarVotoExistente(Voto voto) {

        Optional<Voto> votoPorCpfePauta = votoRepository.
                findByCpfAndPautaId(voto.getCpf(), voto.getPauta().getId());

        if (votoPorCpfePauta.isPresent()) {
            throw new VotoExistenteException(String.format(MSG_VOTO_EXISTENTE, votoPorCpfePauta.get().getCpf()));
        }
    }

    private ResponseEntity<statusCpfDto> consultarCpf(Voto voto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(urlValidacaoCpf.concat("/").concat(voto.getCpf()), HttpMethod.GET, entity,
                statusCpfDto.class);
    }

}
