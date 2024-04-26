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
        connection = DriverManager.getConnection("jdbc:sqlite:" + cookieStoragePath.toString());
    }

    public HashMap<String, String> GetCookies(String host) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("SELECT id, name, value FROM moz_cookies WHERE host = ?");

        pstmt.setString(1, String.format(".%s", host));

        var resultSet = pstmt.executeQuery();

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
