package org.noname.test.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.noname.core.appium.CapabilitiesProvider;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.net.URL;

import static org.noname.core.appium.CapabilitiesProvider.CAPABILITIES_PROVIDER_PROPERTY_KEY;
import static org.noname.core.appium.CapabilitiesProvider.CONFIGURATION;

/**
 * @author Timofei B.
 */
public class TestBase {

    public static final String APPIUM_URL_PROPERTY_KEY = "appium.url";
    public static final String DEFAULT_APPIUM_URL = "http://localhost:4723/wd/hub";

    private AppiumDriver driver;

    @BeforeTest
    public void setUpAppium() throws IOException {
        // android app only
        driver = new AndroidDriver(
                new URL(System.getProperty(APPIUM_URL_PROPERTY_KEY,
                        DEFAULT_APPIUM_URL)),
                CapabilitiesProvider.getInstance(System.getProperty(CAPABILITIES_PROVIDER_PROPERTY_KEY,
                        CONFIGURATION)).get());
    }

    @AfterTest
    public void tearDownAppium() {
        if (driver != null)
            driver.quit();
    }

    @AfterMethod
    public void cleanUpTheApp() {
        if (driver != null)
            driver.resetApp();
    }

    /**
     * @return
     */
    public AppiumDriver getDriver() {
        return driver;
    }

}
