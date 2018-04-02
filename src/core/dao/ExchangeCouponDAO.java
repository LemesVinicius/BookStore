package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import core.app.DomainEntity;
import core.domain.ExchangeCoupon;

public class ExchangeCouponDAO extends AbstractJdbcDAO{

	public ExchangeCouponDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save(DomainEntity entity) throws SQLException {
		
		ExchangeCoupon cupom = (ExchangeCoupon)entity;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
				
		sql.append("INSERT INTO tcupomtroca(valor,cliente,ativo) values(?,?,?)");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setDouble(1, cupom.getValue());
		pst.setInt(2, cupom.getClient());
		pst.setBoolean(3, true);
		
		pst.executeUpdate();
		
		pst.close();
		connection.commit();
		connection.close();
		
	}

	@Override
	public void update(DomainEntity entity) throws SQLException {
		
		ExchangeCoupon cupom = (ExchangeCoupon)entity;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
				
		sql.append("UPDATE tcupomtroca SET ativo=? , utilizado=? WHERE id=?");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		java.util.Date d = new Date();
		pst.setBoolean(1, cupom.isActive());
		pst.setString(2, d.toString());
		pst.setInt(3, cupom.getId());
		
		pst.executeUpdate();
		
		pst.close();
		connection.commit();
		connection.close();
		
	}

	@Override
	public List<DomainEntity> read(DomainEntity entity) throws SQLException {
		
		ExchangeCoupon cupt = (ExchangeCoupon)entity;
		
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		if(cupt.getClient() != null ){
			
			sql.append("SELECT * FROM tcupomtroca WHERE cliente=?");		
			
			if(cupt.isActive()){
				sql.append(" AND ativo="+cupt.isActive());
			}
			
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setInt(1, cupt.getClient());
			
		}else if(cupt.getId() != null){
			
			sql.append("SELECT * FROM tcupomtroca WHERE id=?");
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, cupt.getId());
			
		}
				
		
		ResultSet rs = pst.executeQuery();
		
		List<DomainEntity> cupons = new ArrayList<>();
		
		while(rs.next()){
			
			ExchangeCoupon ct = new ExchangeCoupon();
			
			ct.setId(rs.getInt("id"));
			ct.setValue(rs.getDouble("valor"));
			if(rs.getInt("ativo") == 1){
				ct.setActive(true);
			}else{
				ct.setActive(false);
			}
			ct.setUtilizado(rs.getString("utilizado"));	
			
			cupons.add(ct);
			
			
		}
		
		return cupons;
	}

}
