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
			st.execute("INSERT INTO CUSTOMER VALUES (" +
						ar.get(0) + ", " + ar.get(1) +
					")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
