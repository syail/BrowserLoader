package com.brady.browser.firefox;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Firefox {
    private static final String FIREFOX_COOKIE_STORAGE_NAME = "cookies.sqlite";
    private static final String FIREFOX_LOCAL_STORAGE_NAME = "data.sqlite";

    public static Path GetCookieStoragePath() {
        return Paths.get(GetBasePath().toString(), FIREFOX_COOKIE_STORAGE_NAME);
    }

    public static Path GetLocalStoragePath(String schema, String host) {
        return Paths.get(GetBasePath().toString(), "storage", "default", String.format("%s+++%s", schema, host), "ls", FIREFOX_LOCAL_STORAGE_NAME);
    }

    public static Path GetBasePath() {
        Path profileDir = Paths.get(System.getenv("APPDATA"), "Mozilla", "Firefox", "Profiles");

        File dir = new File(profileDir.toString());

        String[] profileDirs = dir.list((dir1, name) -> name.contains("-release"));

        assert profileDirs != null;
        assert profileDirs.length > 0;

        return Paths.get(profileDir.toString(), profileDirs[0]);
    }
}
