package br.com.mobilesaude.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.mobilesaude.source.Responsavel;
import br.com.mobilesaude.source.Tarefa;

@SuppressWarnings("deprecation")
@Stateless

public class TarefaDao {

	@PersistenceContext
	public EntityManager em;
	private Connection connection;

	public TarefaDao() {
		// this.connection = new ConnectionFactory().getConnection();
		// em.getTransaction().begin();
	}

	// converte string dd-mm-aaaa para Calendar
	public Calendar converter(String data_string) {

		String[] data = data_string.split("-");
		int dia = Integer.parseInt(data[0]);
		int mes = Integer.parseInt(data[1]);
		int ano = Integer.parseInt(data[2]);
		Calendar c = Calendar.getInstance();
		c.set(ano, mes - 1, dia);

		return c;
	}

	/*public Tarefa add(String descricao, String finalizacao, boolean finalizado, long idResponsavel) throws EJBAccessException{
		ResponsavelDao rdao = new ResponsavelDao();
		
		Responsavel responsavel = rdao.get(idResponsavel);
		if (responsavel == null){ 
			//throw new EJBAccessException("Não encontrado!");
		} 

		Tarefa tarefa = new Tarefa();
		tarefa.setDescricao(descricao);
		tarefa.setDataFinalizacao(converter(finalizacao));
		tarefa.setFinalizado(finalizado);
		tarefa.setResponsavel(responsavel);
		em.persist(tarefa);
		

		return tarefa;
	}*/

	public Tarefa get(long id) {

		Tarefa tarefa = em.find(Tarefa.class, id);
		if (tarefa != null) {
			return tarefa;
		}
		return new Tarefa();

	}

	public Tarefa alteraDescricao(long id, String novaDescricao) {

		Tarefa tarefa = em.find(Tarefa.class, id);

		if (tarefa != null)

			tarefa.setDescricao(novaDescricao);

		return tarefa;

	}

	

	

	/**
	 * Usando named queries buscando tarefa por descrição
	 * 
	 * @param descricao
	 * @return
	 */
	public List<Tarefa> buscaTarefa(String descricao) {

		return this.em.createNamedQuery("findByDescricao", Tarefa.class)
				.setParameter("descricao", descricao)
				.getResultList();

	}

