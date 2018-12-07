package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    Button[] digitButtons = new Button[10];
    int i=0;
    public Library() throws SQLException, ClassNotFoundException {
    Stage primaryStage = new Stage();
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));
    Button button;
    resSet = statmt.executeQuery("SELECT * FROM books");
    if(!resSet.next()) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText("Библиотека пуста");
        alert.show();
    }
    else {
            resSet = statmt.executeQuery("SELECT * FROM books");
            while (resSet.next()) {
                Label book = new Label("User Name:");
                Image image = new Image(getClass().getResourceAsStream("..\\Images\\Book_lib.png"));
                book.setGraphic(new ImageView(image));
                int id = resSet.getInt("id");
                String path = resSet.getString("path");
                Integer value = resSet.getInt("value");
                File file = new File(path);
                book.setText(file.getName());
                grid.add(book, 0, id - 1);
                button = new Button();
                button.setOpacity(0);
                button.setMinSize(100, 99);
                button.toFront();
                digitButtons[i] = new Button(Integer.toString(i));
                button.setOnAction(e -> {
                    File f = new File(path);
                    if (f.exists() && !f.isDirectory()) {
                        new Controller().list_2.add(path);
                        new Controller().list_3.add(value);
                    }
                    else {
                        try {
                            new Base().delete(id);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Ошибка");
                            alert.setHeaderText(null);
                            alert.setContentText("Файла не существует");
                            alert.showAndWait();
                    }
                primaryStage.close();
                });
                grid.add(button, 0, id - 1);
            }
            Scene scene = new Scene(grid, 900, 750);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Library");
            primaryStage.show();
        }
    };}

