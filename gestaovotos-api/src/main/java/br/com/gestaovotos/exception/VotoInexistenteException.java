package br.com.gestaovotos.exception;

public class VotoInexistenteException extends NegocioException {
        private static final long serialVersionUID = 1L;

        public VotoInexistenteException(String mensagem) {

            super(mensagem);
        }
}
