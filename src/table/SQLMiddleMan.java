package table;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

public class SQLMiddleMan {

	private Statement st;

	private static final String PRIMARY_KEY_VIOLATION = "Unique index or " +
			"primary key violation";

	public SQLMiddleMan(Statement st) {
		this.st = st;
	}

	public void deleteEmployee(Employee emp) {
		try {
			st.execute("DELETE FROM EMPLOYEE WHERE SSN = \'" + emp.getSSN() +
					"\'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteCustomer(Customer cust) {
		try {
			st.execute(String.format("DELETE FROM EMPLOYEE WHERE SSN = '%s'",
					cust.getID()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void loadEmpData(ObservableList<Employee> data) {
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM EMPLOYEE");
			rs.first();
			while (!rs.isAfterLast()) {
				data.add(new Employee(rs.getString(1), rs.getString(2), rs
						.getString(3)));
				rs.next();
			}
		} catch (SQLException e) {
			if (e.getMessage().contains("No data is available")) {
				return;
			}
			e.printStackTrace();
		}
	}

	public void loadCustData(ObservableList<Customer> data) {
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM CUSTOMER");
			rs.first();
			while (!rs.isAfterLast()) {
				data.add(new Customer(rs.getString(1), rs.getString(2), rs
						.getString(3)));
				rs.next();
			}
		} catch (SQLException e) {
			if (e.getMessage().contains("No data is available")) {
				return;
			}
			e.printStackTrace();
		}
	}

	public void loadPropertyData(ObservableList<Property> data) {
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM PROPERTY");
			rs.first();
			while (!rs.isAfterLast()) {
				data.add(new Property(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6)));
				rs.next();
			}
		} catch(SQLException e) {
			if (e.getMessage().contains("No data is available")) {
				return;
			}
			e.printStackTrace();
		}
	}

	public int addEmployee(Employee emp) {
		try {
			ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM EMPLOYEE " +
					"WHERE SSN = " + emp.getSSN());
			rs.first();
			if (rs.getInt(1) == 0) {
				st.execute("INSERT INTO EMPLOYEE VALUES (\'" + emp.getSSN() +
						"\', \'" + emp.getName() + "\', \'" + emp.getPnumber
						() + "\')");
				return 0;
			}
		} catch (SQLException e) {
			if (e.getMessage().contains("PHONE")) {
				return 1;
			}
			else if (e.getMessage().contains("NAME")) {
				return 2;
			}
			else if (e.getMessage().contains(PRIMARY_KEY_VIOLATION)) {
				return 3;
			}
			else if (e.getMessage().contains("expected \"ALL, ANY, SOME\"")) {
				return 4;
			}
			e.printStackTrace();
		}
		return 5;
	}

	public int addCustomer(Customer cust) {
		try {
			ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM CUSTOMER " +
					"WHERE ID = " + cust.getID());
			rs.first();
			if (rs.getInt(1) == 0) {
				st.execute(String.format("INSERT INTO CUSTOMER VALUES ('%s', " +
						"'%s', '%s');", cust.getID(), cust.getName(), cust
						.getPnumber()));
				return 0;
			}
		} catch (SQLException e) {
			if (e.getMessage().contains("PHONE")) {
				return 1;
			}
			else if (e.getMessage().contains("NAME")) {
				return 2;
			}
			else if (e.getMessage().contains(PRIMARY_KEY_VIOLATION)) {
				return 3;
			}
			else if (e.getMessage().contains("expected \"ALL, ANY, SOME\"")) {
				return 4;
			}
			e.printStackTrace();
		}
		return 5;
	}

	public int updateEmployee(Employee newEmp, Employee oldEmp) {
		try {
			if (!newEmp.getSSN().equals(oldEmp.getSSN())) {
				st.execute("UPDATE EMPLOYEE SET SSN = \'" + newEmp.getSSN()
						+ "\' WHERE SSN = \'" + oldEmp.getSSN() + "\'");
			}
			if (!newEmp.getName().equals(oldEmp.getName())) {
				st.execute("UPDATE EMPLOYEE SET NAME = \'" + newEmp.getName
						() + "\' WHERE NAME = \'" + oldEmp.getName() + "\' " +
						"AND SSN = \'" + newEmp.getSSN() + "\'");
			}
			if (!newEmp.getPnumber().equals(oldEmp.getPnumber())) {
				st.execute("UPDATE EMPLOYEE SET PHONE = \'" + newEmp
						.getPnumber() + "\' WHERE PHONE = \'" + oldEmp
						.getPnumber() + "\' AND SSN = \'" + newEmp.getSSN() +
						"\'");
			}
			return 0;
		} catch (SQLException e) {
			if (e.getMessage().contains("PHONE")) {
				return 1;
			}
			else if (e.getMessage().contains("NAME")) {
				return 2;
			}
			else if (e.getMessage().contains(PRIMARY_KEY_VIOLATION)) {
				return 3;
			}
			e.printStackTrace();
		}
		return 4;
	}

	public int updateCustomer(Customer newCust, Customer oldCust) {
		try {
			if (!newCust.getID().equals(oldCust.getID())) {
				st.execute(String.format("UPDATE CUSTOMER SET ID = '%s' " +
						"WHERE ID = '%s'", newCust.getID(), oldCust.getID()));
			}
			if (!newCust.getName().equals(oldCust.getName())) {
				st.execute(String.format("UPDATE CUSTOMER SET NAME = '%s' " +
						"WHERE NAME = '%s' AND ID = '%s'", newCust.getName(),
						oldCust.getName(), newCust.getID()));
			}
			if (!newCust.getPnumber().equals(oldCust.getPnumber())) {
				st.execute(String.format("UPDATE CUSTOMER SET PHONE = '%s' " +
						"WHERE PHONE = '%s' AND ID = '%s'", newCust
						.getPnumber(), oldCust.getPnumber(), newCust.getID()));
			}
			return 0;
		} catch (SQLException e) {
			if (e.getMessage().contains("PHONE")) {
				return 1;
			}
			else if (e.getMessage().contains("NAME")) {
				return 2;
			}
			else if (e.getMessage().contains(PRIMARY_KEY_VIOLATION)) {
				return 3;
			}
			e.printStackTrace();
		}
		return 4;
	}
	
	//Loads the customer names/ids
	public ObservableList<String> loadCust() {
		ObservableList<String> cust = FXCollections.observableArrayList();
		cust.add("Select a Customer...");
		try {
			ResultSet rs = st.executeQuery("SELECT ID,NAME FROM CUSTOMER");
			rs.first();
			while (!rs.isAfterLast()) {
				cust.add(rs.getString(1) + "/" + rs.getString(2));
				rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cust;
	}

	public ObservableList<String> loadEmployee() {
		ObservableList<String> emp = FXCollections.observableArrayList();
		emp.add("Select an Employee...");
		try {
			ResultSet rs = st.executeQuery("SELECT SSN, NAME FROM Employee");
			rs.first();
			while (!rs.isAfterLast()) {
				emp.add(rs.getString(1) + "/" + rs.getString(2));
				rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emp;
	}

	public ObservableList<String> loadProperty() {
		ObservableList<String> prop= FXCollections.observableArrayList();
		prop.add("Select a Property...");
		try {
			ResultSet rs = st.executeQuery("(SELECT addr FROM LAND) UNION (SELECT addr FROM HOUSE)");
			rs.first();
			while (!rs.isAfterLast()) {
				prop.add(rs.getString(1));
				rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prop;
	}
}
