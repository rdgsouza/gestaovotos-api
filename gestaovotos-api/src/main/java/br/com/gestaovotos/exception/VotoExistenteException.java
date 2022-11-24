package br.com.gestaovotos.exception;

public class VotoExistenteException extends NegocioException {
        private static final long serialVersionUID = 1L;

        public VotoExistenteException(String mensagem) {
            super(mensagem);
        }
}
