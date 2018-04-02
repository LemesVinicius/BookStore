package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import les.core.app.DadosAnalise;
import les.dominio.EntidadeDominio;

public class DadosAnaliseDAO extends AbstractJdbcDAO{

	public DadosAnaliseDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {

		DadosAnalise dados = (DadosAnalise)entidade;

		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("SELECT coalesce(SUM(titempedido.quantidade),0),tcategoria.nome ");
		sql.append("FROM (((((( titempedido INNER JOIN titem on titempedido.produto = titem.id) ");
		sql.append("INNER JOIN tlivro on titem.produto = tlivro.id) ");
		sql.append("INNER JOIN tlivcat on tlivro.id = tlivcat.idlivro) ");
		sql.append("INNER JOIN tcategoria on tlivcat.idcategoria = tcategoria.id) ");
		sql.append("INNER JOIN tpedido on titempedido.pedido = tpedido.id) ");
		sql.append("INNER JOIN tcliente on tpedido.cliente = tcliente.id) ");
		sql.append("WHERE 1=1");
		
		if( dados.getCategorias() != null){
			
			for(int i = 0; i < dados.getCategorias().size(); i++){
				
				if(i==0){
					sql.append(" AND ");
				}else{
					sql.append(" OR ");
				}
				
				sql.append("tcategoria.nome = '"+dados.getCategorias().get(i)+"'");
				
			}		
			
		}
		
		if(dados.getDatainicio() != null && dados.getDatafim() != null){
			
			sql.append(" AND (tpedido.data between STR_TO_DATE('"+dados.getDatainicio()+"','%d/%m/%Y') AND STR_TO_DATE('"+dados.getDatafim()+"','%d/%m/%Y'))");
			
		}
		
		sql.append(" AND tcliente.genero = 0");
		
		
		sql.append(" GROUP BY tcategoria.nome ");
		
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		ResultSet rs = pst.executeQuery();
		
		
		List<EntidadeDominio> listadados = new ArrayList<EntidadeDominio>();
		
		ArrayList<String> labels = new ArrayList<>();
		ArrayList<Integer> datatotal = new ArrayList<>();
		ArrayList<Integer> datamasculino = new ArrayList<>();
		ArrayList<Integer> datafeminino = new ArrayList<>();
		
		while(rs.next()){
			
		
		datamasculino.add(rs.getInt(1));
			
		}
		
		sql = new StringBuilder();		
		
		sql.append("SELECT coalesce(SUM(titempedido.quantidade),0),tcategoria.nome ");
		sql.append("FROM((((((titempedido INNER JOIN titem on titempedido.produto = titem.id) ");
		sql.append("INNER JOIN tlivro on titem.produto = tlivro.id) ");
		sql.append("INNER JOIN tlivcat on tlivro.id = tlivcat.idlivro) ");
		sql.append("INNER JOIN tcategoria on tlivcat.idcategoria = tcategoria.id) ");
		sql.append("INNER JOIN tpedido on titempedido.pedido = tpedido.id) ");
		sql.append("INNER JOIN tcliente on tpedido.cliente = tcliente.id) ");
		sql.append("WHERE 1=1");
		
		if( dados.getCategorias() != null){
		for(int i = 0; i < dados.getCategorias().size(); i++){
			
			if(i==0){
				sql.append(" AND ");
			}else{
				sql.append(" OR ");
			}
			
			sql.append("tcategoria.nome = '"+dados.getCategorias().get(i)+"'");
			
		}	
		}
		
		if(dados.getDatainicio() != null && dados.getDatafim() != null){
			
			sql.append(" AND (tpedido.data between STR_TO_DATE('"+dados.getDatainicio()+"','%d/%m/%Y') AND STR_TO_DATE('"+dados.getDatafim()+"','%d/%m/%Y'))");
			
		}
		
		
		sql.append(" AND tcliente.genero = 1");
		
		
		sql.append(" GROUP BY tcategoria.nome ");
		
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		 rs = pst.executeQuery();
		 
		 while(rs.next()){
				
				datafeminino.add(rs.getInt(1));
					
		}
		 

		 sql = new StringBuilder();		
			
			sql.append("SELECT coalesce(SUM(titempedido.quantidade),0),tcategoria.nome ");
			sql.append("FROM((((((titempedido INNER JOIN titem on titempedido.produto = titem.id) ");
			sql.append("INNER JOIN tlivro on titem.produto = tlivro.id) ");
			sql.append("INNER JOIN tlivcat on tlivro.id = tlivcat.idlivro) ");
			sql.append("INNER JOIN tcategoria on tlivcat.idcategoria = tcategoria.id) ");
			sql.append("INNER JOIN tpedido on titempedido.pedido = tpedido.id) ");
			sql.append("INNER JOIN tcliente on tpedido.cliente = tcliente.id) ");
			sql.append("WHERE 1=1");
			
			if( dados.getCategorias() != null){
			for(int i = 0; i < dados.getCategorias().size(); i++){
				
				if(i==0){
					sql.append(" AND ");
				}else{
					sql.append(" OR ");
				}
				
				sql.append("tcategoria.nome = '"+dados.getCategorias().get(i)+"'");
				
			}	
			}
			
			if(dados.getDatainicio() != null && dados.getDatafim() != null){
				
				sql.append(" AND (tpedido.data between STR_TO_DATE('"+dados.getDatainicio()+"','%d/%m/%Y') AND STR_TO_DATE('"+dados.getDatafim()+"','%d/%m/%Y'))");
				
			}
						
			
			sql.append(" GROUP BY tcategoria.nome ");
			
			
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
			 rs = pst.executeQuery();
			 
			 while(rs.next()){
					
				 	labels.add(rs.getString(2));
					datatotal.add(rs.getInt(1));
						
			}
			 
			 
		ArrayList<ArrayList<Integer>> alldate = new ArrayList<>();
		
		alldate.add(datatotal);
		alldate.add(datamasculino);
		alldate.add(datafeminino);
		
		dados.setData(alldate);
		dados.setLabel(labels);
		
		listadados.add(dados);
		
		return listadados;
	}
	
	

}
