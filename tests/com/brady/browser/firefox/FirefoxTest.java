package com.brady.browser.firefox;

import java.nio.file.Path;

import static org.junit.Assert.*;

public class FirefoxTest {

    @org.junit.Test
    public void getCookieStoragePath() {
        System.out.println(Firefox.GetCookieStoragePath());
    }

    @org.junit.Test
    public void getCookies() {
        Path cookieStoragePath = Firefox.GetCookieStoragePath();

        try {
            FirefoxCookieLoader cookieLoader = new FirefoxCookieLoader(cookieStoragePath);
            var cookies = cookieLoader.GetCookies("youtube.com");
            cookieLoader.Close();

            System.out.println(cookies);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void getLocalStorage() {
        Path localStoragePath = Firefox.GetLocalStoragePath("https", "youtube.com");

        System.out.println(localStoragePath);

        try {
            FirefoxLocalStorageLoader storageLoader = new FirefoxLocalStorageLoader(localStoragePath);

            var items = storageLoader.GetLocalStorage();

            storageLoader.Close();

            for (var item : items) {
                System.out.println(item.key + " " + item.value + " ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void getBasePath() {
    }
}