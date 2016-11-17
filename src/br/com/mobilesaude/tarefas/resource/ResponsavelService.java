package br.com.mobilesaude.tarefas.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.mobilesaude.dao.ResponsavelDao;
import br.com.mobilesaude.dao.TarefaDao;
import br.com.mobilesaude.source.Responsavel;
import br.com.mobilesaude.source.Tarefa;

@SuppressWarnings({ "unused" })
@Path("/ws/service/Responsavel")

public class ResponsavelService { 

	@EJB
	private ResponsavelDao rdao;
	
	public Responsavel notFound(){
		Responsavel r = new Responsavel();
		r.setNome("Não Encontrado!");
		return r;
	}
	
	/**
	 * Encontrar Responsavel por Id
	 * @param id
	 * @return Um Responsavel 
	 * 
	 */
	@POST
	@Path("/findById")
	@Produces( MediaType.APPLICATION_XML )
	//@Consumes("application/x-www-form-urlencoded") 
	public Responsavel consultById(@FormParam("id") Long id){
		
		List<Responsavel> responsavel = rdao.buscaResponsavel(id); 
		if(responsavel.isEmpty()){
			return null;
		}
		return responsavel.get(0);
	}
	
	/**
	 * Encontrar Responsavel por nome
	 * @param name
	 * @return Um Responsavel
	 */
	@POST
	@Path("/findByName")
	@Produces( MediaType.APPLICATION_XML )
	public Responsavel consultByName(@FormParam("name") String name){ 
		
		List<Responsavel> responsaveis = new ArrayList<Responsavel>();
		responsaveis = rdao.buscaResponsavel(name);
		
		if(responsaveis.isEmpty()){
			return null;
		}
		
		return responsaveis.get(0);
	}
	
	/**
	 * Encontrar uma lista de Responsavel
	 * @param name
	 * @return Uma lista de Responsavel ou null
	 */
	@POST
	@Path("/findListByName")
	@Produces( MediaType.APPLICATION_XML )
	public List<Responsavel> consultListByName(@FormParam("name") String name){ 
		
		List<Responsavel> responsaveis = new ArrayList<Responsavel>();
		responsaveis = rdao.buscaResponsavel(name);
		
		if(responsaveis.isEmpty()){
			return null;
		}
		
		return responsaveis;
	}
	
	/**
	 * Encontrar Responsavel por idade
	 * @param age
	 * @return Responsavel da idade pesquisada
	 */
	@POST
	@Path("/findByAge")
	@Produces( MediaType.APPLICATION_XML )
	public Responsavel consultByAge( @FormParam("age") int age ){
		
		List<Responsavel> responsaveis = new ArrayList<Responsavel>(); 
		responsaveis = rdao.buscaResponsavel(age);
		
		if(responsaveis.isEmpty()){ 
			return null;
		}
		
		return responsaveis.get(0);
		
	}
	
	@POST
	@Path("/findListByAge")
	@Produces( MediaType.APPLICATION_XML )
	public List<Responsavel> consultListByAge( @FormParam("age") int age ){
		
		List<Responsavel> responsaveis = new ArrayList<Responsavel>();
		responsaveis = rdao.buscaResponsavel(age);
		
		if( responsaveis.isEmpty() )
			return null;
		
		return responsaveis;
		
	}
	
	/**
	 * Usando em.persist
	 * Inserindo Responsavel no bd
	 * @param nome
	 * @param idade
	 * @return Responsavel inserido
	 */
	@POST
	@Path("/insert")
	@Produces( MediaType.APPLICATION_XML )
	public Responsavel insert( @FormParam("name") String name,
							   @FormParam("age")  int 	 age ){
		
		Responsavel responsavel = new Responsavel();
		responsavel = rdao.insert(name, age);
		return responsavel;
	}
	
	/**
	 * Usando em.remove(obj)
	 * Removendo um Responsavel do BD
	 * Para remover o responsavel é necessario remover antes todas as suas tarefas.
	 * @param id
	 * @return Responsavel deletado.
	 */
	@POST
	@Path("delete")
	@Produces( MediaType.APPLICATION_XML )
	public Responsavel delete( @FormParam("id") long id ){
		
		Responsavel responsavel = rdao.delete(id);
		
		return responsavel;
	}
}
