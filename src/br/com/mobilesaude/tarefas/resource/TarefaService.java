package br.com.mobilesaude.tarefas.resource;

import java.util.ArrayList;
import java.util.Calendar;
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

/**
 * @author andre
 * 
 * @Path("/inserir") 			| descricao, data_finalizacao, finalizado, idResponsavel
 * @Path("/consultar") 			| id
 * @Path("/deletar") 			| id
 * @Path("/alterar_descricao")	| id, nova_descricao
 * @Path("/query") 				| 
 * @Path("/findById") 			| id 
 * @Path("/findByDescription") 	| description
 * @Path("/findByDone") 		|
 * @Path("/findByUndone") 		|
 * 
 */

@SuppressWarnings({ "unused" })
@Path("/ws/service/Tarefa")
public class TarefaService  {

	@EJB
	private TarefaDao dao;  
	
	public Tarefa notFound(){
		Tarefa t = new Tarefa();
		t.setDescricao("Não Encontrada!");  
		return t;
	}

	
	//----------------------------------------------------------------------------
	/**
	 * Uso de named query
	 * Encontrar uma Tarefa por Id
	 * @param id
	 * @return Uma tarefa com o Id pesquisado
	 */
	@POST
	@Path("/findById")
	@Produces( MediaType.APPLICATION_XML )
	public Tarefa namedQuery( @FormParam("id") int id ){
		 
		
		List<Tarefa> tarefa = dao.buscaTarefa(id) ;
		if( tarefa.isEmpty() ){
			return null;
		}
		return tarefa.get(0);
	}
	
	/**
	 * Uso de named query
	 * Econtrar tarefas feitas
	 * @return Lista de Tarefas feitas
	 */
	@POST
	@Path("/findByDone")
	@Produces( MediaType.APPLICATION_XML )
	public List<Tarefa> findByDone(){
		
		List<Tarefa> tarefas = dao.findDone();
		if(tarefas.isEmpty()) return null; 
		return tarefas;
	}
	
	/**
	 * Uso de named query
	 * Encontrar tarefas a fazer
	 * @return Lista de Tarefas a fazer
	 */
	@POST
	@Path("/findByUndone")
	@Produces( MediaType.APPLICATION_XML )
	public List<Tarefa> findByUndone(){
		
		List<Tarefa> tarefas = dao.findUndone(); 
		if(tarefas.isEmpty()) return null;
		return tarefas;
	}
	
	/**
	 * Uso de named query
	 * Encontrar tarefas de uma data
	 * @param date
	 * @return Lista de tarefas de uma data
	 */
	@POST
	@Path("/findByDate")
	@Produces( MediaType.APPLICATION_XML )
	public List<Tarefa> findByDate(@FormParam("date") String date ){
		
		List<Tarefa> tarefas = dao.findByDate(date);
		if(tarefas.isEmpty()) return null;
		return tarefas;
		
	}
	
	/**
	 * Uso de native query
	 * Encontrar tarefas de um dado dia
	 * @param day
	 * @return Lista de tarefas de um dia
	 */
	@POST
	@Path("findByDay")
	@Produces( MediaType.APPLICATION_XML )
	public List<Tarefa> findByDay(@FormParam("day"  )   String day,
								  @FormParam("month")   String month,
								  @FormParam("year" )   String year){
		
		List<Tarefa> tarefas = dao.findByDay(day, month, year);
		if(tarefas.isEmpty()) return null;
		return tarefas;
		
	}
	
	/**
	 * Uso de native query
	 * Encontrar tarefas de um dado mês
	 * @param mês
	 * @return Lista de tarefas de um mês
	 */
	@POST
	@Path("findByMonth")
	@Produces( MediaType.APPLICATION_XML )
	public List<Tarefa> findByMonth(@FormParam("month") String month, 
									@FormParam("year") 	String year){
		
		List<Tarefa> tarefas = dao.findByMonth(month, year); 
		if(tarefas.isEmpty()) return null;
		return tarefas;
		
	}
	
	/**
	 * Uso de native query
	 * Encontrar tarefas de um dado ano
	 * @param ano
	 * @return Lista de tarefas de um ano
	 */
	@POST
	@Path("findByYear")
	@Produces( MediaType.APPLICATION_XML )
	public List<Tarefa> findByYear(@FormParam("year") String year){
		
		List<Tarefa> tarefas = dao.findByYear(year);
		if(tarefas.isEmpty()) return null;
		return tarefas;
		
	}	
	
	/**
	 * Uso de native query
	 * Encontrar tarefas de hoje
	 * @return Lista de tarefas de hoje
	 */
	@POST
	@Path("findByToday")
	@Produces( MediaType.APPLICATION_XML )
	public List<Tarefa> findByToday(){
		
		List<Tarefa> tarefas = dao.findByToday();
		if(tarefas.isEmpty()) return null;
		return tarefas;
		
	}
	
	/**
	 * Uso de Native query
	 * Encontrar tarefas atrasadas
	 * @return Lista de tarefas com atraso.
	 */
	@POST
	@Path("findByLate")
	@Produces( MediaType.APPLICATION_XML )
	public List<Tarefa> findByLate(){
		
		List<Tarefa> tarefas = dao.findByLate();
		if(tarefas.isEmpty()) return null;
		return tarefas;
		
	}
	
	/**
	 * Uso de Named Querie
	 * Encontrar tarefas por descricao a partir de um padrão
	 * @param pattern
	 * @return Lista de tarefas com uma descrição no dado padrão 
	 */
	@POST
	@Path("findByPatternDescription")
	@Produces( MediaType.APPLICATION_XML )
	public List<Tarefa> findByLate(@FormParam("pattern") String pattern){ 
		
		List<Tarefa> tarefas = dao.findByPatternDescription(pattern);
		if(tarefas.isEmpty()) return null;
		return tarefas;
		
	}
	
	/**
	 * Uso de entity mannager.find.
	 * @param data_finalizacao
	 * @param descricao
	 * @param finalizado
	 * @param idResponsavel
	 * @return Tarefa inserida no BD em caso de sucesso. Caso contrario null.
	 */
	@POST
	@Path("/insert")
	@Produces( MediaType.APPLICATION_XML )
	public Tarefa insert( @FormParam("data_finalizacao") String data_finalizacao,
						  @FormParam("descricao")        String descricao,
						  @FormParam("finalizado")		 int finalizado,
						  @FormParam("idResponsavel") 	 int idResponsavel) {	
	 
		Tarefa tarefa = dao.insert(data_finalizacao, descricao, finalizado!=0, (long)idResponsavel);	
	
		return tarefa;
		
	}
	
	/**
	 * Uso do entity manager.remove
	 * Deletar tarefa selecionada pelo id
	 * @param  id
	 * @return Objeto Tarefa deletado em caso de sucesso. 
	 * 		   Caso contrario null.
	 */
	@POST
	@Path("/delete")
	@Produces( MediaType.APPLICATION_XML )
	public Tarefa delete( @FormParam("id") int id ){
		
		Tarefa tarefa = dao.delete(id);
		
		return tarefa;
		
	}
	
	/**
	 * Uso...
	 * Encontrar outras tarefas do reponsavel dessa tarefa.
	 * @param id
	 * @return lista de Tarefas do responsavel.
	 */
	@POST
	@Path("findOtherTasks")
	@Produces( MediaType.APPLICATION_XML )
	public Tarefa findOtherTasks( @FormParam("id") long id ){
		
		List<Tarefa> t = dao.othersTasks(id);
		if(t.isEmpty()) return null;
		
		return t.get(0);
		
		//return new Tarefa();
		
	}
}
