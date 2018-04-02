package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import les.dominio.CupomTroca;
import les.dominio.EntidadeDominio;
import les.dominio.PagamentoCupom;

public class PagamentoCupomDAO extends AbstractJdbcDAO{

	protected PagamentoCupomDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {


		PagamentoCupom pgto = (PagamentoCupom) entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
				
		sql.append("INSERT INTO tpagamentocupom (valor,pedido,cupom) values(?,?,?)");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setDouble(1, pgto.getValor());
		pst.setInt(2, pgto.getPedido());
		pst.setInt(3, pgto.getCupom().getId());
		
		pst.executeUpdate();
		
		ExchangeCouponDAO cupom = new ExchangeCouponDAO(null, null);
		
		pgto.getCupom().setAtivo(false);
		
		cupom.alterar(pgto.getCupom());		
		
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
