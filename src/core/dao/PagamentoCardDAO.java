package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import les.dominio.EntidadeDominio;
import les.dominio.PagamentoCard;

public class PagamentoCardDAO extends AbstractJdbcDAO{

	protected PagamentoCardDAO(String table, String idTable) {
		super(table, idTable);
		
		
		
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		
		PagamentoCard pgto = (PagamentoCard)entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
				
		sql.append("INSERT INTO tpagamentocard(valor,pedido,codigocard,codsegcard,vmes,vano,titular,bandeira) values(?,?,?,?,?,?,?,?)");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setDouble(1, pgto.getValor());
		pst.setInt(2, pgto.getPedido());
		pst.setString(3, pgto.getCard().getCodigo());
		pst.setString(4, pgto.getCard().getCodigoseg());
		pst.setString(5, pgto.getCard().getVencimentomes());
		pst.setString(6, pgto.getCard().getVencimentoano());
		pst.setString(7, pgto.getCard().getTitular());
		pst.setInt(8, pgto.getCard().getBandeira().getId());
		
		pst.executeUpdate();
		
		final ResultSet rs = pst.getGeneratedKeys();
		
		if (rs.next()) {
						
			final int lastId = rs.getInt("GENERATED_KEY");
			pgto.setId(lastId);
			
		}
		
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
