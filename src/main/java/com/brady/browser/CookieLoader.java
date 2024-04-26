package com.brady.browser;

import java.sql.SQLException;
import java.util.HashMap;

public interface CookieLoader {
    public void Close() throws SQLException;
    public HashMap<String, String> GetCookies(String host) throws Exception;
}
