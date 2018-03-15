
package web.command;

import web.command.ICommand;
import core.control.IFachada;
import core.control.Fachada;



public abstract class AbstractCommand implements ICommand {

	protected IFachada fachada = new Fachada();

}
