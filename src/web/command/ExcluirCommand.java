
package web.command;

import core.app.DomainEntity;
import core.app.Result;


public class ExcluirCommand extends AbstractCommand{

	
	public Result execute(DomainEntity entity) {
		
		return facade.delete(entity);
	}

}
