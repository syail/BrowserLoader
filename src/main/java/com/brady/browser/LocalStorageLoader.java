package com.brady.browser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

public interface LocalStorageLoader {
    void Close() throws SQLException;
    LinkedList<LocalStorageItem> Load() throws SQLException, IOException;
}
