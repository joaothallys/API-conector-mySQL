package br.edu.pucgoias.minhaapi.Model;


import jakarta.persistence.*;


@Entity
@Table(name = "contatos")
public class Contatos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String numero;

    @ManyToOne
    @JoinColumn(name = "etiqueta_id") // Nome da coluna que representará a etiqueta associada
    private Etiquetas etiqueta;

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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Etiquetas getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(Etiquetas etiqueta) {
		this.etiqueta = etiqueta;
	}

    

    // Outros campos e métodos, se necessário
}

