package table;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

public class SQLMiddleMan {

	private Statement st;

	public SQLMiddleMan(Statement st) {
		this.st = st;
	}

	/**
	 * @param ar [name, phone]
	 */
	public void addCustomer(ArrayList<String> ar) {
		try {
			st.execute(String.format("INSERT INTO CUSTOMER VALUES (\"%s\", " +
					"\"%s\");", ar.get(0), ar.get(1)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	public void addEmployee(ar) {
		try {
			st.execute(String.format());
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	*/
}
