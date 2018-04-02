package core.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import les.dominio.EntidadeDominio;
import les.dominio.Telefone;

public class TelefoneDAO extends AbstractJdbcDAO{

	protected TelefoneDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		
		Telefone tel = (Telefone)entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("INSERT INTO ttel( telefone, ddd, alias, obs, cliente, tipo)");
		sql.append("values(?,?,?,?,?,?)");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setString(1, tel.getNumero());
		pst.setString(2, tel.getDDD());
		pst.setString(3, tel.getNome());
		pst.setString(4, tel.getObs());
		pst.setInt(5, tel.getCliente());
		pst.setString(6, tel.getTipo());
		
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
		// TODO Auto-generated method stub
		return null;
	}

}
