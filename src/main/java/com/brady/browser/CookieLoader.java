package com.brady.browser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public interface CookieLoader {
    void Close() throws SQLException;
    HashMap<String, String> Load(String host) throws SQLException, IOException;
}
