package core.domain;

import core.app.DomainEntity;

public class PriceGroup extends DomainEntity{

	private Integer profitmargin;

	public Integer getProfitmargin() {
		return profitmargin;
	}

	public void setProfitmargin(Integer profitmargin) {
		this.profitmargin = profitmargin;
	}
	
}
