
package web.command;

import web.command.ICommand;
import core.control.IFacade;
import core.control.Facade;



public abstract class AbstractCommand implements ICommand {

	protected IFacade facade = new Facade();

}
