
package web.viewhelper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.app.DomainEntity;
import core.app.Result;


public interface IViewHelper {

	public DomainEntity getEntidade(HttpServletRequest request);
	
	public void setView(Result resultado, 
			HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException;
	
}
