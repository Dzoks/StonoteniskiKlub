package application.model.dto;

import java.util.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ClanstvoDTO {
	private Date datumOd;
	private Date datumDo;
	private IntegerProperty clanId;
	
	public ClanstvoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClanstvoDTO(Date datumOd, Date datumDo, int clanId) {
		super();
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.clanId = new SimpleIntegerProperty(clanId);
	}

	public final IntegerProperty clanIdProperty() {
		return this.clanId;
	}
	

	public final int getClanId() {
		return this.clanIdProperty().get();
	}
	

	public final void setClanId(final int clanId) {
		this.clanIdProperty().set(clanId);
	}

	public Date getDatumOd() {
		return datumOd;
	}

	public void setDatumOd(Date datumOd) {
		this.datumOd = datumOd;
	}

	public Date getDatumDo() {
		return datumDo;
	}

	public void setDatumDo(Date datumDo) {
		this.datumDo = datumDo;
	}
	
	
	
}
