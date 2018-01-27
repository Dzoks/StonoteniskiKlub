package application.model.dto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import application.model.dao.DAOFactory;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Narudzba {

	private IntegerProperty id;
	private Date datum;
	private StringProperty vrsta;
	private BooleanProperty obradjeno;
	private IntegerProperty idDistributeraOpreme;
	private StringProperty nazivDistributeraOpreme;
	private ObservableList<NarudzbaStavka> listaStavki;
	private StringProperty status;
	
	public Narudzba() {
		super();
	}

	public Narudzba(Integer id, Date datum, Boolean opremaKluba, Boolean obradjeno, Integer idDistributeraOpreme) throws ParseException {
		super();
		this.id = id==null ? null : new SimpleIntegerProperty(id);
		this.datum = datum;
		
		if(opremaKluba) {
			this.vrsta = new SimpleStringProperty("Oprema kluba");
		}
		else {
			this.vrsta = new SimpleStringProperty("Oprema člana");
		}
		
		this.idDistributeraOpreme = idDistributeraOpreme==null ? null : new SimpleIntegerProperty(idDistributeraOpreme);
		this.nazivDistributeraOpreme = idDistributeraOpreme==null ? null : new SimpleStringProperty(DAOFactory.getDAOFactory().getDistributerOpremeDAO().SELECT_BY_ID(idDistributeraOpreme));
		this.obradjeno = obradjeno==null ? null : new SimpleBooleanProperty(obradjeno);
		listaStavki = id==null ? FXCollections.observableArrayList() : DAOFactory.getDAOFactory().getNarudzbaStavkaDAO().SELECT_BY_IDNARUDZBE(id);
		
		if(obradjeno) {
			this.status = new SimpleStringProperty("DA");
		}
		else {
			this.status = new SimpleStringProperty("NE");
		}
	}
	
	public Narudzba(Integer id, Date datum, Boolean opremaKluba, Boolean obradjeno, Integer idDistributeraOpreme, ObservableList<NarudzbaStavka> listaStavki) throws ParseException {
		super();
		this.id = id==null ? null : new SimpleIntegerProperty(id);
		this.datum = datum;
		
		if(opremaKluba) {
			this.vrsta = new SimpleStringProperty("Oprema kluba");
		}
		else {
			this.vrsta = new SimpleStringProperty("Oprema člana");
		}
		
		this.idDistributeraOpreme = idDistributeraOpreme==null ? null : new SimpleIntegerProperty(idDistributeraOpreme);
		this.nazivDistributeraOpreme = idDistributeraOpreme==null ? null : new SimpleStringProperty(DAOFactory.getDAOFactory().getDistributerOpremeDAO().SELECT_BY_ID(idDistributeraOpreme));
		this.obradjeno = obradjeno==null ? null : new SimpleBooleanProperty(obradjeno);
		this.listaStavki = id==null ? FXCollections.observableArrayList() : DAOFactory.getDAOFactory().getNarudzbaStavkaDAO().SELECT_BY_IDNARUDZBE(id);
		
		if(obradjeno) {
			this.status = new SimpleStringProperty("DA");
		}
		else {
			this.status = new SimpleStringProperty("NE");
		}
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

	public BooleanProperty obradjenoProperty() {
		return obradjeno;
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

	public ObservableList<NarudzbaStavka> getListaStavki() {
		return listaStavki;
	}

	public void setListaStavki(ObservableList<NarudzbaStavka> listaStavki) {
		this.listaStavki = listaStavki;
	}

	public String getStatus() {
		return status.get();
	}

	public void setStatus(String status) {
		this.status.set(status);
	}

	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
		Date datumNarudzbe = datum; 
		String datumNarudzbeString = df.format(datumNarudzbe); 
		
		return id.get() + " - " + datumNarudzbeString + " - " + nazivDistributeraOpreme.get();
	}
}
