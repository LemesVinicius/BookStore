package core.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import les.dominio.Cliente;
import les.dominio.EntidadeDominio;
import les.dominio.ItemPedido;
import les.dominio.PagamentoCard;
import les.dominio.PagamentoCupom;
import les.dominio.Pedido;
import les.dominio.StatusPedido;


public class PedidoDAO extends AbstractJdbcDAO{

	public PedidoDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		
		Pedido p = (Pedido)entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
				
		sql.append(" INSERT INTO tpedido(endereco, cliente, status, valor, data)");
		sql.append("VALUES(?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'))");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setString(1, p.getEnd());
		pst.setInt(2, p.getCliente().getId());
		pst.setInt(3, p.getStatus().getId());
		pst.setDouble(4, p.getValor());		
		Date data = new Date();
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		pst.setString(5, formatador.format(data).toString());
		
		pst.executeUpdate();
				
		final ResultSet rs = pst.getGeneratedKeys();
		
		if (rs.next()) {
						
			final int lastId = rs.getInt("GENERATED_KEY");
			p.setId(lastId);
			
		}
		
		
		for(int i = 0 ; i < p.getItens().size(); i++){
			for(int j = 0 ; j < p.getItens().get(i).getQuantidade();j++) {
			ItemPedidoDAO itemDAO = new ItemPedidoDAO(null, null);
			p.getItens().get(i).setPedido(p.getId());
			itemDAO.salvar(p.getItens().get(i));
			}
		}
		
				
		
		pst.close();
		connection.commit();
		connection.close();
		
		
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		
		Pedido p = (Pedido)entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("UPDATE tpedido SET status=?, endereco=? WHERE id=?"); 
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setInt(1, p.getStatus().getId());
		pst.setString(2, p.getEnd());
		pst.setInt(3, p.getId());
		
		pst.executeUpdate();
		
		if( p.getPagamentocard() != null){
		for(int i = 0; i < p.getPagamentocard().size() ; i++){			
			PagamentoCardDAO pcardDAO = new PagamentoCardDAO(null, null);
			p.getPagamentocard().get(i).setPedido(p.getId());
			pcardDAO.salvar(p.getPagamentocard().get(i));			
		}
		}
		
		if( p.getPagamentocupom() != null){
		for(int i = 0; i < p.getPagamentocupom().size() ; i++ ){
			PagamentoCupomDAO pcupDAO = new PagamentoCupomDAO(null, null);
			p.getPagamentocupom().get(i).setPedido(p.getId());
			pcupDAO.salvar(p.getPagamentocupom().get(i));
		}
		}
		
		pst.close();
		connection.commit();
		connection.close();
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		
		Pedido pedido = (Pedido)entidade;
				
