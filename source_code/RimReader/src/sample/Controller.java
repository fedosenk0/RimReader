package sample;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static sample.Base.resSet;
import static sample.Base.statmt;


public class Controller {
    public TextArea Textall;
    public TextArea Text_chapter;

    public Label Text_all;
    File file;
    int i=10;
    double a=0;
    String text;
    String word;
    String translate;
    int scroll;

    static ObservableList<String> list = FXCollections.observableArrayList();
    static ObservableList<Integer> list_1 = FXCollections.observableArrayList();
    static ObservableList<String> list_2 = FXCollections.observableArrayList();
    static ObservableList<Integer> list_3 = FXCollections.observableArrayList();

    public void onClickMethod(ActionEvent actionEvent) throws ClassNotFoundException, SQLException, IOException {
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            file = fileopen.getSelectedFile();
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            text = pdfStripper.getText(document);
            document.close();
            System.out.println(text);
            Textall.setFont(Font.font("Tahoma", FontWeight.NORMAL,i));
            Textall.setText(text);
            Text_chapter.setText(file.getName());
            Base.WriteDB(file.getPath(),0);
        }
        }



    public void onLiib(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        new Library();
        list_2.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                Textall.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
                System.out.println("hi");
                File fil = new File(list_2.get(list_2.size()-1));

                PDDocument document = null;
                try {
                    document = PDDocument.load(fil);
                    PDFTextStripper pdfStripper = new PDFTextStripper();
                    text = pdfStripper.getText(document);
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(text);
                Textall.setFont(Font.font("Tahoma", FontWeight.NORMAL,10));
                Textall.setText(text);
                Text_chapter.setText(file.getName());
                list_3.addListener(new ListChangeListener<Integer>() {
                    @Override
                    public void onChanged(Change<? extends Integer> c) {
                        Textall.setScrollTop(list_3.get(list_3.size()-1));
                    }
                });

            }
        });
    }

    public void onIncrease(ActionEvent actionEvent) {
        i++;
        Textall.setFont(Font.font("Tahoma", FontWeight.NORMAL,i));
    }

    public void on_decrease(ActionEvent actionEvent) {
        i--;
        Textall.setFont(Font.font("Tahoma", FontWeight.NORMAL,i));
        //Textall.setScrollTop(a);
    }

    public void on_search(ActionEvent actionEvent) {
        new Search(text);
        list.addListener(new ListChangeListener<String>() {
            public void onChanged(Change<? extends String> c) {
                word=list.get(list.size() - 1);

            }
        }
        );
        list_1.addListener(new ListChangeListener<Integer>() {
            @Override
            public void onChanged(Change<? extends Integer> c) {

                scroll=list_1.get(list_1.size() - 1);
                Textall.selectPositionCaret(text.indexOf(word)-scroll);
                Textall.deselect();
                Textall.selectNextWord();
            }
        });


    }

    public void Translate_Text(ActionEvent actionEvent) throws IOException, ParseException {
        Stage primaryStage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Label book = new Label("User Name:");
        book.setText(new Translate().qwe(Textall.getSelectedText()));
        grid.add(book, 0, 0);
        Scene scene = new Scene(grid, 900, 750);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Translate");
        primaryStage.show();
    }


    public void set_zak(ActionEvent actionEvent) throws SQLException, InterruptedException {
        a = Textall.getScrollTop();


        String path = null;
        while (true) {


            try {
                resSet = statmt.executeQuery("SELECT * FROM books");
                path = resSet.getString("path");

            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println(file.getPath());
            System.out.println("slow");
            System.out.println(path);
            System.out.println("fast");

            if (file.getPath() == path) {
                Base.WriteDB(path, (int) a);
                break;
            }
        }
    }}