package br.com.gestaovotos.domain.service;

import br.com.gestaovotos.domain.model.Sessao;
import br.com.gestaovotos.domain.repository.SessaoRepository;
import br.com.gestaovotos.exception.EntidadeEmUsoException;
import br.com.gestaovotos.exception.SessaoInvalidaException;
import br.com.gestaovotos.exception.SessaoNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;
    @Autowired
    private PautaService pautaService;
    private static final String MSG_SESSAO_EM_USO
            = "Sessão de id %d não pode ser removida, pois está em uso";

    private static final String MSG_SESSAO_INVALIDA
            = "A pauta de id %d é invalida a sessão de id %d. Por favor informe uma pauta compatível com a sessão informada";

    @Transactional
    public Sessao iniciarSessaoVotacao(Sessao sessao) {

        pautaService.buscarOuFalhar(sessao.getPauta().getId());

        return salvar(sessao);
    }

    private Sessao salvar(final Sessao sessao) {
        if (sessao.getDataInicio() == null) {
            sessao.setDataInicio(LocalDateTime.now());
        }
        if (sessao.getTempoAbertura() == null) {
            sessao.setTempoAbertura(1L);
        }

        return sessaoRepository.save(sessao);
    }

    public Sessao buscarOuFalhar(Long sessaoId) {
        return sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new SessaoNaoEncontradaException(sessaoId));
    }

    @Transactional
    public void excluir(Long sessaoId) {
        try {
            sessaoRepository.deleteById(sessaoId);
            sessaoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new SessaoNaoEncontradaException(sessaoId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_SESSAO_EM_USO,
                    sessaoId));
        }

    }

    public Sessao buscarSessaoPorPauta(Long pautaId, Long sessaoId) {

        Optional<Sessao> sessao = sessaoRepository.findByIdAndPautaId(sessaoId, pautaId);

        if(!sessao.isPresent()){
            throw new SessaoNaoEncontradaException(pautaId, pautaId);
        }

        if (!pautaId.equals(sessao.get().getPauta().getId())) {
            throw new SessaoInvalidaException(MSG_SESSAO_INVALIDA);
        }

        return sessao.get();
    }
}
