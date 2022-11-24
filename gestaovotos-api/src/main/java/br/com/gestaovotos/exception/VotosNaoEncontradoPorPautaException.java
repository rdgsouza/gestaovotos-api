package br.com.gestaovotos.exception;

public class VotosNaoEncontradoPorPautaException extends NegocioException {
    private static final long serialVersionUID = 1L;

    public VotosNaoEncontradoPorPautaException(String mensagem) {
        super(mensagem);
    }
}
