package br.com.mobilesaude.dao;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.mobilesaude.source.Responsavel;
import br.com.mobilesaude.source.Tarefa;

@Stateless

public class ResponsavelDao {

	@PersistenceContext 
	public EntityManager em;
	private Connection connection;
	
	public ResponsavelDao(){ 
		
	}	
	
	
	//-------------------------------------------------------------------------------------
	
	/**
	 * Usando named queries
	 * buscando responsavel por id
	 * Retornando uma lista
	 * @param id
	 * @return
	 */
	public List<Responsavel> buscaResponsavel(long id){
			
		return this.em
		.createNamedQuery("findRespById", Responsavel.class)  
		.setParameter("id", id)
		.getResultList();
		
	}
	
	/**
	 * Usando named queries
	 * buscando responsavel por nome
	 * @param name
	 * @return Lista de Responsaveis com o nome pesquisado
	 */
	public List<Responsavel> buscaResponsavel(String name){
			
		return this.em
		.createNamedQuery("findRespByName", Responsavel.class) 
		.setParameter("name", name )
		.getResultList();
		
	}
	
	/**
	 * Usando named queries
	 * buscando responsavel por idade
	 * @param age
	 * @return Lista de responsaveis com a idade pesquisada
	 */
	public List<Responsavel> buscaResponsavel(int age){
		
		return this.em
		.createNamedQuery( "findRespByAge", Responsavel.class )
		.setParameter( "idade" , age )
		.getResultList();
		 
	}
	
	/**
	 * Usando em.persist
	 * Inserindo Responsavel no bd
	 * @param nome
	 * @param idade
	 * @return Responsavel inserido
	 */
	public Responsavel insert(String nome, int idade) {
		
		Responsavel responsavel = new Responsavel();
		responsavel.setIdade(idade);
		responsavel.setNome(nome);
		
		em.persist(responsavel);
		
		return responsavel;
	}
	
	/**
	 * Usando em.remove(obj)
	 * Removendo um Responsavel do BD
	 * Para remover o responsavel é necessario remover antes todas as suas tarefas.
	 * @param id
	 * @return Responsavel deletado.
	 */
	public Responsavel delete(long id){
		
		Responsavel responsavel = em.find(Responsavel.class, id); 
		if(responsavel==null) return null;
		
		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		tarefas = responsavel.getTarefas();
		
		for (Tarefa tarefa : tarefas) {
			
			TarefaDao dao = new TarefaDao();
			long id_tarefa = tarefa.getId();
			em.remove(tarefa);
			
		}
		em.remove(responsavel);
		return responsavel;
		
	}
	

	
}
