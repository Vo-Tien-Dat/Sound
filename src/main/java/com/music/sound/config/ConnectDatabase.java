package com.music.sound.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.lang.Exception;

public class ConnectDatabase {
    private static Connection connection;

    public static void run() throws Exception {
        String username = "root";
        String password = "Dat081101";
        String url = "";
        connection = DriverManager.getConnection(url, username, password);
        

    }
}
