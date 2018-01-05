package application.model.dto;

import java.sql.Date;

public class KorisnickiNalogDTO {

	private String korisnickoIme;
	private byte[] lozinkaHash;
	private Date datumRegistracije;
	private Boolean aktivan;
	private Integer ulogaId;
	private Integer zaposleniId;
	private Integer nalogId;
	private String nazivUloge;
	private String ime;
	public String getNazivUloge() {
		return nazivUloge;
	}
	public void setNazivUloge(String nazivUloge) {
		this.nazivUloge = nazivUloge;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	private String prezime;

	public KorisnickiNalogDTO(String korisnickoIme, byte[] lozinkaHash, Date datumRegistracije, Boolean aktivan,
			Integer ulogaId, Integer zaposleniId) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinkaHash = lozinkaHash;
		this.datumRegistracije = datumRegistracije;
		this.aktivan = aktivan;
		this.ulogaId = ulogaId;
		this.zaposleniId = zaposleniId;
		this.nalogId=nalogId;
		this.nazivUloge=nazivUloge;
	}
	public KorisnickiNalogDTO(String korisnickoIme,String nazivUloge,String ime,String prezime,Integer nalogId) {
		this.korisnickoIme=korisnickoIme;
		this.ime=ime;
		this.prezime=prezime;
		this.nazivUloge=nazivUloge;
		this.nalogId=nalogId;
	}

	public Integer getNalogId() {
		return nalogId;
	}

	public void setNalogId(Integer nalogId) {
		this.nalogId = nalogId;
	}

	public KorisnickiNalogDTO() {
		super();
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public byte[] getLozinkaHash() {
		return lozinkaHash;
	}

	public void setLozinkaHash(byte[] lozinkaHash) {
		this.lozinkaHash = lozinkaHash;
	}

	public Date getDatumRegistracije() {
		return datumRegistracije;
	}

	public void setDatumRegistracije(Date datumRegistracije) {
		this.datumRegistracije = datumRegistracije;
	}

	public Boolean getAktivan() {
		return aktivan;
	}

	public void setAktivan(Boolean aktivan) {
		this.aktivan = aktivan;
	}

	public Integer getUlogaId() {
		return ulogaId;
	}

	public void setUlogaId(Integer ulogaId) {
		this.ulogaId = ulogaId;
	}

	public Integer getZaposleniId() {
		return zaposleniId;
	}

	public void setZaposleniId(Integer zaposleniId) {
		this.zaposleniId = zaposleniId;
	}

}
