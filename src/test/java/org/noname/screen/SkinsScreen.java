package org.noname.screen;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.noname.screen.common.BaseScreen;

public class SkinsScreen extends BaseScreen {

    /**
     * @param driver
     */
    public SkinsScreen(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    @Override
    protected String getCurrentActivity() {
        return ".SkinsActivity";
    }

    public void setSkin(int index) {
    }

}
