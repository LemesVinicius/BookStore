package core.domain;

import core.app.DomainEntity;

public class Product extends DomainEntity{
	
	private String barcode;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	
	
}
