package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.app.DomainEntity;
import core.domain.OfferCoupon;

public class OfferCouponDAO extends AbstractJdbcDAO{

	public OfferCouponDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save(DomainEntity entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(DomainEntity entidade) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DomainEntity> read(DomainEntity entity) throws SQLException {
		
		OfferCoupon cp = (OfferCoupon)entity;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("Select * from tcupompromo Where codigo=?");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setString(1, cp.getCode());
		
		ResultSet rs = pst.executeQuery();
		
		List<DomainEntity> cupons = new ArrayList<DomainEntity>();
		
		while(rs.next()){
			
			OfferCoupon cpromo = new OfferCoupon();
			
			cpromo.setCode(rs.getString("codigo"));
			cpromo.setValue(rs.getDouble("valor"));
			
			cupons.add(cpromo);
		}
		pst.close();
		connection.commit();
		connection.close();
		return cupons;
	}
	

}
