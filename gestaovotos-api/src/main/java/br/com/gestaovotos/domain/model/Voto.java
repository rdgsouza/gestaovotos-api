package br.com.gestaovotos.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Voto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Id
    private Long id;

    @Column(nullable = false)
    private Boolean opcaoVoto;

    @Column(nullable = false)
    private String cpf;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pauta pauta;

    @ManyToOne(fetch = FetchType.EAGER)
    private Associado associado;
}
