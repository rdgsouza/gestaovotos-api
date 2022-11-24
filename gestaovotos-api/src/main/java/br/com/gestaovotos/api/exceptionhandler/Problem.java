package br.com.gestaovotos.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@Builder
@Getter
@Schema(name = "Problema")
public class Problem {

	@Schema(example = "400")
	private Integer status;

	@Schema(example = "2022-11-23T11:21:50Z")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
	private OffsetDateTime timestamp;

	@Schema(example = "https://gestaovotacao.com.br/dados-invalidos")
	private String type;

	@Schema(example = "Dados inválidos")
	private String title;

	@Schema(example = "um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente")
	private String detail;

	@Schema(example = "um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente")
	private String userMessage;

	@Schema(example = "Lista de objetos ou campos que geraram o erro")
	private List<Object> objects;

	@Getter
	@Builder
	@Schema(example = "ObjetoProblema")
	public static class Object {

		@Schema(example = "cpf")
		private String name;

		@Schema(example = "cpf inválido")
		private String userMessage;
	}

}
