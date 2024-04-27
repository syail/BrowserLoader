# BrowserLoader

## Basic examples

### Firefox

```java
import com.brady.browser.firefox.Firefox;

public class Main {
    public static void main(String[] args) {
        CookieLoader cookieLoader = Firefox.GetCookieLoader();
        LocalStorageLoader localStorageLoader = Firefox.GetLocalStorageLoader("https", "github.com");

        HashMap<String, String> cookies = cookieLoader.Load("github.com");
        LinkedList<LocalStorageItem> localStorageItems = localStorageLoader.Load();

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
    }
}
```