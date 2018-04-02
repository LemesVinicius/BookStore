package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.app.DomainEntity;
import core.domain.CardType;


public class CardTypeDAO extends AbstractJdbcDAO{

	public CardTypeDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save(DomainEntity entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(DomainEntity entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DomainEntity> read(DomainEntity entity) throws SQLException {
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("SELECT * FROM tbandcard");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		ResultSet rs = pst.executeQuery();
		
		List<DomainEntity> bcards = new ArrayList<DomainEntity>();
		
		while(rs.next()){
			
			CardType b = new CardType();
			
			b.setId(rs.getInt("id"));
			b.setName(rs.getString("nome"));
			bcards.add(b);
		
		}
		
		connection.close();
		
		return bcards;
	}

}
