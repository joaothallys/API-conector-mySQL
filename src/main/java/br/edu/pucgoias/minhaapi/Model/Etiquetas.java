package br.edu.pucgoias.minhaapi.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "etiquetas")
public class Etiquetas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

    

    // Outros campos e métodos, se necessário
}
