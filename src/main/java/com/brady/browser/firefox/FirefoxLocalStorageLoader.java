package com.brady.browser.firefox;

import com.brady.browser.LocalStorageItem;
import org.xerial.snappy.Snappy;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

public class FirefoxLocalStorageLoader {
    private final Connection connection;

    public FirefoxLocalStorageLoader(Path cookieStoragePath) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + cookieStoragePath.toString());
    }

    public LinkedList<LocalStorageItem> GetLocalStorage() throws SQLException, IOException {
        PreparedStatement stmt = connection.prepareStatement("SELECT key, compression_type, value FROM data");

        var resultSet = stmt.executeQuery();

        LinkedList<LocalStorageItem> items = new LinkedList<>();

        while (resultSet.next()) {
            String key = resultSet.getString("key");
            byte[] value = resultSet.getBytes("value");
            int CompressionType = resultSet.getInt("compression_type");

            LocalStorageItem item = null;

            if(CompressionType == FireFoxCompressionType.SNAPPY.ordinal()) {
                // Snappy decompression
                byte[] decompressed = Snappy.uncompress(value);

                item = new LocalStorageItem(key, new String(decompressed));
            } else {
                item = new LocalStorageItem(key, new String(value));
            }
            items.add(item);
        }
        return items;
    }

    public void Close() throws SQLException {
        connection.close();
    }
}
