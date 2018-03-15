
package web.command;

import core.app.DomainEntity;
import core.app.Result;


public class UpdateCommand extends AbstractCommand{

	
	public Result execute(DomainEntity entity) {
		
		return facade.alterar(entity);
	}

}
