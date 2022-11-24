package br.com.gestaovotos.exception;

public class VotoNaoEncontradaException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L;

    public VotoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public VotoNaoEncontradaException(Long votoId) {
        this(String.format("Não existe um cadastro de voto com o id %d", votoId));
    }

}

