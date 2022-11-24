package br.com.gestaovotos.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pauta {

	@Schema(example = "1")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Id
	private Long id;

	@Schema(example = "Ref. acordo coletivo do reajuste de 50% no salário dos programadores")
	@Column(nullable = false)
	private String descricao;

}