		List<EntidadeDominio> pedidos = new ArrayList<EntidadeDominio>();
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
			if(pedido.getId() != null){
			
			sql.append("SELECT tpedido.id, tpedido.endereco, tstatuspedido.id, tstatuspedido.nome, "
					+ "tpedido.valor, tpedido.data "
					+ "FROM (tpedido INNER JOIN tstatuspedido ON tpedido.status = tstatuspedido.id)");
			sql.append(" WHERE tpedido.id=?");
			
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setInt(1, pedido.getId());
			
			ResultSet rs = pst.executeQuery();
			
			
			while(rs.next()){
			
				Pedido p = new Pedido();
				StatusPedido status = new StatusPedido();
				
				p.setId(rs.getInt(1));
				p.setEnd(rs.getString(2));
				status.setId(rs.getInt(3));
				status.setNome(rs.getString(4));
				p.setStatus(status);
				p.setValor(rs.getDouble(5));
				p.setData(rs.getString(6));
				
				PagamentoCard pgtocard = new PagamentoCard();	
				PagamentoCupom pgtocup = new PagamentoCupom();
				ItemPedido itempedido = new ItemPedido();
				
				PagamentoCardDAO pcardDAO = new PagamentoCardDAO(null, null);
				PagamentoCupomDAO pcupDAO = new PagamentoCupomDAO(null, null);
				ItemPedidoDAO itempedDAO = new ItemPedidoDAO(null, null);
				
				pgtocard.setPedido(p.getId());
				pgtocup.setPedido(p.getId());
				itempedido.setPedido(p.getId());
				
				List<EntidadeDominio> pgtoscard = pcardDAO.consultar(pgtocard);
				List<EntidadeDominio> pgtoscupom = pcupDAO.consultar(pgtocup);
				List<EntidadeDominio> itspedido = itempedDAO.consultar(itempedido);
				
				
				List<PagamentoCard> pagamentoscard = new ArrayList<>(); 
				List<PagamentoCupom> pagamentoscupom = new ArrayList<>();
				List<ItemPedido> itenspedido = new ArrayList<>();
				
						
				
							
				pgtocard.setPedido(p.getId());				
				pgtocup.setPedido(p.getId());
				
				for(int i = 0 ; i < itspedido.size() ; i++){
					itenspedido.add((ItemPedido)itspedido.get(i));
				}
				if(pgtoscard != null){
				for(int i = 0; i < pgtoscard.size() ; i++){					
					pagamentoscard.add((PagamentoCard)pgtoscard.get(i));					
				}
				}
				if(pgtoscupom != null){
				for(int i = 0 ; i < pgtoscupom.size() ; i++){
					pagamentoscupom.add((PagamentoCupom)pgtoscupom.get(i));
				}
				}
				p.setPagamentocard(pagamentoscard);
				p.setPagamentocupom(pagamentoscupom);
				p.setItens(itenspedido);
				
				pedidos.add(p);
			
			}
			return pedidos;
			
			}if(pedido.getId() == null && pedido.getCliente().getId() != null){
			sql.append("SELECT tpedido.id, tpedido.endereco, tstatuspedido.id, tstatuspedido.nome, "
					+ "tpedido.valor, tpedido.data "
					+ "FROM (tpedido INNER JOIN tstatuspedido ON tpedido.status = tstatuspedido.id)");
			sql.append(" WHERE cliente=?");
			
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setInt(1, pedido.getCliente().getId());
			
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
			
				Pedido p = new Pedido();
				StatusPedido status = new StatusPedido();
				
				p.setId(rs.getInt(1));
				p.setEnd(rs.getString(2));
				status.setId(rs.getInt(3));
				status.setNome(rs.getString(4));
				p.setStatus(status);
				p.setValor(rs.getDouble(5));
				p.setData(rs.getString(6));
				
				PagamentoCard pgtocard = new PagamentoCard();	
				PagamentoCupom pgtocup = new PagamentoCupom();
				ItemPedido itempedido = new ItemPedido();
				
				PagamentoCardDAO pcardDAO = new PagamentoCardDAO(null, null);
				PagamentoCupomDAO pcupDAO = new PagamentoCupomDAO(null, null);
				ItemPedidoDAO itempedDAO = new ItemPedidoDAO(null, null);
				
				pgtocard.setPedido(p.getId());				
				pgtocup.setPedido(p.getId());
				itempedido.setPedido(p.getId());
				
				List<EntidadeDominio> pgtoscard = pcardDAO.consultar(pgtocard);
				List<EntidadeDominio> pgtoscupom = pcupDAO.consultar(pgtocup);
				List<EntidadeDominio> itspedido = itempedDAO.consultar(itempedido);
				
				List<PagamentoCard> pagamentoscard = new ArrayList<>(); 
				List<PagamentoCupom> pagamentoscupom = new ArrayList<>();
				List<ItemPedido> itenspedido = new ArrayList<>();				
				
				
							
				
				
				for(int i = 0 ; i < itspedido.size() ; i++){
					itenspedido.add((ItemPedido)itspedido.get(i));
				}
				
				if(pgtoscard != null){
				for(int i = 0; i < pgtoscard.size() ; i++){					
					pagamentoscard.add((PagamentoCard)pgtoscard.get(i));					
				}
				}
				
				if(pgtoscupom != null){
				for(int i = 0 ; i < pgtoscupom.size() ; i++){
					pagamentoscupom.add((PagamentoCupom)pgtoscupom.get(i));
				}
				}
				
				p.setPagamentocard(pagamentoscard);
				p.setPagamentocupom(pagamentoscupom);
				p.setItens(itenspedido);
				
				pedidos.add(p);
			
			}
			
