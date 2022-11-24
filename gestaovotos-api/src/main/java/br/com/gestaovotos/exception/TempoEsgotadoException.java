package br.com.gestaovotos.exception;

public class TempoEsgotadoException extends NegocioException {
    private static final long serialVersionUID = 1L;

    public TempoEsgotadoException(String mensagem) {
        super(mensagem);
    }
}
