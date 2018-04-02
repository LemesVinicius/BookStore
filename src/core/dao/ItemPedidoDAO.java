package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import les.dominio.EntidadeDominio;
import les.dominio.Item;
import les.dominio.ItemPedido;
import les.dominio.StatusItemPedido;

public class ItemPedidoDAO extends AbstractJdbcDAO{

	protected ItemPedidoDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		
		ItemPedido it = (ItemPedido)entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
				
		sql.append("INSERT INTO titempedido(produto,quantidade,valor,status,pedido)");
		sql.append("values(?,?,?,?,?)");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		
		pst.setInt(1, it.getItem().getId());
		pst.setInt(2, 1);
		pst.setDouble(3, it.getItem().getValor());
		pst.setInt(4, 1);
		pst.setInt(5, it.getPedido());
		
		
		pst.executeUpdate();
		
		pst.close();
		connection.commit();
		connection.close();
		
		
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {

		ItemPedido it = (ItemPedido)entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
				
		sql.append("UPDATE titempedido SET status=? WHERE id=?");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		pst.setInt(1, it.getStatus().getId());
		pst.setInt(2, it.getId());
		
		
		pst.executeUpdate();
		
		pst.close();
		connection.commit();
		connection.close();

		
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		
		ItemPedido item = (ItemPedido)entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT titempedido.produto, titempedido.id, titempedido.quantidade, titempedido.valor, titempedido.pedido,tstatusitempedido.id,tstatusitempedido.nome "
				+ "FROM (titempedido INNER JOIN tstatusitempedido ON titempedido.status = tstatusitempedido.id )"
				+ " WHERE pedido=? ");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setInt(1, item.getPedido());
		
		ResultSet rs = pst.executeQuery();
				
		List<EntidadeDominio> itens = new ArrayList<>();
		
		while(rs.next()){
			
			ItemDAO itDAO = new ItemDAO(null, null);
			Item it = new Item();
			
			it.setId(rs.getInt(1));
			it = (Item)itDAO.consultar(it).get(0);
			
			ItemPedido i = new ItemPedido();			
			i.setId(rs.getInt(2));
			i.setItem(it);
			i.setQuantidade(rs.getInt(3));
			i.setValor(rs.getDouble(4));
			i.setPedido(rs.getInt(5));
			
			StatusItemPedido status = new StatusItemPedido();			
			status.setId(rs.getInt(6));
			status.setNome(rs.getString(7));
			
			i.setStatus(status);
			
			itens.add(i);
						
		}
		
		
		return itens;
	}

}
