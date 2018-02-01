package core.domain;

import core.app.DomainEntity;

public class Coupon extends DomainEntity{

	private String code;
	private Double value;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	
}
