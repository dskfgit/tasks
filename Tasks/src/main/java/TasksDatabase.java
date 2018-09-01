
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TasksDatabase {

	// private static final long serialVersionUID = 1L;
	private static final String DBCON = "jdbc:mysql://localhost/tasks?user=root&password=Trizon1604!&useSSL=false&serverTimezone=Australia/Melbourne";
	private static final String DBDRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String SQL_SELECT_ALL_TASKS = "SELECT * FROM tasks";
	private static final Logger LOG = LoggerFactory.getLogger(TasksDatabase.class);
	private Connection mConnection;

	public TasksDatabase() {
		LOG.info("Tasks Database called.");
	}

	/*
	public static void main(String[] args) {
		TasksDatabase td = new TasksDatabase();
		td.init();
		ResultSet rs = null;
		try {
			if (rs.next()) {
				String name = rs.getString(2);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
    */
	
	public boolean init() {

		boolean gtg = true;

		Connection conn = null;

		try {
			LOG.info("Creating Database connection...");
			Class.forName(DBDRIVER).newInstance();
			LOG.info("Driver loaded: " + DBDRIVER);
			LOG.info("Attempting connection... " + DBCON);
			conn = DriverManager.getConnection(DBCON);
			// Do something with the Connection
			mConnection = conn;

		} catch (SQLException ex) {
			LOG.error("SQL Exception on creating Database connection!",ex);
			ex.printStackTrace();
			gtg = false;
		} catch (IllegalAccessException iae) {
			LOG.error("Illegal Access Exception on creating Database connection!",iae);
			iae.printStackTrace();
			gtg = false;
		} catch (InstantiationException ie) {
			LOG.error("Instantiation Exception on creating Database connection!",ie);
			ie.printStackTrace();
			gtg = false;
		} catch (ClassNotFoundException cnfe) {
			LOG.error("Class Not Found Exception on creating Dtabase connection!",cnfe);
			cnfe.printStackTrace();
			gtg = false;
		}

		return gtg;
	}

	public ArrayList<Task> getAllTasks() {

		ResultSet rs = null;
		ArrayList<Task> tasks = new ArrayList<Task>();

		LOG.info("Loading tasks from database...");
		
		try {
			CallableStatement sql = mConnection.prepareCall(SQL_SELECT_ALL_TASKS);
			rs = sql.executeQuery();
			while (rs.next()) {
				Task task = new Task();
				task.setId(rs.getInt(1));
				task.setName(rs.getString(2));
				task.setDetails(rs.getString(3));
				task.setDuedate(rs.getDate(4));
				task.setAlert(rs.getInt(5));
				task.setPriority(rs.getInt(6));
				tasks.add(task);
			}

		LOG.info("Finished loading tasks.");
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		}

		return tasks;

	}

}
