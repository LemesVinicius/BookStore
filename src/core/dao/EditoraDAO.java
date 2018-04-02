package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import les.dominio.Editora;
import les.dominio.EntidadeDominio;

public class EditoraDAO extends AbstractJdbcDAO{

	public EditoraDAO(String table, String idTable) {
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
		
		
		sql.append("SELECT * FROM teditora");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		ResultSet rs = pst.executeQuery();
		
		List<EntidadeDominio> editoras = new ArrayList<EntidadeDominio>();
		
		while(rs.next()){
			
		Editora e = new Editora();
		
		e.setId(rs.getInt("id"));
		e.setNome(rs.getString("nome"));
		
		editoras.add(e);
		
			
		}	
		
		pst.close();
		connection.commit();
		connection.close();
		
		return editoras;
	}

}
