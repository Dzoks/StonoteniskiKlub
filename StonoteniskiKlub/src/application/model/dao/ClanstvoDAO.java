package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.model.dto.ClanstvoDTO;
import application.util.ConnectionPool;

public interface ClanstvoDAO {

	public List<ClanstvoDTO> getByClanId(int id);
	public boolean insert(int clanId);
}
