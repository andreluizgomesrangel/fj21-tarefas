package br.com.mobilesaude.source;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Responsavel")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity

@NamedQueries({
	
	/**
	 * find
	 * @author andre
	 *
	 */
	@NamedQuery( 
				name="findRespById", 
				query="SELECT c FROM Responsavel c WHERE c.id LIKE :id" ), 
	@NamedQuery( 
				name="findRespByName", 
				query="SELECT c FROM Responsavel c WHERE c.nome LIKE :name" ),  
	@NamedQuery( 
				name="findRespByAge", 
				query="SELECT c FROM Responsavel c WHERE c.idade LIKE :idade" ),
	
})


@Table(name="Responsavel")
public class Responsavel {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "idResponsavel")
	private long id;
	
	private String nome; 
	private int idade;
	
	
	
	//@OneToMany( targetEntity = Tarefa.class ) 
	
	@OneToMany(mappedBy = "responsavel", fetch=FetchType.EAGER)
	private List<Tarefa> tarefas; 
	
	
	
	public Responsavel(){
		tarefas = new ArrayList<Tarefa>();
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	
}
