package br.com.gestaovotos.exception;

public class VotoNegadoException extends NegocioException {
    private static final long serialVersionUID = 1L;

    public VotoNegadoException(String mensagem) {
        super(mensagem);
    }
}
