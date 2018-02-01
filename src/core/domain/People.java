package core.domain;

import core.app.DomainEntity;

public class People extends DomainEntity{
	
	private String email;
	private String Password;
	private String PassConfirm;
	private String cpf;
	private String borndate;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getPassConfirm() {
		return PassConfirm;
	}
	public void setPassConfirm(String passConfirm) {
		PassConfirm = passConfirm;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getBorndate() {
		return borndate;
	}
	public void setBorndate(String borndate) {
		this.borndate = borndate;
	}
	
}