	public List<Tarefa> getLista() {
		// TODO Auto-generated method stub
		try {
			List<Tarefa> tarefas = new ArrayList<Tarefa>();
			PreparedStatement stmt = this.connection.prepareStatement("select * from TAREFA");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto Contato
				Tarefa tarefa = new Tarefa();
				tarefa.setId(rs.getLong("idTarefa"));
				tarefa.setDescricao(rs.getString("descricao"));
				tarefa.setFinalizado(rs.getBoolean("finalizado"));

				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("data_finalizacao"));
				tarefa.setDataFinalizacao(data);

				// adicionando o objeto à lista
				tarefas.add(tarefa);
			}
			rs.close();
			stmt.close();
			return tarefas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void adiciona(Tarefa tarefa) {

		String sql = "insert into TAREFA " + "(data_finalizacao,descricao,finalizado)" + " values (?,?,?)";
		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			stmt.setString(2, tarefa.getDescricao());
			stmt.setDate(1, new Date(tarefa.getDataFinalizacao().getTimeInMillis()));
			stmt.setBoolean(3, tarefa.isFinalizado());
			// executa
			stmt.execute();
			System.out.println("Gravado!");
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public EntityManager getEm() {
		return em;
	}
//------------------------------------------------------------------------------------
	/**
	 * Uso de named query
	 * Encontrar Tarefas com o id passado no parametro
	 * @param id
	 * @return lista de tarefas com o id pesquisado
	 */
	public List<Tarefa> buscaTarefa(long id) {

		return this.em.createNamedQuery("findTarefById", Tarefa.class)
				.setParameter("id", id)
				.getResultList();

	}
	
	/**
	 * Uso de named query
	 * Encontrar tarefas prontas
	 * @return Lista de Tarefas prontas
	 */
	public List<Tarefa> findDone() {

		return this.em.createNamedQuery("findTarefByDone", Tarefa.class) 
				.setParameter("true", true)
				.getResultList();

	}
	
	/**
	 * Uso de named query
	 * Encontrar tarefas a fazer
	 * @return Lista de Tarefas a fazer
	 */
	public List<Tarefa> findUndone() {

		return this.em.createNamedQuery("findTarefByUndone", Tarefa.class) 
				.setParameter("false", false)
				.getResultList();

	}
	
	/**
	 * Uso de named query
	 * Encontrar tarefas de uma data
	 * @param data
	 * @return Lista de Tarefas de uma data
	 */
	public List<Tarefa> findByDate(String data) {
		
		return this.em.createNamedQuery( "findTarefByDate", Tarefa.class )
				.setParameter("date", converter(data))
				.getResultList();
	}
	
	/**
	 * Uso de native query
	 * Encontrar tarefas de um dado dia
	 * @param day
	 * @return Lista de tarefas de um dia
	 */
	public List<Tarefa> findByDay(String day, String month, String year) {

		Query q = em.createNativeQuery("select * from TAREFA where Day(data_finalizacao)='"
		+Integer.parseInt(day)+"' "
		+"and Month(data_finalizacao)='"
		+Integer.parseInt(month)+"' "
		+"and Year(data_finalizacao)='"
		+Integer.parseInt(year)+"' ", Tarefa.class);
		List<Tarefa> tarefas = q.getResultList();
		
		return tarefas;
	}
	
	/**
	 * Uso de native query
	 * Encontrar tarefas de um dado mês
	 * @param mês
	 * @return Lista de tarefas de um mês
	 */ 
	public List<Tarefa> findByMonth(String month, String year) {
		
		Query q = em.createNativeQuery("select * from TAREFA where Month(data_finalizacao)='"+Integer.parseInt(month)+"' and Year(data_finalizacao)='"+Integer.parseInt(year)+"'", Tarefa.class);
		List<Tarefa> tarefas = q.getResultList();
		
		
		
		return tarefas;
	}
	
	/**
	 * Uso de native query
	 * Encontrar tarefas de um dado ano
	 * @param ano
	 * @return Lista de tarefas de um ano
	 */
	public List<Tarefa> findByYear(String year) {
		
		Query q = em.createNativeQuery("select * from TAREFA where Year(data_finalizacao)='"+Integer.parseInt(year)+"'", Tarefa.class); 
		List<Tarefa> tarefas = q.getResultList();
		
		return tarefas;
	}
	
	/**
	 * Uso de native query
	 * Encontrar tarefas de hoje
	 * @return Lista de tarefas de hoje
	 */
	public List<Tarefa> findByToday(){
		
		Query q = em.createNativeQuery("select * from TAREFA where Day(data_finalizacao) = Day(Now())", Tarefa.class);
		List<Tarefa> tarefas = q.getResultList();
		
		return tarefas;
		
	}
	
	/**
	 * Uso de Native query
	 * Encontrar tarefas atrasadas
	 * @return Lista de tarefas com atraso.
	 */
	public List<Tarefa> findByLate(){
		
		Query q = em.createNativeQuery("select * from TAREFA where data_finalizacao < Now() and finalizado=0"
				+ " and Day(data_finalizacao) <> Day(Now()) ", Tarefa.class);
		List<Tarefa> tarefas = q.getResultList();
		
		return tarefas;
		
	}
	
	/**
	 * Uso de Named Querie
	 * Encontrar tarefas por descricao a partir de um padrão
	 * @param pattern
	 * @return Lista de tarefas com uma descrição no dado padrão 
	 */
	public List<Tarefa> findByPatternDescription(String pattern){
		
		return this.em.createNamedQuery( "findTarefByPatternDescription", Tarefa.class )
				.setParameter("pattern", "%" + pattern + "%")  
				.getResultList();
		
	}
	
	/**
	 * Uso de entity mannager.find para encontrar o responsavel da tarefa. 
	 * Caso esse responsavel não exista restornar null.
	 * Se ele existir inserir a tarefa no BD.
	 * @param data_finalizacao
	 * @param descricao
	 * @param finalizado
	 * @param idResponsavel
	 * @return Tarefa inserida no BD em caso de sucesso.
	 */
	public Tarefa insert(String data_finalizacao, String descricao, boolean finalizado, long idResponsavel) {
		
		//verificar se o responsavel de idResponsavel existe
		ResponsavelDao rdao = new ResponsavelDao();
		List<Responsavel> responsavel = new ArrayList<Responsavel>();
		
		Responsavel resp = em.find(Responsavel.class, idResponsavel);
		if( resp==null ) return null;
	
		Tarefa tarefa = new Tarefa();
		tarefa.setDataFinalizacao( converter(data_finalizacao) ); 
		tarefa.setDescricao(descricao);
		tarefa.setFinalizado(finalizado);
		tarefa.setResponsavel(resp);
		//tarefa.setIdResp(idResponsavel); 
		em.persist(tarefa);
		
		return tarefa;
	}
	
	/**
	 * Uso do entity manager.remove
	 * Deletar tarefa selecionada pelo id
	 * @param  id
	 * @return Objeto Tarefa deletado em caso de sucesso. 
	 * 		   Caso contrario null.
	 */
	public Tarefa delete(long id) {

		Tarefa tarefa = em.find(Tarefa.class, id);
		if(tarefa==null) return null;
		em.remove(tarefa);
		return tarefa;
	}
	
	/**
	 * Uso...
	 * Encontrar outras tarefas do reponsavel dessa tarefa.
	 * @param id
	 * @return lista de Tarefas do responsavel.
	 */
	public List<Tarefa> othersTasks(long id){
		
		List<Tarefa> tarefa = buscaTarefa(id);
		if(tarefa.isEmpty()) return null;
		
		System.out.println( tarefa.get(0).getDescricao() );
		System.out.println( tarefa.get(0).getDescricao() );
		System.out.println( tarefa.get(0).getDescricao() );
		System.out.println( tarefa.get(0).getDescricao() );
		System.out.println( tarefa.get(0).getDescricao() );
		System.out.println( tarefa.get(0).getDescricao() );
		System.out.println( tarefa.get(0).getDescricao() );
		System.out.println( tarefa.get(0).getDescricao() );
 		
		//verificar se o responsavel de idResponsavel existe. Ele sempre existirá.
		/*ResponsavelDao rdao = new ResponsavelDao();
		List<Responsavel> responsavel = new ArrayList<Responsavel>();
		
		Responsavel resp = em.find(Responsavel.class, idResp);   
		if( resp==null ) return null;*/
		
		return tarefa;
		
		
	}
}
