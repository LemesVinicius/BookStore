package core.domain;

import core.app.DomainEntity;

public class Card extends DomainEntity{

	private boolean main;
	private String code;
	private String expirationmonth;
	private String expirationyear;
	private String securecode;	
	private String owner;
	private String alias;
	private CardType cardtype;
	private int client;
	public boolean isMain() {
		return main;
	}
	public void setMain(boolean main) {
		this.main = main;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getExpirationmonth() {
		return expirationmonth;
	}
	public void setExpirationmonth(String expirationmonth) {
		this.expirationmonth = expirationmonth;
	}
	public String getExpirationyear() {
		return expirationyear;
	}
	public void setExpirationyear(String expirationyear) {
		this.expirationyear = expirationyear;
	}
	public String getSecurecode() {
		return securecode;
	}
	public void setSecurecode(String securecode) {
		this.securecode = securecode;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public CardType getCardtype() {
		return cardtype;
	}
	public void setCardtype(CardType cardtype) {
		this.cardtype = cardtype;
	}
	public int getClient() {
		return client;
	}
	public void setClient(int client) {
		this.client = client;
	}
	
}
