package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import les.dominio.EntidadeDominio;
import les.dominio.Estado;

public class EstadoDAO extends AbstractJdbcDAO{

	public EstadoDAO(String table, String idTable) {
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
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("SELECT * FROM testado");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		ResultSet rs = pst.executeQuery();
		
		List<EntidadeDominio> estados = new ArrayList<EntidadeDominio>();
		
		while(rs.next()){
			
			Estado e = new Estado();
			
			e.setId(rs.getInt("id"));
			e.setNome(rs.getString("nome"));
			estados.add(e);
		
		}
		
		connection.close();
		
		return estados;
		
	
	}

	
	
	
}
