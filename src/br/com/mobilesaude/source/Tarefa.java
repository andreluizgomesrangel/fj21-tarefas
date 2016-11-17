package br.com.mobilesaude.source;

import java.util.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="Tarefa")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity

@NamedQueries({
	
	/**
	 * find
	 * @author andre
	 *
	 */
	//id
	@NamedQuery( 
				name="findTarefById", 
				query="SELECT c FROM Tarefa c WHERE c.id LIKE :id" ), 
	
	
	//finalizado
	@NamedQuery(
				name="findTarefByDone",
				query="SELECT c FROM Tarefa c WHERE c.finalizado LIKE :true"),
	@NamedQuery(
				name="findTarefByUndone",
				query="SELECT c FROM Tarefa c WHERE c.finalizado LIKE :false"),
	
	
	//data_finalizacao
	@NamedQuery(
				name="findTarefByDate",
				query="SELECT c FROM Tarefa c WHERE c.dataFinalizacao LIKE :date"),
	
	//descricao
	@NamedQuery(
				name="findTarefByPatternDescription",
				query="SELECT c FROM Tarefa c WHERE c.descricao LIKE :pattern"),
})



@Table(name="TAREFA")
public class Tarefa { 

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	@Column(name = "idTarefa")
	private long id = 0;
	
	@ManyToOne
	@XmlTransient
    @JoinColumn(name = "idResponsavel") 
	//private long idResp;
	private Responsavel responsavel;

	
	private boolean finalizado = false;   
	private String descricao;
    
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_finalizacao")  
	private Calendar dataFinalizacao;
	
	
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) { 
		this.finalizado = finalizado;
	}

	public Calendar getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(Calendar dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	/*public long getIdResp() {
		return idResp;
	}

	public void setIdResp(long idResp) {
		this.idResp = idResp;
	}*/
	


}
