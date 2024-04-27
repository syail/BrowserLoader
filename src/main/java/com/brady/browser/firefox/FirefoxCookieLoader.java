package com.brady.browser.firefox;

import com.brady.browser.CookieLoader;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class FirefoxCookieLoader implements CookieLoader {
    private final Connection connection;

    public FirefoxCookieLoader(Path cookieStoragePath) throws SQLException {
        if(!cookieStoragePath.toFile().exists())
            throw new IllegalArgumentException(String.format("Path %s does not exist", cookieStoragePath));

        connection = DriverManager.getConnection("jdbc:sqlite:" + cookieStoragePath);
    }

    public HashMap<String, String> Load(String host) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT id, name, value FROM moz_cookies WHERE host = ?");

        stmt.setString(1, String.format(".%s", host));

        var resultSet = stmt.executeQuery();

        HashMap<String, String> cookies = new HashMap<>();

        while (resultSet.next()) {
            cookies.put(resultSet.getString("name"), resultSet.getString("value"));
        }
        return cookies;
    }

    public void Close() throws SQLException {
        connection.close();
    }
}
