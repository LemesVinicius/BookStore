
package web.command;

import core.app.DomainEntity;
import core.app.Result;


public class SaveCommand extends AbstractCommand{

	
	public Result execute(DomainEntity entity) {
		
		return facade.save(entity);
	}

}
