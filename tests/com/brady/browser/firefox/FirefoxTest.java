package com.brady.browser.firefox;

import com.brady.browser.CookieLoader;
import com.brady.browser.LocalStorageItem;
import com.brady.browser.LocalStorageLoader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class FirefoxTest {

    @org.junit.Test
    public void loadFirefox() {
        try {
            CookieLoader cookieLoader = Firefox.GetCookieLoader();
            LocalStorageLoader localStorageLoader = Firefox.GetLocalStorageLoader("https", "github.com");

            assertNotNull(cookieLoader);
            assertNotNull(localStorageLoader);

            HashMap<String, String> cookies = cookieLoader.Load("github.com");
            LinkedList<LocalStorageItem> localStorageItems = localStorageLoader.Load();

            assertNotNull(cookies);
            assertNotNull(localStorageItems);

            System.out.println("Cookies:");
            for (var cookie : cookies.entrySet()) {
                System.out.println(cookie.getKey() + ": " + cookie.getValue());
            }

            System.out.println("Local Storage Items:");
            for(var item : localStorageItems) {
                System.out.println(item.getKey() + ": " + item.getValue());
            }

            cookieLoader.Close();
            localStorageLoader.Close();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void getBasePath() {
    }
}