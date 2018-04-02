package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.app.DomainEntity;
import core.domain.CardType;
import core.domain.Card;


public class CardDAO extends AbstractJdbcDAO{

	public CardDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save(DomainEntity entity) throws SQLException {
		openConnection();
		PreparedStatement pst=null;
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		Card card =(Card)entity;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO tcard( alias, codigo, vencmes, vencano, codseg, bandeira, cliente) ");
		sql.append("Values(?,?,?,?,?,?,?)");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setString(1, card.getAlias());
		pst.setString(2, card.getCode());
		pst.setString(3, card.getExpirationmonth());
		pst.setString(4, card.getExpirationmonth());
		pst.setString(5, card.getSecurecode());
		pst.setInt(6, card.getCardtype().getId());
		pst.setInt(7, card.getClient());
		
			
		pst.executeUpdate();
		
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
		
		Card card = (Card)entity;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("SELECT tcard.id, tcard.alias, tcard.codigo, tcard.vencmes, tcard.vencano, tcard.codseg, tbandcard.id, tbandcard.nome "
				+ "FROM (tcard INNER JOIN tbandcard ON tcard.bandeira = tbandcard.id)");
		sql.append(" WHERE cliente=?");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setInt(1, card.getClient());
		
		ResultSet rs = pst.executeQuery();
		
		List<DomainEntity> cards = new ArrayList<DomainEntity>();
		
		while(rs.next()){

			Card c = new Card();
			
			c.setId(rs.getInt(1));
			c.setAlias(rs.getString(2));
			c.setCode(rs.getString(3));
			c.setExpirationmonth(rs.getString(4));
			c.setExpirationyear(rs.getString(5));
			c.setSecurecode(rs.getString(6));
			CardType band = new CardType();
			band.setId(rs.getInt(7));
			band.setName(rs.getString(8));
			c.setCardtype(band);
			
			cards.add(c);
			
			
		}
		
		pst.close();
		connection.commit();
		connection.close();
		
		return cards;
	}

}
