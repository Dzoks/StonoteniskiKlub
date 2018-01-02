package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.dao.DAOFactory;
import application.model.dao.ZaposleniTipDAO;
import application.model.dao.ZaposlenjeDAO;
import application.model.dto.ZaposlenjeDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLZaposlenjeDAO implements ZaposlenjeDAO {

	private static final String SQL_GET_BY_ID = "select * from ZAPOSLENJE where ZAPOSLENI_OSOBA_Id=?";

	@Override
	public ObservableList<ZaposlenjeDTO> selectAllById(Integer id) {
		ObservableList<ZaposlenjeDTO> result = FXCollections.observableArrayList();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_GET_BY_ID, false, id);
			resultSet = statement.executeQuery();
			ZaposleniTipDAO ztDAO = DAOFactory.getDAOFactory().getZaposleniTipDAO();
			while (resultSet.next()) {
				result.add(new ZaposlenjeDTO(resultSet.getInt("ZAPOSLENI_TIP_Id"),
						ztDAO.getById(resultSet.getInt("ZAPOSLENI_TIP_Id")).getTip(), resultSet.getDate("DatumOd"),
						resultSet.getDate("DatumDo"), resultSet.getDouble("Plata")));
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
