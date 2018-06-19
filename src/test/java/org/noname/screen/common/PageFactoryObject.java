package org.noname.screen.common;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author Timofei B.
 */
public class PageFactoryObject {

    /**
     * @param driver
     */
    public PageFactoryObject(RemoteWebDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver, 15, TimeUnit.SECONDS), this);
    }

}
