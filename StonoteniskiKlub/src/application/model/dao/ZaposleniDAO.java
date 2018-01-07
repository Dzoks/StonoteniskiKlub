package application.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.dto.ZaposleniDTO;
import application.util.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ZaposleniDAO {
	private static final String SQL_SELECT_ALL = "select * from zaposleni z inner join osoba o on z.OSOBA_Id=o.Id";
	
	public static ObservableList<ZaposleniDTO> selectAll(){
		ObservableList<ZaposleniDTO> result = FXCollections.observableArrayList();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().checkOut();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_SELECT_ALL);
			while (resultSet.next()) {
				result.add(new ZaposleniDTO(resultSet.getInt("Id"),
											resultSet.getString("Ime"),
											resultSet.getString("Prezime"), 
											resultSet.getString("ImeRoditelja"),
											resultSet.getString("JMB"),
											resultSet.getString("Pol").charAt(0),
											resultSet.getDate("DatumRodjenja"),
											resultSet.getBlob("Fotografija"),
											DAOFactory.getDAOFactory().getOsobaDAO().getTelefoni(resultSet.getInt("Id")),
											resultSet.getBoolean("Aktivan"), 
											ZaposlenjeDAO.selectAllByIdZaposlenog(resultSet.getInt("Id"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			ConnectionPool.getInstance().checkIn(connection);
			ConnectionPool.close(resultSet, statement);
		}
		return result;
	}
}
