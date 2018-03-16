package core.control;

import core.app.DomainEntity;
import core.app.Result;


public interface IFacade {

	public Result save(DomainEntity entity);
	public Result update(DomainEntity entity);
	public Result delete(DomainEntity entity);
	public Result read(DomainEntity entity);
	public Result visualizar(DomainEntity entity);
	
	
}
