package pl.codeleak.selenium.support;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;
import static pl.codeleak.selenium.support.CaseFormat.toLowerUnderscore;

public class SeleniumTestExecutionListener extends AbstractTestExecutionListener {

    private WebDriver webDriver;

    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public void prepareTestInstance(TestContext testContext) throws Exception {
        if (webDriver != null) {
            return;
        }
        ApplicationContext context = testContext.getApplicationContext();
        if (context instanceof ConfigurableApplicationContext) {

            SeleniumTest annotation = findAnnotation(
                    testContext.getTestClass(), SeleniumTest.class);

            URL seleniumHubUrl = buildSeleniumHubUrl();
            DesiredCapabilities capability = buildRemoteCapability();

            webDriver = BeanUtils.instantiateClass(annotation.driver().getDeclaredConstructor(
                    URL.class, Capabilities.class), capability);

            ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
            ConfigurableListableBeanFactory bf = configurableApplicationContext.getBeanFactory();
            bf.registerResolvableDependency(WebDriver.class, webDriver);
        }
    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        if (webDriver != null) {
            SeleniumTest annotation = findAnnotation(
                    testContext.getTestClass(), SeleniumTest.class);
            webDriver.get(annotation.baseUrl());
        }
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        if (testContext.getTestException() == null) {
            return;
        }
        File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        String testName = toLowerUnderscore(testContext.getTestClass().getSimpleName());
        String methodName = toLowerUnderscore(testContext.getTestMethod().getName());
        Files.copy(screenshot.toPath(),
                Paths.get("screenshots", testName + "_" + methodName + "_" + screenshot.getName()));
    }

    private static DesiredCapabilities buildRemoteCapability() {
        DesiredCapabilities capability = DesiredCapabilities.firefox();
        capability.setBrowserName("firefox");
        capability.setPlatform(Platform.LINUX);
        return capability;
    }

    private static URL buildSeleniumHubUrl() throws MalformedURLException {
        String seleniumHost = System.getProperty("seleniumHost");
        String seleniumPort = System.getProperty("seleniumPort");

        return new URL("http://" + seleniumHost + ":" + seleniumPort + "/wd/hub");
    }

}
