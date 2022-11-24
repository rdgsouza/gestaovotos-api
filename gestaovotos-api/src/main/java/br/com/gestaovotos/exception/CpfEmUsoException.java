package br.com.gestaovotos.exception;

public class CpfEmUsoException extends NegocioException {
    private static final long serialVersionUID = 1L;

    public CpfEmUsoException(String mensagem) {
        super(mensagem);
    }
}
