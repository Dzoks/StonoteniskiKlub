package application.model.dto;

import java.util.ArrayList;
import java.util.Date;

import application.model.dao.DistributerOpremeDAO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NarudzbaDTO {

	private IntegerProperty id;
	private Date datum;
	private StringProperty vrsta;
	private BooleanProperty obradjeno;
	private IntegerProperty idDistributeraOpreme;
	private StringProperty nazivDistributeraOpreme;
	private ArrayList<NarudzbaStavkaDTO> listaStavki;
	
	public NarudzbaDTO() {
		super();
	}

	public NarudzbaDTO(Integer id, Date datum, Boolean opremaKluba, Boolean obradjeno, Integer idDistributeraOpreme) {
		super();
		this.id = id==null ? null : new SimpleIntegerProperty(id);
		this.datum = datum;
		if(opremaKluba) {
			this.vrsta = new SimpleStringProperty("Oprema kluba");
		}
		else {
			this.vrsta = new SimpleStringProperty("Oprema clana");
		}
		this.idDistributeraOpreme = idDistributeraOpreme==null ? null : new SimpleIntegerProperty(idDistributeraOpreme);
		this.nazivDistributeraOpreme = idDistributeraOpreme==null ? null : new SimpleStringProperty(DistributerOpremeDAO.SELECT_BY_ID(idDistributeraOpreme));
		this.obradjeno = obradjeno==null ? null : new SimpleBooleanProperty(obradjeno);
		listaStavki = new ArrayList<>();
	}

	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id.set(id);
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getVrsta() {
		return vrsta.get();
	}

	public void setVrsta(String vrsta) {
		this.vrsta.set(vrsta);
	}

	public Boolean getObradjeno() {
		return obradjeno.get();
	}

	public Integer getIdDistributeraOpreme() {
		return idDistributeraOpreme.get();
	}

	public void setIdDistributeraOpreme(Integer idDistributeraOpreme) {
		this.idDistributeraOpreme.set(idDistributeraOpreme);
	}

	public void setObradjeno(Boolean obradjeno) {
		this.obradjeno.set(obradjeno);
	}

	public String getNazivDistributeraOpreme() {
		return nazivDistributeraOpreme.get();
	}

	public void setNazivDistributeraOpreme(String nazivDistributeraOpreme) {
		this.nazivDistributeraOpreme.set(nazivDistributeraOpreme);
	}

	public ArrayList<NarudzbaStavkaDTO> getListaStavki() {
		return listaStavki;
	}

	public void setListaStavki(ArrayList<NarudzbaStavkaDTO> listaStavki) {
		this.listaStavki = listaStavki;
	}
}
