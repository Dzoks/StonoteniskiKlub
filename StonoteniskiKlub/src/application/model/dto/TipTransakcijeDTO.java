package application.model.dto;

public class TipTransakcijeDTO {
	private Integer id;
	private String tip;
	public TipTransakcijeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TipTransakcijeDTO(Integer id, String tip) {
		super();
		this.id = id;
		this.tip = tip;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	
}
