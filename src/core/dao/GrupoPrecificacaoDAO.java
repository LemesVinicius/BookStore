package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import les.dominio.EntidadeDominio;
import les.dominio.GrupoPreco;

public class GrupoPrecificacaoDAO extends AbstractJdbcDAO{

	public GrupoPrecificacaoDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		
		GrupoPreco grp = (GrupoPreco) entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("INSERT INTO tgrupop(nome,valor)");
		sql.append("values(?,?)");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setString(1, grp.getNome());
		pst.setInt(2, grp.getMargemLucro());
		
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
		GrupoPreco grp = (GrupoPreco) entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		if(grp.getId()==null){
		
			sql.append("SELECT * FROM tgrupop");		
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);			
		}
		
		else{
		sql.append("SELECT * FROM tgrupop WHERE id=?");		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);		
		pst.setInt(1, grp.getId());
		}
		
		ResultSet rs = pst.executeQuery();
		
		List<EntidadeDominio> grupos = new ArrayList<EntidadeDominio>();
		
		while(rs.next()){
			GrupoPreco grpo = new GrupoPreco();
			
			grpo.setId(rs.getInt("id"));
			grpo.setNome(rs.getString("nome"));
			grpo.setMargemLucro(rs.getInt("valor"));
			
			grupos.add(grpo);
		
		}
		
		pst.close();
		connection.commit();
		connection.close();
		
		return grupos;
	}

	

}
