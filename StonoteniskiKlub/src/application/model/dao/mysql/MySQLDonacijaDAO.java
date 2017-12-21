package application.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.model.dao.DonacijaDAO;
import application.model.dto.DonacijaDTO;
import application.util.ConnectionPool;

public class MySQLDonacijaDAO implements DonacijaDAO{

	public static final String SQL_SELECT_ALL_BY_ID = "select * from DONACIJA where SPONZOR_Id=? and UGOVOR_RedniBroj=?";
	
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
			// dodati popunjavanje rezultata
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
