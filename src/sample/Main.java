package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import table.SQLMiddleMan;
import table.Schema;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.h2.Driver;

public class Main extends Application {

	private Stage stg;
	private Schema schem;
	private SQLMiddleMan middleMan;
	private Statement st;
	
    @Override
    public void start(Stage primaryStage) throws Exception {
		try {
			Class.forName("org.h2.Driver");
			// "jdbc:h2:~/{name of the database}", "username", "password"
			Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/MarkDB",
					"test", "test");
			st = conn.createStatement();
			

			st.execute("DROP TABLE IF EXISTS EMPLOYEE");
			st.execute("DROP TABLE IF EXISTS PROPERTY");
			st.execute("DROP TABLE IF EXISTS CUSTOMER");
			st.execute("DROP TABLE IF EXISTS APPOINTMENT");
			st.execute("DROP TABLE IF EXISTS SALE");


			st.execute("CREATE TABLE IF NOT EXISTS EMPLOYEE(SSN CHAR(9) " +
					"PRIMARY KEY, NAME VARCHAR(100), PHONE CHAR(10))");

			st.execute("CREATE TABLE IF NOT EXISTS CUSTOMER(ID INT " +
					"PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(100), PHONE " +
					"CHAR(10))");

			st.execute("CREATE TABLE IF NOT EXISTS PROPERTY(ADDR VARCHAR" +
					"(256) PRIMARY KEY, SELLER INT, LISTPRICE DECIMAL(10," +
					"2), FOOTAGE INT, NBEDS TINYINT, NBATHS TINYINT, FOREIGN KEY (SELLER) REFERENCES CUSTOMER(ID))");

			st.execute("CREATE TABLE IF NOT EXISTS APPOINTMENT(ADDRESS " +
					"VARCHAR(256), CID INT, STIME " +
					"TIME, ETIME TIME, REFNUM INT, ESSN CHAR(9), PRIMARY KEY" +
					"(ADDRESS, CID, ESSN), FOREIGN KEY (CID) " +
					"REFERENCES CUSTOMER(ID), FOREIGN KEY (ADDRESS) " +
					"REFERENCES PROPERTY(ADDR),"
					+ " FOREIGN KEY (ESSN) REFERENCES EMPLOYEE(SSN))");

			st.execute("CREATE TABLE IF NOT EXISTS SALE(ADDRESS VARCHAR(256)" +
					", PRICE DECIMAL(10, 2), SID INT, CID INT, ESSN char(9), "
					+ "date DATETIME, REFNUM INT, PRIMARY KEY(ADDRESS, " +
					"DATE), FOREIGN KEY (cID) REFERENCES CUSTOMER(ID), " +
					"FOREIGN KEY (SID) REFERENCES CUSTOMER(ID), "
					+ "FOREIGN KEY (eSSN) REFERENCES EMPLOYEE(SSN))");

			System.out.println("tables loaded successfully");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		middleMan = new SQLMiddleMan(st);
    	schem = new Schema();
    	stg = primaryStage;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(
        		"../res/MM.fxml").toURI().toURL());
        loader.setController(new MMController(this));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Property Management System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public void swapScene(char sceneabbr) throws IOException {
    	Parent root;
    	FXMLLoader loader;
    	switch	(sceneabbr) {
    		case 'e':
    			loader = new FXMLLoader(getClass()
    		            .getResource("../res/TE.fxml"));
    			loader.setController(new TEController(this, middleMan));
    			root = (Parent)loader.load();
    	        stg.setScene(new Scene(root));
    	        break;
    		case 'm':
    			loader = new FXMLLoader(getClass()
    		            .getResource("../res/MM.fxml"));
    			loader.setController(new MMController(this));
    			root = (Parent)loader.load();
    	        stg.setScene(new Scene(root));
    			break;
    		case 'c':
    			loader = new FXMLLoader(getClass()
    		            .getResource("../res/TC.fxml"));
    			loader.setController(new CTController(this, middleMan));
    			root = (Parent)loader.load();
    	        stg.setScene(new Scene(root));
    	        break;
    		case 'p':
    			loader = new FXMLLoader(getClass()
    		            .getResource("../res/TP.fxml"));
    			loader.setController(new TPController(this, middleMan));
    			root = (Parent)loader.load();
    	        stg.setScene(new Scene(root));
    	        break;
    		case 's':
    			loader = new FXMLLoader(getClass()
    		            .getResource("../res/TS.fxml"));
    			loader.setController(new TSController(this, middleMan));
    			root = (Parent)loader.load();
    	        stg.setScene(new Scene(root));
    	        break;
    		case 'a':
    			loader = new FXMLLoader(getClass()
    		            .getResource("../res/TA.fxml"));
    			loader.setController(new TAController(this, middleMan));
    			root = (Parent)loader.load();
    	        stg.setScene(new Scene(root));
    	        break;
    		case 'q':
    			Platform.exit();
    	        break;
    	}
    }
}
