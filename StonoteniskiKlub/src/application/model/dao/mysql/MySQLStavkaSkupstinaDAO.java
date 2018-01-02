package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.dao.StavkaSkupstinaDAO;
import application.model.dto.SkupstinaDTO;
import application.model.dto.StavkaSkupstinaDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLStavkaSkupstinaDAO implements StavkaSkupstinaDAO {

	private static final String SQL_SELECT_ALL_DNEVNI_RED = "select * from DNEVNI_RED_STAVKA where SKUPSTINA_Id=?";
	private static final String SQL_SELECT_ALL_IZVJESTAJ = "select * from IZVJESTAJ_STAVKA where SKUPSTINA_Id=?";
	private static final String SQL_INSERT_DNEVNI_RED = "insert into DNEVNI_RED_STAVKA values(?,?,?,?)";
	private static final String SQL_INSERT_IZVJESTAJ = "insert into IZVJESTAJ_STAVKA values(?,?,?,?)";

	@Override
	public ObservableList<StavkaSkupstinaDTO> selectAllById(Integer id, int type) {
		ObservableList<StavkaSkupstinaDTO> result = FXCollections.observableArrayList();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = type == StavkaSkupstinaDAO.DNEVNI_RED ? SQL_SELECT_ALL_DNEVNI_RED : SQL_SELECT_ALL_IZVJESTAJ;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, query, false, id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result.add(new StavkaSkupstinaDTO(resultSet.getInt("RedniBroj"), resultSet.getString("Naslov"),
						resultSet.getString("Tekst")));
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
	public boolean insert(SkupstinaDTO skupstina, StavkaSkupstinaDTO stavka, int type) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			String querey = type == StavkaSkupstinaDAO.DNEVNI_RED ? SQL_INSERT_DNEVNI_RED : SQL_INSERT_IZVJESTAJ;
			statement = ConnectionPool.prepareStatement(connection, querey, false, stavka.getRedniBroj(),
					stavka.getNaslov(), stavka.getTekst(), skupstina.getId());
			statement.executeUpdate();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(statement);
		}
		return result;
	}

	public static MySQLStavkaSkupstinaDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MySQLStavkaSkupstinaDAO();
		}
		return INSTANCE;
	}

	protected MySQLStavkaSkupstinaDAO() {
	}

	private static MySQLStavkaSkupstinaDAO INSTANCE = null;
}
