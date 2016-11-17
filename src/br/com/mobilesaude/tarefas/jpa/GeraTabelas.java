package br.com.mobilesaude.tarefas.jpa;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;  
 
import br.com.mobilesaude.dao.TarefaDao; 	
import br.com.mobilesaude.source.Tarefa; 
public class GeraTabelas {

	public static void main(String[] args) { 
		
		TarefaDao dao = new TarefaDao();
		dao.add("novo", "01-06-1993", true);
		
	  }
	
}
