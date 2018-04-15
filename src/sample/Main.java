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
			st.execute("DROP TABLE IF EXISTS LAND");
			st.execute("DROP TABLE IF EXISTS HOUSE");
			st.execute("DROP TABLE IF EXISTS PROPERTY");
			st.execute("DROP TABLE IF EXISTS CUSTOMER");
			st.execute("DROP TABLE IF EXISTS APPOINTMENT");
			st.execute("DROP TABLE IF EXISTS SALE");

			st.execute("CREATE TABLE IF NOT EXISTS EMPLOYEE(SSN CHAR(9) " +
					"PRIMARY KEY, name VARCHAR(100), PHONE CHAR(10))");
			st.execute("CREATE TABLE IF NOT EXISTS PROPERTY(ADDR VARCHAR" +
					"(256) PRIMARY KEY, SELLER INT FOREIGN KEY REFERENCES CUSTOMER(ID), LISTPRICE DECIMAL(10," +
					" 2), FOOTAGE INT, NBEDS TINYINT, NBATHS TINYINT)");
			st.execute("CREATE TABLE IF NOT EXISTS CUSTOMER(ID INT " +
					"PRIMARY KEY, name VARCHAR(100), phone CHAR(10))");

			st.execute("CREATE TABLE IF NOT EXISTS APPOINTMENT(address " +
					"VARCHAR(256), cid CHAR(9), sTime " +
					"TIME, eTime TIME, refNum INT, eSSN char(9), type char(6)" +
					", PRIMARY KEY(address, cid))");

			st.execute("CREATE TABLE IF NOT EXISTS SALE(address VARCHAR(256)," +
					" price DECIMAL(10, 2), sID INT, cID CHAR(9), eSSN CHAR" +
					"(9), date DATETIME, refNum INT, PRIMARY KEY(ADDRESS, " +
					"DATE))");
			System.out.println("tables created successfully");
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
