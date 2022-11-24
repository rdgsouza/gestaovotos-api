package br.com.gestaovotos.api.exceptionhandler;
import lombok.Getter;

@Getter
public enum ProblemType {

	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel","Mensagem incompreensível"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso","Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	PARAMETRO_INVALIDO("/parametro-invalido","Parâmetro inválido"),
	ERRO_DE_SISTEMA("/erro-de-sistema","Erro de sistema"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"), 
	TAMANHO_MAX_DO_ARQUIVO_EXCEDIDO("/tamanho-maximo-do-arquivo-excedido", "Tamanho máximo do arquivo excedido"),
	ACESSO_NEGADO("/acesso-negado", "Acesso negado"); //ACESSO_NEGADO("/acesso-negado", "Acesso negado"), // Aula: https://app.algaworks.com/aulas/2276/desafio-tratando-accessdeniedexception-no-exceptionhandler

	private String uri;
	private String title;
	
	ProblemType(String path, String title) {
		this.uri = "https://gestaomensalidade.com.br" + path;
		this.title = title;
	}
}