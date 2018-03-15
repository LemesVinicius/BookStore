
package core.app;

import java.util.List;


public class Result extends AppEntity {

	private String msg = null;
	private List<DomainEntity> entidades;
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public List<DomainEntity> getEntidades() {
		return entidades;
	}
	
	public void setEntidades(List<DomainEntity> entidades) {
		this.entidades = entidades;
	}
	
	
	
	
}
