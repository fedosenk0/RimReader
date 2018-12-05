package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Search {

    String mas[];
    String str_1;
    String str_2;
    String str_3;
    int i=0;
    int counter=0;
    boolean comp=false;
    TextField text = new TextField();
    public Search(String str){
        Stage primaryStage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(text, 0, 0);
        Button button=new Button("OK");
        grid.add(button,2,0);
        Scene scene = new Scene(grid, 300, 50);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Search");
        primaryStage.show();
        mas=str.split(" ");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                str_1=text.getText();

                    i=0;
                    counter=0;


              while(true){
                  System.out.println(mas[i]);
                   str_2=mas[i].replaceAll("[,.!/:;()<>«»?%$#@&*^+=№—_|•]","");
                   str_3=str_2.replaceAll("\n|\r\n", "");
                   comp=str_1.equals(str_3);
                  for (char element : mas[i].toCharArray()){
                      if (element == '\n') counter++;
                  }

                   if(comp){
                        new Controller().list.add(str_1);
                       new Controller().list_1.add(counter);
                        text.setText("Слово найдено");
                        break;
                   }
                   if(i==mas.length-1){
                       text.setText("Слово не найдено");
                       break;
                   }
                  i++;
                                  }
            }
        });

    }

}
