package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.app.DomainEntity;
import core.domain.Category;


public class CategoryDAO extends AbstractJdbcDAO{

	public CategoryDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save(DomainEntity entity) throws SQLException {
		
		Category cat = (Category) entity;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("INSERT INTO tcategoria(nome)");
		sql.append("values(?)");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setString(1, cat.getName());
		
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

		
		Category cat = (Category) entity;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		if(cat.getId()==null){
		
			sql.append("SELECT * FROM tcategoria");		
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);			
		}
		
		else{
		sql.append("SELECT * FROM tcategoria WHERE id=?");		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);		
		pst.setInt(1, cat.getId());
		}
		
		ResultSet rs = pst.executeQuery();
		
		List<DomainEntity> categorias = new ArrayList<DomainEntity>();
		
		while(rs.next()){
			
			Category c = new Category();
			c.setId(rs.getInt("id"));
			c.setName(rs.getString("nome"));
			categorias.add(c);
			
		}
		
		pst.close();
		connection.commit();
		connection.close();
		
		return categorias;
	}

	
}
