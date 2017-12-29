package application;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import application.model.dao.ClanDAO;
import application.model.dao.DAOFactory;
import application.model.dao.DonacijaDAO;
import application.model.dao.SponzorDAO;
import application.model.dao.UgovorDAO;
import application.model.dao.ZaposleniDAO;
import application.model.dao.mysql.MySQLDonacijaDAO;
import application.model.dto.ClanDTO;
import application.model.dto.DonacijaDTO;
import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;
import application.model.dto.ZaposleniDTO;
import application.model.dto.ZaposlenjeDTO;
import javafx.collections.ObservableList;

public class Test {
	public static void main(String[] args) {
		ClanDTO clan = ClanDAO.getById(30);
		System.out.println(clan.getIme());
	}
}
