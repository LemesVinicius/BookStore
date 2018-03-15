
package web.command;

import core.app.DomainEntity;
import core.app.Result;


public class VisualizarCommand extends AbstractCommand{

	
	public Result execute(DomainEntity entity) {
		
		return facade.visualizar(entity);
	}

}
