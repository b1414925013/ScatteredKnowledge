package com.heima;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HsqlTest {

    public static void main(String[] args) throws SQLException {
        initData();
        final Connection conn = getConnection();
        final Statement stmt = conn.createStatement();
        final ResultSet rs = stmt.executeQuery("select * from tb_books");
        while (rs.next()) {
            System.out.println(rs.getString("bookname") + "=>" + rs.getString("author"));
        }
        rs.close();
        stmt.close();
        conn.close();

    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/bookdb", "SA", "");
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void initData() throws SQLException {

        final Connection conn = getConnection();
        final Statement stmt = conn.createStatement();
        for (int i = 10; i < 30; i++) {
            final String sql = "insert into tb_books(bookname,author) values('BookName" + i + "','Author" + i + "')";
            System.out.println(sql);
            stmt.addBatch(sql);
        }

        stmt.executeBatch();
        stmt.close();
        conn.close();
    }

}