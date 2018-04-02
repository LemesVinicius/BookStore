package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import les.dominio.Cliente;
import les.dominio.EntidadeDominio;
import les.dominio.Pedido;
import les.dominio.StatusPedido;
import les.dominio.StatusTroca;
import les.dominio.TrocaPedido;

public class TrocaPedidoDAO extends AbstractJdbcDAO{

	

	public TrocaPedidoDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		TrocaPedido trocaped = (TrocaPedido)entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO ttrocapedido(pedido,datainicio,status,valor,motivo,cliente)");
		sql.append(" Values(?,?,?,?,?,?)");
		
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		LocalDate hoje = LocalDate.now();
		
		pst.setInt(1, trocaped.getPedido().getId());
		pst.setString(2, hoje.toString());
		pst.setInt(3, trocaped.getStatus().getId());
		pst.setDouble(4, trocaped.getValor());
		pst.setString(5, trocaped.getMotivo());
		pst.setInt(6, trocaped.getCliente().getId());
		
		pst.executeUpdate();
		
		
		StatusPedido status = new StatusPedido();
		status.setId(5);
		
		Pedido pedido = new Pedido();
		
		pedido.setId(trocaped.getPedido().getId());
		pedido.setStatus(status);
		
		PedidoDAO peddao = new PedidoDAO(null, null);
		
		peddao.alterar(pedido);
		
		
		pst.close();
		connection.commit();
		connection.close();
		
		
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {

		TrocaPedido trocaped = (TrocaPedido)entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("UPDATE ttrocapedido SET status=?, datafim=? WHERE id=?");
				
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		
		LocalDate hoje = LocalDate.now();
		
		pst.setInt(1, trocaped.getStatus().getId());
		pst.setString(2, hoje.toString());
		pst.setInt(3, trocaped.getId());
		
		
		
		
		pst.executeUpdate();
		
		
		StatusPedido status = new StatusPedido();
		status.setId(7);
		
		Pedido pedido = new Pedido();
		
		pedido.setId(trocaped.getPedido().getId());
		pedido.setStatus(status);
		
		PedidoDAO peddao = new PedidoDAO(null, null);
		
		peddao.alterar(pedido);
		
		
		pst.close();
		connection.commit();
		connection.close();

		
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		
		TrocaPedido trocaped = (TrocaPedido)entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
		if(trocaped.getCliente() != null){
			
			sql.append(" SELECT ttrocapedido.id, ttrocapedido.datainicio, ttrocapedido.datafim"); 
			sql.append(", ttrocapedido.valor, ttrocapedido.motivo, tstatustroca.id, tstatustroca.nome,tcliente.id,tcliente.nome,ttrocapedido.pedido");
			sql.append(" FROM((ttrocapedido INNER JOIN tstatustroca on ttrocapedido.status = tstatustroca.id) INNER JOIN tcliente on ttrocapedido.cliente = tcliente.id) ");
			sql.append("WHERE cliente = ?");
			 
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setInt(1, trocaped.getCliente().getId());
			
		}else if(trocaped.getStatus() != null){
			
			sql.append(" SELECT ttrocapedido.id, ttrocapedido.datainicio, ttrocapedido.datafim"); 
			sql.append(", ttrocapedido.valor, ttrocapedido.motivo, tstatustroca.id, tstatustroca.nome,tcliente.id,tcliente.nome,ttrocapedido.pedido");
			sql.append(" FROM((ttrocapedido INNER JOIN tstatustroca on ttrocapedido.status = tstatustroca.id) INNER JOIN tcliente on ttrocapedido.cliente = tcliente.id) ");
			sql.append(" WHERE status = ?");
			 
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setInt(1, trocaped.getStatus().getId());
			
			
		}else{
			
			sql.append(" SELECT ttrocapedido.id, ttrocapedido.datainicio, ttrocapedido.datafim"); 
			sql.append(", ttrocapedido.valor, ttrocapedido.motivo, tstatustroca.id, tstatustroca.nome,tcliente.id,tcliente.nome,ttrocapedido.pedido");
			sql.append(" FROM((ttrocapedido INNER JOIN tstatustroca on ttrocapedido.status = tstatustroca.id) INNER JOIN tcliente on ttrocapedido.cliente = tcliente.id) ");
			
			 
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
			
		}
		
		List<EntidadeDominio> lista = new ArrayList<>();
		
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()){
			
			TrocaPedido troca = new TrocaPedido();
			
			troca.setId(rs.getInt(1));
			troca.setDataSolicita(rs.getString(2));
			troca.setDataFinaliza(rs.getString(3));
			troca.setValor(rs.getDouble(4));
			troca.setMotivo(rs.getString(5));
			
			StatusTroca status = new StatusTroca();
			status.setId(rs.getInt(6));
			status.setNome(rs.getString(7));
			
			troca.setStatus(status);
			
			Cliente cli = new Cliente();
			
			cli.setId(rs.getInt(8));
			cli.setNome(rs.getString(9));
			
			troca.setCliente(cli);
			
			Pedido pedido = new Pedido();
			
			pedido.setId(rs.getInt(10));
			
			PedidoDAO peddao = new PedidoDAO(null, null);
			
			pedido = (Pedido)peddao.consultar(pedido).get(0);
			
			troca.setPedido(pedido);			
			
			lista.add(troca);
			
		}
		
		
		return lista;
	}

}
