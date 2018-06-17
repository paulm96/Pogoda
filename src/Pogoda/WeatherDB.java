package Pogoda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class WeatherDB {
	public static final String DRIVER = "org.sqlite.JDBC";
	public static final String DB_URL = "jdbc:sqlite:weather.db";
	private Connection conn;
	private Statement stat;
	
	private int id;
	private String City;
	private String Temperature;
	private String Pressure;
	private String Date;
	
	public void setDate(String Date) {
		this.Date = Date;
	}
	
	public String getDate() {
		return Date;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setCity(String City) {
		this.City = City;
	}
	
	public String getCity() {
		return City;
	}
	
	public void setTemperature(String Temperature) {
		this.Temperature = Temperature;
	}
	
	public String getTemperature() {
		return Temperature;
	}
	
	public void setPressure(String Pressure) {
		this.Pressure = Pressure;
	}
	
	public String getPressure() {
		return Pressure;
	}
	
	public WeatherDB() {
		try {
			Class.forName(WeatherDB.DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("DB DRIVER error");
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection(DB_URL);
			stat = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("DB connection error");
			e.printStackTrace();
		}
		
		createTables();
	}
	
	public boolean createTables() {
		String createWeather="CREATE TABLE IF NOT EXISTS weather(id_weather INTEGER PRIMARY KEY AUTOINCREMENT, city varchar(255), temperature varchar(255), pressure varchar(255), date varchar(255))";
		try {
			stat.execute(createWeather);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Creating tables error");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean deleteAllRows() {
		String deleter = "DELETE FROM weather;";
		
		try {
			stat.execute(deleter);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Deleting all rows error");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean deleteRow(String date) {
		String deleter = "DELETE FROM weather WHERE date='"+date+"';";
		
		try {
			stat.execute(deleter);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Deleting rows by date error");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean insertWeather(String city, String temperature, String pressure, String date) {
		try {
			PreparedStatement prepStmt = conn.prepareStatement("insert into weather values(NULL,?,?,?,?);");
			prepStmt.setString(1, city);
			prepStmt.setString(2, temperature);
			prepStmt.setString(3, pressure);
			prepStmt.setString(4, date);
			prepStmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Inserting error");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<WeatherDB> selectWeather() {
		List<WeatherDB> weather = new LinkedList<WeatherDB>();
		try {
			ResultSet result = stat.executeQuery("SELECT * from weather");
			int id;
			String city, temperature, pressure, date;
			while(result.next()) {
				id = result.getInt("id_weather");
				city = result.getString("city");
				temperature = result.getString("temperature");
				pressure = result.getString("pressure");
				date = result.getString("date");
				weather.add(new WeatherDB(id, city, temperature, pressure, date));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return weather;
	}
	
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Problem z zamknieciem polaczenia");
			e.printStackTrace();
		}
	}
	
	public WeatherDB(int id, String City, String Temperature, String Pressure, String Date) {
		this.id = id;
		this.City = City;
		this.Temperature = Temperature;
		this.Pressure = Pressure;
		this.Date = Date;
	}
}

