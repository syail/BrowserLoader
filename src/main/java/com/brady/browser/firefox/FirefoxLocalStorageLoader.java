package com.brady.browser.firefox;

import com.brady.browser.LocalStorageItem;
import com.brady.browser.LocalStorageLoader;
import org.xerial.snappy.Snappy;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class FirefoxLocalStorageLoader implements LocalStorageLoader {
    private final Connection connection;

    public FirefoxLocalStorageLoader(Path cookieStoragePath) throws SQLException {
        if(!cookieStoragePath.toFile().exists())
            throw new IllegalArgumentException(String.format("Path %s does not exist", cookieStoragePath));

        connection = DriverManager.getConnection("jdbc:sqlite:" + cookieStoragePath);
    }

    public LinkedList<LocalStorageItem> Load() throws SQLException, IOException {
        var resultSet = connection.prepareStatement("SELECT key, compression_type, value FROM data").executeQuery();

        LinkedList<LocalStorageItem> items = new LinkedList<>();

        while (resultSet.next()) {
            String key = resultSet.getString("key");
            byte[] value = resultSet.getBytes("value");
            int CompressionType = resultSet.getInt("compression_type");

            LocalStorageItem item;

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
