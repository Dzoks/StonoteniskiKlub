package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.model.dao.DonacijaDAO;
import application.model.dao.OpremaTipDAO;
import application.model.dao.SponzorDAO;
import application.model.dto.DonacijaDTO;
import application.model.dto.OpremaTip;
import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;
import application.util.ConnectionPool;

public class MySQLDonacijaDAO implements DonacijaDAO {

	public static final String SQL_SELECT_ALL_BY_ID = "select * from donacija_detaljno where SponzorId=? and UgovorRb=?";
	public static final String SQL_NEOBRADJENE = "select * from donacija_detaljno where Obradjeno=false and NovcanaDonacija=?";

	@Override
	public List<DonacijaDTO> selectAllById(Integer idSponzora, Integer rbUgovora) {
		List<DonacijaDTO> result = new ArrayList<DonacijaDTO>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_SELECT_ALL_BY_ID, false, idSponzora, rbUgovora);
			resultSet = statement.executeQuery();
			SponzorDTO sponzor = null;
			UgovorDTO ugovor = null;
			if (resultSet.next()) {
				sponzor = new SponzorDTO(resultSet.getInt("SponzorId"), resultSet.getString("Naziv"),
						resultSet.getString("Adresa"), resultSet.getString("Mail"), null);
				ugovor = new UgovorDTO(resultSet.getInt("UgovorRb"), resultSet.getDate("DatumOd"),
						resultSet.getDate("DatumDo"), resultSet.getString("UgovorOpis"), null);
				do {
					DonacijaDTO d = new DonacijaDTO(sponzor, ugovor, resultSet.getInt("DonacijaRb"),
							resultSet.getString("DonacijaOpis"), resultSet.getBigDecimal("Kolicina"),
							resultSet.getBigDecimal("NovcaniIznos"), resultSet.getBoolean("NovcanaDonacija"),
							resultSet.getBoolean("Obradjeno"), null);
					if (!d.getNovcanaDonacija()) {
						d.setTipOpreme(new OpremaTip(resultSet.getInt("OPREMA_TIP_Id"), resultSet.getString("Tip"),
								resultSet.getString("Proizvodjac"), resultSet.getString("Model"),
								resultSet.getBoolean("ImaVelicinu")));
					}
					result.add(d);
				} while (resultSet.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(resultSet, statement);
		}
		return result;
	}

	@Override
	public List<DonacijaDTO> neobradjene(boolean novcane) {
		List<DonacijaDTO> result = new ArrayList<DonacijaDTO>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_NEOBRADJENE, false, novcane);
			resultSet = statement.executeQuery();
			SponzorDTO sponzor = null;
			UgovorDTO ugovor = null;
			while (resultSet.next()) {
				sponzor = new SponzorDTO(resultSet.getInt("SponzorId"), resultSet.getString("Naziv"),
						resultSet.getString("Adresa"), resultSet.getString("Mail"), null);
				ugovor = new UgovorDTO(resultSet.getInt("UgovorRb"), resultSet.getDate("DatumOd"),
						resultSet.getDate("DatumDo"), resultSet.getString("UgovorOpis"), null);
				DonacijaDTO d = new DonacijaDTO(sponzor, ugovor, resultSet.getInt("DonacijaRb"),
						resultSet.getString("DonacijaOpis"), resultSet.getBigDecimal("Kolicina"),
						resultSet.getBigDecimal("NovcaniIznos"), resultSet.getBoolean("NovcanaDonacija"),
						resultSet.getBoolean("Obradjeno"), null);
				if (!d.getNovcanaDonacija()) {
					d.setTipOpreme(new OpremaTip(resultSet.getInt("OPREMA_TIP_Id"), resultSet.getString("Tip"),
							resultSet.getString("Proizvodjac"), resultSet.getString("Model"),
							resultSet.getBoolean("ImaVelicinu")));
				}
				result.add(d);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(resultSet, statement);
		}
		return result;
	}

}
