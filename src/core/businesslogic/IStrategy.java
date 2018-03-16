package core.businesslogic;

import core.app.DomainEntity;



public interface IStrategy 
{

	public String process(DomainEntity entity);
	
}
