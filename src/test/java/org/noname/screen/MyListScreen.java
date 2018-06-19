package org.noname.screen;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.noname.screen.common.BaseScreen;
import org.noname.screen.common.PageFactoryObject;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Timofei B.
 */
public class MyListScreen extends BaseScreen {

    private static final String SELECT_ALL = "Select all";
    private static final String DESELECT_ALL = "Deselect all";

    @AndroidFindBy(id = "com.slava.buylist:id/button2")
    private MobileElement mOk;

    @AndroidFindBy(id = "com.slava.buylist:id/button3")
    private MobileElement mCancel;

    @AndroidFindBy(id = "com.slava.buylist:id/button1")
    private MobileElement mMenu;

    private ListView listView = new ListView();

    /**
     * @param driver
     */
    public MyListScreen(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    @Override
    protected String getCurrentActivity() {
        return ".MyListActivity";
    }

    /**
     * select all
     */
    @Step("click select all button")
    public AddScreen addAll() {
        listView.scrollIntoViewItem(SELECT_ALL).click();
        mOk.click();
        return new AddScreen(driver);
    }

    /**
     * @param name
     */
    @Step("click on product with name '{0}'")
    public AddScreen addProduct(String name) {
        listView.scrollIntoViewItem(name).click();
        mOk.click();
        return new AddScreen(driver);
    }

    /**
     * @param index
     */
    @Step("click on product with index '{0}'")
    public AddScreen addProduct(int index) {
        listView.getItemByIndex(index).click();
        mOk.click();
        return new AddScreen(driver);
    }

    /**
     * @param names
     */
    public void addProducts(String... names) {
    }

    /**
     * @param indices
     */
    public void addProducts(int... indices) {
    }

    /**
     * @param name
     * @return
     */
    public boolean hasProduct(String name) {
        boolean result = true;
        try {
            listView.scrollIntoViewItem(name);
        } catch (NoSuchElementException ignored) {
            result = false;
        }
        return result;
    }

    private class ListView extends PageFactoryObject {

        @AndroidFindBy(id = "com.slava.buylist:id/rr1")
        List<MobileElement> mItems;

        ListView() {
            super(driver);
        }

        /**
         * @param name
         * @return
         */
        MobileElement scrollIntoViewItem(String name) {
            return driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"com.slava.buylist:id/listView1\")).scrollIntoView(new UiSelector().resourceId(\"com.slava.buylist:id/title\").text(\"" + name + "\"))"));
        }

        /**
         * @param name
         * @return
         */
        boolean isItemChecked(String name) {
            return false;
        }

        /**
         * @param index
         * @return
         */
        boolean isItemChecked(int index) {
            return false;
        }

        /**
         * @param name
         * @return
         */
        MobileElement getItemCheckBox(String name) {
            return getItemByName(name).findElement(MobileBy.id("com.slava.buylist:id/imageView1"));
        }

        /**
         * @param index
         * @return
         */
        MobileElement getItemCheckBox(int index) {
            return getItemByIndex(index).findElement(MobileBy.id("com.slava.buylist:id/imageView1"));
        }

        /**
         * @param name
         * @return
         */
        String getItemSummary(String name) {
            return getItemByName(name).findElement(MobileBy.id("com.slava.buylist:id/str1")).getText();
        }

        /**
         * @param index
         * @return
         */
        String getItemSummary(int index) {
            return mItems.get(index).findElement(MobileBy.id("com.slava.buylist:id/str1")).getText();
        }

        /**
         * @param name
         * @return
         */
        MobileElement getItemByName(String name) {
            return mItems.stream()
                    .filter(e -> e.findElement(
                            MobileBy.id("com.slava.buylist:id/title")).getText()
                            .equals(name))
                    .collect(toList()).get(0);
        }

        /**
         * @param index
         * @return
         */
        MobileElement getItemByIndex(int index) {
            return mItems.get(index);
        }
    }

}
