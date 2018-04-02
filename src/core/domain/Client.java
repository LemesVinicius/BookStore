package core.domain;


import java.util.List;

public class Client extends People{
	
	private int ranking;	
	private List<Card> cards;
	private List<Telephone> telephone;
	private List<Address> address;
	private List<ExchangeCoupon> exchangecoupon;
	
	
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public List<Card> getCards() {
		return cards;
	}
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	public List<Telephone> getTelephone() {
		return telephone;
	}
	public void setTelephone(List<Telephone> telephone) {
		this.telephone = telephone;
	}
	public List<Address> getAddress() {
		return address;
	}
	public void setAddress(List<Address> address) {
		this.address = address;
	}
	public List<ExchangeCoupon> getExchangecoupon() {
		return exchangecoupon;
	}
	public void setExchangecoupon(List<ExchangeCoupon> exchangecoupon) {
		this.exchangecoupon = exchangecoupon;
	}
	
		

}
