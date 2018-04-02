package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import les.dominio.EntidadeDominio;
import les.dominio.Pais;

public class PaisDAO extends AbstractJdbcDAO {

	public PaisDAO(String table, String idTable) {
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
		
		
		sql.append("SELECT * FROM tpais");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		ResultSet rs = pst.executeQuery();
		
		List<EntidadeDominio> paises = new ArrayList<EntidadeDominio>();
		
		while(rs.next()){
			
			Pais p = new Pais();
			
			p.setId(rs.getInt("id"));
			p.setNome(rs.getString("nome"));
			paises.add(p);
		
		}
		
		connection.close();
		
		return paises;
		
		
	}
	
	

}
