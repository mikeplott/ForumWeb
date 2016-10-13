package com.company;

import org.junit.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by michaelplott on 10/13/16.
 */
public class MainTest {
    public Connection startConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");
        Main.createTables(conn);
        return conn;
    }

    @Test
    public void testUser() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Mike", "123");
        User user = Main.selectUser(conn, "Mike");
        conn.close();
        assertTrue(user != null);
    }

    @Test
    public void testMessage() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Mike", "123");
        User user = Main.selectUser(conn, "Mike");
        Main.insertMessage(conn, -1, "Hello, world!", user.id);
        Message message = Main.selectMessage(conn, 1);
        conn.close();
        assertTrue(message != null);
    }

    @Test
    public void testReplies() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Mike", "123");
        Main.insertUser(conn, "Bob", "321");
        User mike = Main.selectUser(conn, "Mike");
        User bob = Main.selectUser(conn, "Bob");
        Main.insertMessage(conn, -1, "Hello, world!", mike.id);
        Main.insertMessage(conn, 1, "Sup brah!", bob.id);
        Main.insertMessage(conn, 1, "Another one!", bob.id);
        ArrayList<Message> replies = Main.selectReplies(conn, 1);
        conn.close();
        assertTrue(replies.size() == 2);
    }
}