package pl.codeleak.selenium.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class WebDriverFactory {
    public static WebDriver buildWebDriver() {
        System.setProperty("webdriver.gecko.driver", "/home/yntonfon/IdeaProjects/spring-boot-thymeleaf/geckodriver");
//        System.setProperty("webdriver.firefox.bin", System.getenv("SELENIUM_FIREFOX_BIN"));

        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("-headless");

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);

        return new FirefoxDriver(firefoxOptions);
    }
}
