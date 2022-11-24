package br.com.gestaovotos.exception;

public class CpfDiferenteCadastroException extends NegocioException {
    private static final long serialVersionUID = 1L;

    public CpfDiferenteCadastroException(String mensagem) {
        super(mensagem);
    }
}
