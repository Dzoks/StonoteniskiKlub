package application.model.dto;

import javafx.beans.property.IntegerProperty;

public class NovcanaSredstvaDTO {
	private Integer id;
	private String sezona;
	private Double budzet;
	private Double prihodi;
	private Double rashodi;
	public NovcanaSredstvaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NovcanaSredstvaDTO(Integer id, String sezona, Double budzet, Double prihodi, Double rashodi) {
		super();
		this.id = id;
		this.sezona = sezona;
		this.budzet = budzet;
		this.prihodi = prihodi;
		this.rashodi = rashodi;
	}
	public NovcanaSredstvaDTO(String sezona, Double budzet, Double prihodi, Double rashodi) {
		super();
		this.sezona = sezona;
		this.budzet = budzet;
		this.prihodi = prihodi;
		this.rashodi = rashodi;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSezona() {
		return sezona;
	}
	public void setSezona(String sezona) {
		this.sezona = sezona;
	}
	public Double getBudzet() {
		return budzet;
	}
	public void setBudzet(Double budzet) {
		this.budzet = budzet;
	}
	public Double getPrihodi() {
		return prihodi;
	}
	public void setPrihodi(Double prihodi) {
		this.prihodi = prihodi;
	}
	public Double getRashodi() {
		return rashodi;
	}
	public void setRashodi(Double rashodi) {
		this.rashodi = rashodi;
	}
	
	
}
