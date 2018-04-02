package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import table.Schema;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

	private Stage stg;
	private Schema schem;
	
    @Override
    public void start(Stage primaryStage) throws Exception{
    	schem = new Schema();
    	stg = primaryStage;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("../res/MM.fxml").toURI().toURL());
        loader.setController(new MMController(this));
        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Property Management System");
        primaryStage.show();
    }


    public static void main(String[] args) {
        try {
            Class.forName("org.h2.Driver");
            // "jdbc:h2:~/{name of the database}", "username", "password"
            Connection conn = DriverManager.getConnection("jdbc:h2:~/MarkDB",
                    "test", "test");
            Statement st = conn.createStatement();
            st.execute("create table pawn(name varchar(20))");
            //st.execute("drop table pawn");
            System.out.println("table created successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        launch(args);
    }
    
    public void swapScene(char sceneabbr) throws IOException {
    	Parent root;
    	FXMLLoader loader;
    	switch	(sceneabbr) {
    		case 'e':
    			loader = new FXMLLoader(getClass()
    		            .getResource("../res/TE.fxml"));
    			loader.setController(new TEController(this));
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
    			loader.setController(new TEController(this));
    			root = (Parent)loader.load();
    	        stg.setScene(new Scene(root));
    	        break;
    		case 'p':
    			loader = new FXMLLoader(getClass()
    		            .getResource("../res/TP.fxml"));
    			loader.setController(new TEController(this));
    			root = (Parent)loader.load();
    	        stg.setScene(new Scene(root));
    	        break;
    		case 's':
    			loader = new FXMLLoader(getClass()
    		            .getResource("../res/TS.fxml"));
    			loader.setController(new TEController(this));
    			root = (Parent)loader.load();
    	        stg.setScene(new Scene(root));
    	        break;
    		case 'a':
    			loader = new FXMLLoader(getClass()
    		            .getResource("../res/TA.fxml"));
    			loader.setController(new TEController(this));
    			root = (Parent)loader.load();
    	        stg.setScene(new Scene(root));
    	        break;
    		case 'q':
    			Platform.exit();
    	        break;
    	}
    }
}
