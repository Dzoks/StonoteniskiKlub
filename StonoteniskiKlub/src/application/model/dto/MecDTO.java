package application.model.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MecDTO {
	private IntegerProperty idZrijeba;
	private IntegerProperty brojRunde;
	private IntegerProperty redniBroj;
	private StringProperty rezultat;
	private IntegerProperty idPrvogTima;
	private IntegerProperty idDrugogTima;
	private StringProperty prikazMeca;
	
	public MecDTO() {
		super();
	}
	
	public MecDTO(Integer idZrijeba, Integer brojRunde, Integer redniBroj,Integer tim1, Integer tim2) {
		super();
		this.idZrijeba = new SimpleIntegerProperty(idZrijeba);
		this.brojRunde = new SimpleIntegerProperty(brojRunde);
		this.redniBroj = new SimpleIntegerProperty(redniBroj);
		this.rezultat = new SimpleStringProperty();
		this.idPrvogTima = new SimpleIntegerProperty(tim1);
		this.idDrugogTima = new SimpleIntegerProperty(tim2);
		this.prikazMeca= new SimpleStringProperty();
	}

	public MecDTO(Integer idZrijeba, Integer brojRunde, Integer redniBroj,
			String rezultat, Integer tim1, Integer tim2) {
		super();
		this.idZrijeba = new SimpleIntegerProperty(idZrijeba);
		this.brojRunde = new SimpleIntegerProperty(brojRunde);
		this.redniBroj = new SimpleIntegerProperty(redniBroj);
		this.rezultat = new SimpleStringProperty(rezultat);
		this.idPrvogTima = new SimpleIntegerProperty(tim1);
		this.idDrugogTima = new SimpleIntegerProperty(tim2);
		this.prikazMeca= new SimpleStringProperty();
	}

	public final IntegerProperty idZrijebaProperty() {
		return this.idZrijeba;
	}
	
	public final int getIdZrijeba() {
		return this.idZrijebaProperty().get();
	}
	
	public final void setIdZrijeba(final int idZrijeba) {
		this.idZrijebaProperty().set(idZrijeba);
	}
	
	public final IntegerProperty brojRundeProperty() {
		return this.brojRunde;
	}
	
	public final int getBrojRunde() {
		return this.brojRundeProperty().get();
	}
	
	public final void setBrojRunde(final int brojRunde) {
		this.brojRundeProperty().set(brojRunde);
	}
	
	public final IntegerProperty redniBrojProperty() {
		return this.redniBroj;
	}
	
	public final int getRedniBroj() {
		return this.redniBrojProperty().get();
	}
	
	public final void setRedniBroj(final int redniBroj) {
		this.redniBrojProperty().set(redniBroj);
	}
	
	public final StringProperty rezultatProperty() {
		return this.rezultat;
	}
	
	public final String getRezultat() {
		return this.rezultatProperty().get();
	}
	
	public final void setRezultat(final String rezultat) {
		this.rezultatProperty().set(rezultat);
	}
	
	public final IntegerProperty idPrvogTimaProperty() {
		return this.idPrvogTima;
	}
	
	public final int getIdPrvogTima() {
		return this.idPrvogTimaProperty().get();
	}
	
	public final void setIdPrvogTima(final int idPrvogTima) {
		this.idPrvogTimaProperty().set(idPrvogTima);
	}
	
	public final IntegerProperty idDrugogTimaProperty() {
		return this.idDrugogTima;
	}
	
	public Integer getIdDrugogTima() {
		return this.idDrugogTimaProperty().get();
	}
	
	public final void setIdDrugogTima(final int idDrugogTima) {
		this.idDrugogTimaProperty().set(idDrugogTima);
	}

	public final StringProperty prikazMecaProperty() {
		return this.prikazMeca;
	}
	
	public final String getPrikazMeca() {
		return this.prikazMecaProperty().get();
	}
	
	public final void setPrikazMeca(final String prikazMeca) {
		this.prikazMecaProperty().set(prikazMeca);
	}
	
}
