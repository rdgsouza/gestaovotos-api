package br.com.gestaovotos.exception;

public class SessaoNaoEncontradaException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L;

    public SessaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public SessaoNaoEncontradaException(Long sessaoId) {
        this(String.format("Não existe um cadastro de sessão com o id %d", sessaoId));
    }

    public SessaoNaoEncontradaException(Long sessaoId, Long pautaId) {
        this(String.format("Sessão e Pauta devem coincidir. Por favor escolha a pauta para votação correspondente a sessão."));
    }
}
