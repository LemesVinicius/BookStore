
package web.command;

import core.app.Result;
import core.app.DomainEntity;


public interface ICommand {

	public Result execute(DomainEntity entidade);
	
}
