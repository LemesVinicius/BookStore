package core.domain;

import java.util.List;

public class Book extends Product{

	
	private String ISBN;
	private String synopsis;
	private String cover;
	private Author author;
	private PubCompany pubcompany;
    private PriceGroup pricegroup;
    private Edition edition;
    private List<Category> categories; 
	private List<SubCategory> subcategory;
	
	
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public PubCompany getPubcompany() {
		return pubcompany;
	}
	public void setPubcompany(PubCompany pubcompany) {
		this.pubcompany = pubcompany;
	}
	public PriceGroup getPricegroup() {
		return pricegroup;
	}
	public void setPricegroup(PriceGroup pricegroup) {
		this.pricegroup = pricegroup;
	}
	public Edition getEdition() {
		return edition;
	}
	public void setEdition(Edition edition) {
		this.edition = edition;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public List<SubCategory> getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(List<SubCategory> subcategory) {
		this.subcategory = subcategory;
	}
	
}
