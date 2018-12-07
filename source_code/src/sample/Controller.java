package sample;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;

import static sample.Base.resSet;
import static sample.Base.statmt;


public class Controller {
    public TextArea Textall;
    public TextArea Text_chapter;
    int i=10;
    double a=0;
    int scroll;
    File file;
    String text;
    String word;
    String path_main;

    static ObservableList<String> list = FXCollections.observableArrayList();
    static ObservableList<Integer> list_1 = FXCollections.observableArrayList();
    static ObservableList<String> list_2 = FXCollections.observableArrayList();
    static ObservableList<Integer> list_3 = FXCollections.observableArrayList();

    public void onClickMethod(ActionEvent actionEvent) throws ClassNotFoundException, SQLException, IOException {
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            file = fileopen.getSelectedFile();
            int ind = file.getName().indexOf('.');
            if(file.getName().substring(ind+1,file.getName().length()).equals("pdf")) {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle("Load");
                alert.setContentText("Идёт загрузка. Подождите");
                alert.show();
                PDDocument document = PDDocument.load(file);
                PDFTextStripper pdfStripper = new PDFTextStripper();
                text = pdfStripper.getText(document);
                document.close();
                alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                alert.close();
                Textall.setFont(Font.font("Tahoma", FontWeight.NORMAL,10));
                Textall.setText(text);
                Text_chapter.setText(file.getName());
                path_main=file.getPath();
                Base.WriteDB(file.getPath(),0);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Формат файла не поддерживается");
                alert.show();
            }
        }
    }

    public void onLiib(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        new Library();
        list_2.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                path_main = list_2.get(list_2.size()-1);
                File fil = new File(path_main);
                PDDocument document = null;
                try {
                    Alert alert = new Alert(Alert.AlertType.NONE);
                    alert.setTitle("Load");
                    alert.setContentText("Идёт загрузка. Подождите");
                    alert.show();
                    document = PDDocument.load(fil);
                    PDFTextStripper pdfStripper = new PDFTextStripper();
                    text = pdfStripper.getText(document);
                    document.close();
                    alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                    alert.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(text);
                Textall.setFont(Font.font("Tahoma", FontWeight.NORMAL,10));
                Textall.setText(text);
                Text_chapter.setText(fil.getName());
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
        if(i!=20)
            Textall.setFont(Font.font("Tahoma", FontWeight.NORMAL, i));
        else
            i--;
    }

    public void on_decrease(ActionEvent actionEvent) {
        i--;
        if(i!=8)
            Textall.setFont(Font.font("Tahoma", FontWeight.NORMAL,i));
        else
            i++;
    }

    public void on_search(ActionEvent actionEvent) {
        new Search(text);
        list.addListener(new ListChangeListener<String>() {
            public void onChanged(Change<? extends String> c) {
                word=list.get(list.size() - 1);
            }
        });
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
        Label book = new Label("");
            try {
                final URL url = new URL("http://www.google.com");
                final URLConnection conn = url.openConnection();
                conn.connect();
                conn.getInputStream().close();
                book.setText(new Translate().qwe(Textall.getSelectedText()));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                book.setText("Проверьте соединение с Интернетом");
            }

        grid.add(book, 0, 0);
        Scene scene = new Scene(grid, 900, 750);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Translate");
        primaryStage.show();
    }


    public void set_zak(ActionEvent actionEvent) throws SQLException, InterruptedException {
        a = Textall.getScrollTop();
        int id=0;
        String path = null;
        resSet = statmt.executeQuery("SELECT * FROM books");
        while(resSet.next())
        {
            id = resSet.getInt("id");
            path = resSet.getString("path");
            if(path.equals(path_main))
            {
                new Base().delete(id);
                new Base().WriteDB( path, (int) a);
                break;
            }
        }
    }}