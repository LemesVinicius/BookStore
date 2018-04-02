package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import les.dominio.Cliente;
import les.dominio.EntidadeDominio;
import les.dominio.ItemPedido;
import les.dominio.StatusItemPedido;
import les.dominio.StatusTroca;
import les.dominio.TrocaItem;

public class TrocaItemDAO extends AbstractJdbcDAO{

	public TrocaItemDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		
		TrocaItem ti = (TrocaItem)entidade;
		
		Gson json = new Gson();
		
		System.out.println(json.toJson(ti));
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO ttrocaitem(pedido, motivo, cliente, valor, datainicio, status, item) ");
		sql.append("values(?,?,?,?,?,?,?)");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		 
		LocalDate hoje = LocalDate.now();
		
		pst.setInt(1, ti.getPedido());
		pst.setString(2, ti.getMotivo());
		pst.setInt(3, ti.getCliente().getId());
		pst.setDouble(4, ti.getValor());
		pst.setString(5, hoje.toString());
		pst.setInt(6, 1);
		pst.setInt(7, ti.getItempedido().getItem().getId());
		
		pst.executeUpdate();
		
		ItemPedidoDAO itempedidodao = new ItemPedidoDAO(null, null);		
		ItemPedido itemPedido = new ItemPedido();
		
		itemPedido.setId(ti.getItempedido().getId());
		StatusItemPedido status = new StatusItemPedido();
		status.setId(4);		
		itemPedido.setStatus(status);
		
		itempedidodao.alterar(itemPedido);
		
		pst.close();
		connection.commit();
		connection.close();
		
		
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		
		TrocaItem ti = (TrocaItem)entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("UPDATE ttrocaitem SET status=? WHERE id=?");

		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		pst.setInt(1, ti.getStatus().getId());
		pst.setInt(2, ti.getId());
		
		
		pst.executeUpdate();
		
		pst.close();
		connection.commit();
		connection.close();
		
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {

		TrocaItem ti = (TrocaItem)entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
		if(ti.getCliente() != null){
			
			sql.append("SELECT ttrocaitem.id, ttrocaitem.pedido, ttrocaitem.motivo, ttrocaitem.valor, ");
			sql.append("ttrocaitem.datainicio, ttrocaitem.datafim, tcliente.id, tcliente.nome, tstatustroca.id, tstatustroca.id");
			sql.append(" FROM ((ttrocaitem INNER JOIN tcliente on ttrocaitem.cliente = tcliente.id)INNER JOIN tstatustroca on ttrocaitem.status = tstatustroca.id)");
			sql.append("WHERE cliente=?");
			
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setInt(1, ti.getCliente().getId());
			
		}else if(ti.getStatus() != null){
			
			sql.append("SELECT ttrocaitem.id, ttrocaitem.pedido, ttrocaitem.motivo, ttrocaitem.valor, ");
			sql.append("ttrocaitem.datainicio, ttrocaitem.datafim, tcliente.id, tcliente.nome, tstatustroca.id, tstatustroca.nome");
			sql.append(" FROM ((ttrocaitem INNER JOIN tcliente on ttrocaitem.cliente = tcliente.id)INNER JOIN tstatustroca on ttrocaitem.status = tstatustroca.id)");
			sql.append("WHERE status=?");
			
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setInt(1, ti.getStatus().getId());
			
		}else{
			
			sql.append("SELECT ttrocaitem.id, ttrocaitem.pedido, ttrocaitem.motivo, ttrocaitem.valor, ");
			sql.append("ttrocaitem.datainicio, ttrocaitem.datafim, tcliente.id, tcliente.nome, tstatustroca.id, tstatustroca.nome");
			sql.append(" FROM ((ttrocaitem INNER JOIN tcliente on ttrocaitem.cliente = tcliente.id)INNER JOIN tstatustroca on ttrocaitem.status = tstatustroca.id)");
			
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
		}
		
		List<EntidadeDominio> lista = new ArrayList<>();
		
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()){
			
			TrocaItem troca = new TrocaItem();
			
			troca.setId(rs.getInt(1));
			troca.setPedido(rs.getInt(2));
			troca.setMotivo(rs.getString(3));
			troca.setValor(rs.getDouble(4));
			troca.setDataSolicita(rs.getString(5));
			troca.setDataFinaliza(rs.getString(6));
			
			
			Cliente cli = new Cliente();
			
			cli.setId(rs.getInt(7));
			cli.setNome(rs.getString(8));
			
			StatusTroca st = new StatusTroca();
			st.setId(rs.getInt(9));
			st.setNome(rs.getString(10));
			
			troca.setStatus(st);			
			troca.setCliente(cli);
			
			lista.add(troca);
		}
		
		pst.close();
		connection.commit();
		connection.close();
		
		return lista;
	}

}
