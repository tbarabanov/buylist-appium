package org.noname.screen;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.noname.screen.common.PageFactoryObject;

/**
 * @author Timofei B.
 */
public class AddScreen extends MyListAddScreen {

    @AndroidFindBy(id = "com.slava.buylist:id/button1")
    private MobileElement mMenu;

    private ListView listView = new ListView();

    /**
     * @param driver
     */
    public AddScreen(AppiumDriver driver) {
        super(driver);
    }

    /**
     * @return
     */
    @Step("open 'my list' from menu options")
    public MyListScreen openMyListScreen() {
        mMenu.click();
        new Menu1().mAddFromMyList.click();
        return new MyListScreen(driver);
    }

    @Override
    public String getCurrentActivity() {
        return ".AddActivity";
    }

    /**
     * menu for addScreen
     */
    private class Menu1 extends PageFactoryObject {

        @AndroidFindBy(xpath = "//android.widget.TextView[@text='Settings']")
        MobileElement mSettings;

        @AndroidFindBy(xpath = "//android.widget.TextView[@text='Remind']")
        MobileElement mRemind;

        @AndroidFindBy(xpath = "//android.widget.TextView[@text='Add from my list']")
        MobileElement mAddFromMyList;

        Menu1() {
            super(driver);
        }

    }

}
