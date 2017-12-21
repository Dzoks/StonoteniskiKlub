package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.dto.ZaposlenjeDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ZaposlenjeDAO {
	private static final String SQL_GET_BY_ID = "select * from ZAPOSLENJE where ZAPOSLENI_OSOBA_Id=?";

	public static ObservableList<ZaposlenjeDTO> selectAllByIdZaposlenog(Integer zaposleniId) {
		ObservableList<ZaposlenjeDTO> result = FXCollections.observableArrayList();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = ConnectionPool.prepareStatement(connection, SQL_GET_BY_ID, false, zaposleniId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result.add(new ZaposlenjeDTO(resultSet.getInt("ZAPOSLENI_TIP_Id"),
						ZaposleniTipDAO.select_tip(resultSet.getInt("ZAPOSLENI_TIP_Id")), resultSet.getDate("DatumOd"),
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
