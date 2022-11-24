package br.com.gestaovotos.exception;

public class AssociadoNaoEncontradaException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L;

    public AssociadoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public AssociadoNaoEncontradaException(Long sessaoId) {
        this(String.format("Não existe um cadastro de associado com o id %d", sessaoId));
    }

}
