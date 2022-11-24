package br.com.gestaovotos.exception;

public class SessaoInvalidaException extends NegocioException {
    private static final long serialVersionUID = 1L;

    public SessaoInvalidaException(String mensagem) {

        super(mensagem);
    }
}
