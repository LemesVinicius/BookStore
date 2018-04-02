package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import les.dominio.EntidadeDominio;
import les.dominio.SubCategoria;

public class SubCategoriaDAO extends AbstractJdbcDAO{

	public SubCategoriaDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {

		SubCategoria sub = (SubCategoria) entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("INSERT INTO tsubcategoria(nome)");
		sql.append("values(?)");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setString(1, sub.getNome());
		
		pst.executeUpdate();
		
		pst.close();
		connection.commit();
		connection.close();
		
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {

		SubCategoria scat = (SubCategoria) entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
		if(scat.getId()==null){
			sql.append("SELECT * FROM tsubcat");
			
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
		}		
		else{
		sql.append("SELECT * FROM tsubcat WHERE id=?");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setInt(1, scat.getId());
		}
		
		
		ResultSet rs = pst.executeQuery();
		
		List<EntidadeDominio> subcategorias = new ArrayList<EntidadeDominio>();
		
		while(rs.next()){
			
			SubCategoria sc = new SubCategoria();
			sc.setId(rs.getInt("id"));
			sc.setNome(rs.getString("nome"));
			subcategorias.add(sc);
			
		}
		
		pst.close();
		connection.commit();
		connection.close();
		
		return subcategorias;
	}

	

}
