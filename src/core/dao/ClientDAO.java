package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.app.DomainEntity;
import core.domain.Address;
import core.domain.Client;
import core.domain.ExchangeCoupon;
import core.domain.Telephone;
import core.domain.Card;



public class ClientDAO extends AbstractJdbcDAO{

	public ClientDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save(DomainEntity entity) throws SQLException {

		openConnection();
		PreparedStatement pst=null;
		connection.setAutoCommit(false);
		
		Client cli = (Client)entity;
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO tcliente (nome, email, senha, cpf, datanascimento)");
		sql.append(" VALUES (?,?,?,?,?)");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setString(1, cli.getName());
		pst.setString(2, cli.getEmail());
		pst.setString(3, cli.getPassword());
		pst.setString(4, cli.getCpf());
		pst.setString(5, cli.getBorndate());
		
		pst.executeUpdate();
		
		final ResultSet rs = pst.getGeneratedKeys();
		
		if (rs.next()) {
			
			
			final int lastId = rs.getInt("GENERATED_KEY");
			cli.setId(lastId);;
		}
		
		
		
		for(int i = 0 ; i < cli.getAddress().size();i++){
		
			Address end = cli.getAddress().get(i);
			
			end.setClient(cli.getId());
			
			EnderecoDAO endDAO = new EnderecoDAO(null, null);
			
			endDAO.save(end);
			
		}
		
		for(int i = 0 ; i < cli.getTelephone().size(); i++){
			
			Telephone tel = cli.getTelephone().get(i);
			
			tel.setClient(cli.getId());
			
			TelefoneDAO telDAO = new TelefoneDAO(null, null);
			
			telDAO.save(tel);
			
		}
		
		for(int i = 0 ; i < cli.getCards().size();i++){
			
			Card c = cli.getCards().get(i);
			
			c.setClient(cli.getId());
			
			CardDAO carDAO = new CardDAO(null,null);
			
			carDAO.save(c);
			
		}
		
		
		pst.close();
		connection.commit();
		connection.close();
		
		
	}

	@Override
	public void update(DomainEntity entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DomainEntity> read(DomainEntity entity) throws SQLException {
		
		Client cli =(Client)entity;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
	
		
		
		if(cli.getEmail() != null && cli.getPassword() != null){
			
			sql.append("SELECT * FROM tcliente WHERE email=? AND senha=? ");
			
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, cli.getEmail());
			pst.setString(2, cli.getPassword());
			
			
			
		}if(cli.getId() != null){
			
			
			sql.append("SELECT * FROM tcliente WHERE id=? ");
			
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setInt(1, cli.getId());
			
			
			
			
		}if(cli.getId() == null && cli.getEmail() == null && cli.getPassword() == null){
			
			sql.append("SELECT * FROM tcliente");
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
		}
				
		ResultSet rs = pst.executeQuery();
		
		
		List<DomainEntity> clis = new ArrayList<DomainEntity>();
		
		while(rs.next()){
		
			Client c = new Client();
			
			c.setId(rs.getInt("id"));
			c.setName(rs.getString("nome"));
			c.setEmail(rs.getString("email"));
			c.setCpf(rs.getString("cpf"));
			c.setBorndate(rs.getString("datanascimento"));
			
			EnderecoDAO enddao = new EnderecoDAO(null, null);
			Address end = new Address();	
			end.setId(c.getId());
			List<DomainEntity> listaend = enddao.read(end);
			List<Address> es = new ArrayList<Address>();
			
			
			for(int i = 0; i < listaend.size(); i++){
				
				es.add((Address)listaend.get(i));				
			}
			
			
			CardDAO carddao = new CardDAO(null,null);
			Card card = new Card();
			card.setId(c.getId());
			List<DomainEntity> listacard = carddao.read(card);
			List<Card> cd = new ArrayList<Card>();
			
			for(int i = 0; i < listacard.size() ; i++){
				
				cd.add((Card)listacard.get(i));
				
			}
			
			
			ExchangeCouponDAO cupDAO = new ExchangeCouponDAO(null, null);
			ExchangeCoupon cupt = new ExchangeCoupon();
			cupt.setId(c.getId());
			List<DomainEntity> listacupons = cupDAO.read(cupt);
			List<ExchangeCoupon> ct = new ArrayList<ExchangeCoupon>();
			
			for(int i  = 0 ; i < listacupons.size() ; i++){
				
				ct.add((ExchangeCoupon)listacupons.get(i));
				
			}
			
			
			
			c.setCards(cd);
			c.setAddress(es);
			c.setExchangecoupon(ct);
			
			clis.add(c);
		}

		
		return clis;
	}

}
