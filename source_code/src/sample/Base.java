package sample;

import java.sql.*;


public class Base {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:Libmy.s3db");
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'books' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'path' text,'value' INT);");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB(String path_book, int value_book) throws SQLException
    {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO books ('path', 'value') VALUES ( ?, ?)");
        statement.setString(1, path_book);
        statement.setInt(2, value_book);
        statement.execute();
        System.out.println("Таблица заполнена");
    }

    // -------- Вывод таблицы--------
    public static void ReadDB() throws ClassNotFoundException, SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM books");
        while(resSet.next())
        {
            int id = resSet.getInt("id");
            String  path = resSet.getString("path");
            Integer value = resSet.getInt("value");
            System.out.println();
        }
    }

    // -------- Удаление элемента--------
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql) ;
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
}

    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();
        resSet.close();
    }
}