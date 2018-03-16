package core.dao;

import java.sql.SQLException;
import java.util.List;

import core.app.DomainEntity;

public interface IDAO {

	public void save(DomainEntity entity) throws SQLException;
	public void update(DomainEntity entity)throws SQLException;
	public void delete(DomainEntity entity)throws SQLException;
	public List<DomainEntity> read(DomainEntity entity)throws SQLException;
	
	
}
