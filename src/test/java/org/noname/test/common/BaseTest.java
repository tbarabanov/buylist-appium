package org.noname.test.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.net.URL;

import static java.lang.System.getProperty;
import static org.noname.core.appium.CapabilitiesProvider.*;

/**
 * @author Timofei B.
 */
public class BaseTest {

    public static final String APPIUM_URL_PROPERTY_KEY = "appium.url";
    public static final String DEFAULT_APPIUM_URL = "http://localhost:4723/wd/hub";

    private AppiumDriver driver;

    @BeforeTest
    public void setUpAppium() throws IOException {
        // android app only
        driver = new AndroidDriver(
                new URL(getProperty(APPIUM_URL_PROPERTY_KEY,
                        DEFAULT_APPIUM_URL)),
                getInstance(getProperty(CAPABILITIES_PROVIDER_PROPERTY_KEY,
                        CONFIGURATION)).get());
    }

    @AfterTest
    public void tearDownAppium() {
        if (getDriver() != null)
            getDriver().quit();
    }

    @AfterMethod
    public void cleanUpTheApp() {
        if (getDriver() != null)
            getDriver().resetApp();
    }

    /**
     * @return
     */
    public AppiumDriver getDriver() {
        return driver;
    }

}
