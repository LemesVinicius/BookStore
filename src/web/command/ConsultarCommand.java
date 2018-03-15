
package web.command;

import core.app.DomainEntity;
import core.app.Result;


public class ConsultarCommand extends AbstractCommand{

	
	public Result execute(DomainEntity entity) {
		
		return facade.consultar(entity);
	}

}