			return pedidos;
			
		}if(pedido.getId() == null && pedido.getCliente().getId() == null && pedido.getStatus() != null){
			sql.append("SELECT tpedido.id, tpedido.endereco, tstatuspedido.id, tstatuspedido.nome, "
					+ "tpedido.valor, tpedido.data "
					+ "FROM (tpedido INNER JOIN tstatuspedido ON tpedido.status = tstatuspedido.id)");
			sql.append(" WHERE status=?");
			
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setInt(1, pedido.getStatus().getId());
			
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
			
				Pedido p = new Pedido();
				StatusPedido status = new StatusPedido();
				
				p.setId(rs.getInt(1));
				p.setEnd(rs.getString(2));
				status.setId(rs.getInt(3));
				status.setNome(rs.getString(4));
				p.setStatus(status);
				p.setValor(rs.getDouble(5));
				p.setData(rs.getString(6));
				
				PagamentoCard pgtocard = new PagamentoCard();	
				PagamentoCupom pgtocup = new PagamentoCupom();
				ItemPedido itempedido = new ItemPedido();
				
				PagamentoCardDAO pcardDAO = new PagamentoCardDAO(null, null);
				PagamentoCupomDAO pcupDAO = new PagamentoCupomDAO(null, null);
				ItemPedidoDAO itempedDAO = new ItemPedidoDAO(null, null);
				
				pgtocard.setPedido(p.getId());				
				pgtocup.setPedido(p.getId());
				itempedido.setPedido(p.getId());
				
				List<EntidadeDominio> pgtoscard = pcardDAO.consultar(pgtocard);
				List<EntidadeDominio> pgtoscupom = pcupDAO.consultar(pgtocup);
				List<EntidadeDominio> itspedido = itempedDAO.consultar(itempedido);
				
				List<PagamentoCard> pagamentoscard = new ArrayList<>(); 
				List<PagamentoCupom> pagamentoscupom = new ArrayList<>();
				List<ItemPedido> itenspedido = new ArrayList<>();				
				
				
							
				
				
				for(int i = 0 ; i < itspedido.size() ; i++){
					itenspedido.add((ItemPedido)itspedido.get(i));
				}
				
				if(pgtoscard != null){
				for(int i = 0; i < pgtoscard.size() ; i++){					
					pagamentoscard.add((PagamentoCard)pgtoscard.get(i));					
				}
				}
				
				if(pgtoscupom != null){
				for(int i = 0 ; i < pgtoscupom.size() ; i++){
					pagamentoscupom.add((PagamentoCupom)pgtoscupom.get(i));
				}
				}
				
				p.setPagamentocard(pagamentoscard);
				p.setPagamentocupom(pagamentoscupom);
				p.setItens(itenspedido);
				
				pedidos.add(p);
			
			}
			
			return pedidos;
			
		}else{
			
			sql.append("SELECT tpedido.id, tpedido.endereco, tstatuspedido.id, tstatuspedido.nome, "
					+ "tpedido.valor, tpedido.data, tpedido.cliente, tcliente.nome, tcliente.cpf, tcliente.datanascimento, tcliente.email "
					+ "FROM ((tpedido INNER JOIN tstatuspedido ON tpedido.status = tstatuspedido.id)"
					+ " INNER JOIN tcliente ON tpedido.cliente = tcliente.id)");
			
			
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
			
				Pedido p = new Pedido();
				StatusPedido status = new StatusPedido();
				
				PagamentoCard pgtocard = new PagamentoCard();	
				PagamentoCupom pgtocup = new PagamentoCupom();
				
				PagamentoCardDAO pcardDAO = new PagamentoCardDAO(null, null);
				PagamentoCupomDAO pcupDAO = new PagamentoCupomDAO(null, null);				
				
				List<EntidadeDominio> pgtoscard = pcardDAO.consultar(pgtocard);
				List<EntidadeDominio> pgtoscupom = pcupDAO.consultar(pgtocup);
				
				List<PagamentoCard> pagamentoscard = new ArrayList<>(); 
				List<PagamentoCupom> pagamentoscupom = new ArrayList<>();
				
				
				p.setId(rs.getInt(1));
				p.setEnd(rs.getString(2));
				status.setId(rs.getInt(3));
				status.setNome(rs.getString(4));
				p.setStatus(status);
				p.setValor(rs.getDouble(5));
				p.setData(rs.getString(6));
							
				pgtocard.setPedido(p.getId());				
				pgtocup.setPedido(p.getId());
				
				if(pgtoscard != null) {
				for(int i = 0; i < pgtoscard.size() ; i++){					
					pagamentoscard.add((PagamentoCard)pgtoscard.get(i));					
				}
				}
				if(pgtoscupom != null) {
				for(int i = 0 ; i < pgtoscupom.size() ; i++){
					pagamentoscupom.add((PagamentoCupom)pgtoscupom.get(i));
				}
				}
				p.setPagamentocard(pagamentoscard);
				p.setPagamentocupom(pagamentoscupom);
				
				
				Cliente cli = new Cliente();
				cli.setId(rs.getInt(7));
				cli.setNome(rs.getString(8));
				cli.setCpf(rs.getString(9));
				cli.setDatanascimento(rs.getString(10));
				cli.setEmail(rs.getString(11));
				
				p.setCliente(cli);
				
				
				pedidos.add(p);
			
			}
			
			pst.close();
			connection.commit();
			connection.close();
			
			return pedidos;
			
			
			
		}
		
		
		
	}

}
