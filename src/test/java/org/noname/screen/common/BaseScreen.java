package org.noname.screen.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumFluentWait;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.concurrent.TimeUnit;

/**
 * @author Timofei B.
 */
public abstract class BaseScreen extends PageFactoryObject {

    // common driver instance
    protected final AppiumDriver<? extends MobileElement> driver;

    // common wait instance
    protected final FluentWait<AppiumDriver<? extends MobileElement>> wait;

    /**
     * @param driver
     */
    public BaseScreen(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
        this.driver = driver;
        wait = newWait(driver);
        wait.until(a -> ((AndroidDriver) a).currentActivity()
                .equals(getCurrentActivity()));
    }

    /**
     * @param driver
     * @return
     */
    private static FluentWait<AppiumDriver<? extends MobileElement>> newWait(AppiumDriver<? extends MobileElement> driver) {
        return new AppiumFluentWait<AppiumDriver<? extends MobileElement>>(driver)
                .ignoring(NoSuchElementException.class).withTimeout(15, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS);
    }

    /**
     * @return
     */
    protected abstract String getCurrentActivity();

    /**
     * @param mobileElement
     * @param keys
     */
    protected void sendKeys(MobileElement mobileElement, String keys) {
        mobileElement.click();
        mobileElement.clear();
        mobileElement.sendKeys(keys);
        hideKeyboard();
    }

    /**
     * hide keyboard if needed
     */
    protected void hideKeyboard() {
        if (((AndroidDriver) driver).isKeyboardShown())
            driver.hideKeyboard();
    }

    /**
     * navigate back
     */
    public void navigateBack() {
        hideKeyboard();
        driver.navigate().back();
    }

}
