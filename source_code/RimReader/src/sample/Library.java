package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;

import static sample.Base.resSet;
import static sample.Base.statmt;


public class Library {
    public Library() throws SQLException, ClassNotFoundException {
    Stage primaryStage = new Stage();
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    int id=0;
    grid.setPadding(new Insets(25, 25, 25, 25));
        Button button = null;

        resSet = statmt.executeQuery("SELECT * FROM books");
        while(resSet.next())
       {
           Label book = new Label("User Name:");
           Image image = new Image(getClass().getResourceAsStream("..\\Images\\Book_lib.png"));
           book.setGraphic(new ImageView(image));
           id = resSet.getInt("id");
            String  path = resSet.getString("path");
            Integer value = resSet.getInt("value");
            File file = new File(path);
            book.setText(file.getName());
            grid.add(book, 0, id-1);
           button=new Button("OK");
           grid.add(button, 10, id-1);
        }
       
    Scene scene = new Scene(grid, 900, 750);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Library");
    primaryStage.show();

    button.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String  path = null;
            System.out.println("HI");
            String  text = null;
            int index=10;
            int value = 0;
            while(true) {


                    try {
                        resSet = statmt.executeQuery("SELECT * FROM books");
                        index = resSet.getInt("id");
                        path = resSet.getString("path");
                        index = resSet.getInt("id");
                        value = resSet.getInt("value");
                        System.out.println(index);
                        System.out.println(grid.impl_getRowCount());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                if(grid.impl_getRowCount() ==index) {
                    new Controller().list_2.add(path);
                    new Controller().list_3.add(value);

                    break;
                }
            }}



    });}}

