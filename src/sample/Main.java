package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource
                ("../res/Table_Sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        /*
        try {
            Class.forName("org.h2.Driver");
            // "jdbc:h2:~/{name of the database}", "username", "password"
            Connection conn = DriverManager.getConnection("jdbc:h2:~/MarkDB",
                    "test", "test");
            Statement st = conn.createStatement();
            st.execute("create table pawn(name varchar(20))");
            System.out.println("table created successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
        launch(args);
    }
}
