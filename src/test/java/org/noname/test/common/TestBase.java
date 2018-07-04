package org.noname.test.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.noname.core.appium.CapabilitiesProvider;
import org.openqa.selenium.Capabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.net.URL;

import static org.noname.core.appium.CapabilitiesProvider.CONFIGURATION;

/**
 * @author Timofei B.
 */
public class TestBase {

    public static final String CAPABILITIES_PROVIDER_PROPERTY_KEY = "capabilities.provider";
    public static final String APPIUM_URL_PROPERTY_KEY = "appium.url";

    private AppiumDriver driver;

    @BeforeTest
    public void setUpAppium() throws IOException {
        // android app only
        Capabilities capabilities = CapabilitiesProvider.getInstance(
                System.getProperty(CAPABILITIES_PROVIDER_PROPERTY_KEY, CONFIGURATION)).get();
        String appiumServerURL = System.getProperty(APPIUM_URL_PROPERTY_KEY);
        driver = appiumServerURL == null ? new AndroidDriver(capabilities)
                : new AndroidDriver(new URL(appiumServerURL), capabilities);
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
