package application.model.dao.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import application.model.dao.DonacijaDAO;
import application.model.dto.DonacijaDTO;
import application.model.dto.OpremaTip;
import application.model.dto.SponzorDTO;
import application.model.dto.UgovorDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLDonacijaDAO implements DonacijaDAO {

	public static final String SQL_SELECT_ALL_BY_ID = "select * from donacija_detaljno where SponzorId=? and UgovorRb=?";
	public static final String SQL_NEOBRADJENE = "select * from donacija_detaljno where Obradjeno=false and NovcanaDonacija=?";
	public static final String SQL_INSERT = "{call dodaj_donaciju(?,?,?,?,?,?,?,?)}";
	public static final String SQL_UPDATE_OBRADJENA = "update DONACIJA set Obradjeno=true where SPONZOR_Id=? and UGOVOR_RedniBroj=? and RedniBroj=?";

	@Override
	public ObservableList<DonacijaDTO> selectAllById(Integer idSponzora, Integer rbUgovora) {
		ObservableList<DonacijaDTO> result = FXCollections.observableArrayList();
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
	public ObservableList<DonacijaDTO> neobradjene(boolean novcane) {
		ObservableList<DonacijaDTO> result = FXCollections.observableArrayList();
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

	@Override
	public boolean insert(SponzorDTO sponzor, UgovorDTO ugovor, DonacijaDTO donacija) {
		boolean result = false;
		Connection connection = null;
		CallableStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = connection.prepareCall(SQL_INSERT);
			statement.setInt("pSponzorId", sponzor.getId());
			statement.setInt("pRedniBrojUgovor", ugovor.getRedniBroj());
			statement.setString("pOpis", donacija.getOpis());
			if (donacija.getKolicina() == null) {
				statement.setNull("pKolicina", Types.DECIMAL);
			} else {
				statement.setBigDecimal("pKolicina", donacija.getKolicina());
			}
			if (donacija.getNovcaniIznos() == null) {
				statement.setNull("pNovcaniIznos", Types.DECIMAL);
			} else {
				statement.setBigDecimal("pNovcaniIznos", donacija.getNovcaniIznos());
			}
			statement.setBoolean("pNovcanaDonacija", donacija.getNovcanaDonacija());
			if (donacija.getTipOpreme() == null) {
				statement.setNull("pOpremaTipId", Types.INTEGER);
			} else {
				statement.setInt("pOpremaTipId", donacija.getTipOpreme().getId());
			}
			statement.registerOutParameter("pUspjesno", Types.BOOLEAN);
			statement.execute();
			result = statement.getBoolean("pUspjesno");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(statement);
		}
		return result;
	}

	@Override
	public void setObradjeno(DonacijaDTO donacija) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_UPDATE_OBRADJENA, false,
					donacija.getSponzor().getId(), donacija.getUgovor().getRedniBroj(), donacija.getRedniBroj());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(statement);
		}
	}

}
