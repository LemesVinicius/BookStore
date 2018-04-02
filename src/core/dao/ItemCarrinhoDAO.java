package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import les.dominio.EntidadeDominio;
import les.dominio.ItemCarrinho;

public class ItemCarrinhoDAO extends AbstractJdbcDAO{

	public ItemCarrinhoDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {

		ItemCarrinho ic = (ItemCarrinho) entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("INSERT INTO treserva (session,produto,quantidade)");
		sql.append("values(?,?,?)");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		pst.setString(1, ic.getIdsession());
		pst.setInt(2, ic.getId());
		pst.setInt(3, ic.getQuantidade());
		
		pst.executeUpdate();
		
		pst.close();
		connection.commit();
		connection.close();
		
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		
		ItemCarrinho ic = (ItemCarrinho) entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("UPDATE treserva SET quantidade=? ");
		sql.append("WHERE session=? AND produto=?");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		pst.setInt(1, ic.getQuantidade());
		pst.setString(2, ic.getIdsession());
		pst.setInt(3, ic.getId());
		
		pst.executeUpdate();
		
		pst.close();
		connection.commit();
		connection.close();
		
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		ItemCarrinho ic = (ItemCarrinho) entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("SELECT * FROM treserva ");
		sql.append("WHERE session=? AND produto=?");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setString(1, ic.getIdsession());
		pst.setInt(2, ic.getId());
		
		ResultSet rs = pst.executeQuery();
		
		
		List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();
		
		while(rs.next()){
			
			ItemCarrinho item = new ItemCarrinho();
			
			item.setId(rs.getInt("produto"));
			item.setIdsession(rs.getString("session"));
			item.setQuantidade(rs.getInt("quantidade"));
			
			lista.add(item);
			
		}
		
		
		
		return lista;
	}
	
	@Override
	public void excluir(EntidadeDominio entidade) {
		
		ItemCarrinho ic = (ItemCarrinho)entidade;
		
		openConnection();
		PreparedStatement pst=null;		
		StringBuilder sb = new StringBuilder();
		
		
		if(ic.getId() != null){
			
			sb.append("DELETE FROM treserva");
			sb.append(" WHERE session=? AND produto=?");
			
			try {
				connection.setAutoCommit(false);
				pst = connection.prepareStatement(sb.toString());
				pst.setString(1, ic.getIdsession());
				pst.setInt(2, ic.getId());

				pst.executeUpdate();
				connection.commit();
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();			
			}finally{
				
				try {
					pst.close();
					if(ctrlTransaction)
						connection.close();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			
			
		}else{
			
			sb.append("DELETE FROM treserva");
			sb.append(" WHERE session=?");
			
			try {
				connection.setAutoCommit(false);
				pst = connection.prepareStatement(sb.toString());
				pst.setString(1, ic.getIdsession());

				pst.executeUpdate();
				connection.commit();
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();			
			}finally{
				
				try {
					pst.close();
					if(ctrlTransaction)
						connection.close();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			
		}
		
		
		
		
	}

}
