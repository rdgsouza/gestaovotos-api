package br.com.gestaovotos.exception;

public class HttpClientErrorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public HttpClientErrorException(String mensagem) {
        super(mensagem);
    }
}
