package br.com.gestaovotos.exception;

public class CpfInvalidoException extends NegocioException {
    private static final long serialVersionUID = 1L;

    public CpfInvalidoException(String mensagem) {
        super(mensagem);
    }
}
