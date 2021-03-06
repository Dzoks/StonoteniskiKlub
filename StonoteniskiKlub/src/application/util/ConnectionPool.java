package application.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ConnectionPool {
	private String jdbcURL;
	private String username;
	private String password;
	private int preconnectCount;
	private int maxIdleConnections;
	private int maxConnections;

	private int connectCount;
	private List<Connection> usedConnections;
	private List<Connection> freeConnections;

	private static ConnectionPool instance;

	public static ConnectionPool getInstance() {
		if (instance == null)
			instance = new ConnectionPool();
		return instance;
	}

	private ConnectionPool() {
		readConfiguration();
		try {
			freeConnections = new ArrayList<Connection>();
			usedConnections = new ArrayList<Connection>();

			for (int i = 0; i < preconnectCount; i++) {
				Connection conn = DriverManager.getConnection(jdbcURL, username, password);
				freeConnections.add(conn);
			}
			connectCount = preconnectCount;
		} catch (Exception e) {
			 ;
			new ErrorLogger().log(e);
		}
	}

	private void readConfiguration() {
		ResourceBundle bundle;
		try {
			bundle = new PropertyResourceBundle(new FileInputStream("Properties.properties"));
			jdbcURL = bundle.getString("jdbcURL");
			username = bundle.getString("username");
			password = bundle.getString("password");
			preconnectCount = 0;
			maxIdleConnections = 10;
			maxConnections = 10;
			preconnectCount = Integer.parseInt(bundle.getString("preconnectCount"));
			maxIdleConnections = Integer.parseInt(bundle.getString("maxIdleConnections"));
			maxConnections = Integer.parseInt(bundle.getString("maxConnections"));
		} catch (Exception e) {
			 ;
			new ErrorLogger().log(e);
		}
	}

	public synchronized Connection checkOut() throws SQLException {
		Connection conn = null;
		if (freeConnections.size() > 0) {
			conn = freeConnections.remove(0);
			usedConnections.add(conn);
		} else {
			if (connectCount < maxConnections) {
				conn = DriverManager.getConnection(jdbcURL, username, password);
				usedConnections.add(conn);
				connectCount++;
			} else {
				try {
					wait();
					conn = freeConnections.remove(0);
					usedConnections.add(conn);
				} catch (InterruptedException e) {
					 ;
					new ErrorLogger().log(e);
				}
			}
		}
		return conn;
	}

	public synchronized void checkIn(Connection conn) {
		if (conn == null)
			return;
		if (usedConnections.remove(conn)) {
			freeConnections.add(conn);
			while (freeConnections.size() > maxIdleConnections) {
				int lastOne = freeConnections.size() - 1;
				Connection c = freeConnections.remove(lastOne);
				try {
					c.close();
				} catch (SQLException e) {
					 ;
					new ErrorLogger().log(e);
				}
			}
			notify();
		}
	}

	public static PreparedStatement prepareStatement(Connection c, String sql, boolean retGenKeys, Object... values)
			throws SQLException {
		PreparedStatement ps = c.prepareStatement(sql,
				retGenKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		for (int i = 0; i < values.length; i++)
			ps.setObject(i + 1, values[i]);
		return ps;
	}

	public static void close(Statement s) {
		if (s != null)
			try {
				s.close();
			} catch (SQLException e) {
				 ;
				new ErrorLogger().log(e);
			}
	}

	public static void close(ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				 ;
				new ErrorLogger().log(e);
			}
	}

	public static void close(ResultSet rs, Statement s) {
		close(rs);
		close(s);
	}
}
