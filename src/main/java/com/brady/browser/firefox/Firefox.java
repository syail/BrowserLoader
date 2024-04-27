package com.brady.browser.firefox;

import com.brady.browser.CookieLoader;
import com.brady.browser.LocalStorageLoader;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class Firefox {
    private static final String FIREFOX_COOKIE_STORAGE_NAME = "cookies.sqlite";
    private static final String FIREFOX_LOCAL_STORAGE_NAME = "data.sqlite";

    public static CookieLoader GetCookieLoader() throws SQLException {
        return new FirefoxCookieLoader(GetCookieStoragePath());
    }

    /**
     * Get a LocalStorageLoader for a given schema and host
     * @param schema The schema of the website (http, https)
     * @param host The host of the website
     * @return A LocalStorageLoader for the given schema and host
     */
    public static LocalStorageLoader GetLocalStorageLoader(String schema, String host) throws SQLException {
        return new FirefoxLocalStorageLoader(GetLocalStoragePath(schema, host));
    }

    /**
     * Get the path to the cookie storage file
     * @return The path to the cookie storage file
     */
    public static Path GetCookieStoragePath() {
        return Paths.get(GetBasePath().toString(), FIREFOX_COOKIE_STORAGE_NAME);
    }

    public static Path GetLocalStoragePath(String schema, String host) {
        return Paths.get(GetBasePath().toString(), "storage", "default", String.format("%s+++%s", schema, host), "ls", FIREFOX_LOCAL_STORAGE_NAME);
    }

    private static Path GetBasePath() {
        Path profileDir = Paths.get(System.getenv("APPDATA"), "Mozilla", "Firefox", "Profiles");

        File dir = new File(profileDir.toString());

        String[] profileDirs = dir.list((dir1, name) -> name.contains("-release"));

        assert profileDirs != null;
        assert profileDirs.length > 0;

        return Paths.get(profileDir.toString(), profileDirs[0]);
    }
}
