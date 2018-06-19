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
public class MainScreen extends BaseScreen {

    @AndroidFindBy(id = "com.slava.buylist:id/editText1")
    private MobileElement mListName;

    @AndroidFindBy(id = "com.slava.buylist:id/button2")
    private MobileElement mPlusButton;

    @AndroidFindBy(id = "com.slava.buylist:id/button1")
    private MobileElement mMenu;

    private ListView listView = new ListView();

    /**
     * @param driver
     */
    public MainScreen(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    /**
     * @param name
     */
    @Step("create shopping list with name '{0}'")
    public AddScreen createShoppingList(String name) {
        sendKeys(mListName, name);
        mPlusButton.click();
        return new AddScreen(driver);
    }

    /**
     * @param oldName
     * @param newName
     */
    @Step("change name of the shopping list with name '{0}' on new one '{1}'")
    public void editShoppingListName(String oldName, String newName) {
        listView.scrollIntoViewItem(oldName);
        listView.getItemEditIcon(listView.getItemByName(oldName)).click();

        AlertDialog1 alertDialog2 = new AlertDialog1();
        sendKeys(alertDialog2.mEditName, newName);
        alertDialog2.mYes.click();
    }

    /**
     * @param index
     * @param newName
     */
    @Step("change name of the shopping list with index '{0}' on new name '{1}'")
    public void editShoppingListName(int index, String newName) {
        listView.getItemEditIcon(listView.getItemByIndex(index)).click();

        AlertDialog1 alertDialog2 = new AlertDialog1();
        sendKeys(alertDialog2.mEditName, newName);
        alertDialog2.mYes.click();
    }

    /**
     * @param name
     * @return
     */
    @Step("open 'add screen' for shopping list '{0}'")
    public AddScreen editShoppingList(String name) {
        listView.scrollIntoViewItem(name).click();
        return new AddScreen(driver);
    }

    /**
     * @param index
     * @return
     */
    @Step("open 'add screen' for shopping index '{0}'")
    public AddScreen editShoppingList(int index) {
        listView.getItemByIndex(index).click();
        return new AddScreen(driver);
    }

    /**
     * @param name
     */
    @Step("delete shopping list '{0}'")
    public void deleteShoppingList(String name) {
        listView.scrollIntoViewItem(name);
        listView.getItemDeleteIcon(listView.getItemByName(name)).click();
        new AlertDialog2().mYes.click();
    }

    /**
     * @param index
     */
    @Step("delete shopping list index '{0}'")
    public void deleteShoppingList(int index) {
        listView.getItemDeleteIcon(listView.getItemByIndex(index)).click();
        new AlertDialog2().mYes.click();
    }

    /**
     * @param name
     * @return
     */
    public String getShoppingListSummary(String name) {
        listView.scrollIntoViewItem(name);
        return listView.getItemSummary(name);
    }

    /**
     * @param index
     * @return
     */
    public String getShoppingListSummary(int index) {
        return listView.getItemSummary(index);
    }

    /**
     * @param name
     * @return
     */
    public boolean hasShoppingList(String name) {
        boolean result = true;
        try {
            listView.scrollIntoViewItem(name);
        } catch (NoSuchElementException ignored) {
            result = false;
        }
        return result;
    }

    /**
     * @return
     */
    public List<String> getShoppingListNames() {
        return listView.itemNames();
    }

    /**
     * @return
     */
    @Step("open 'settings' screen from menu options")
    public SettingsScreen openSettingsScreen() {
        mMenu.click();
        new Menu1().mSettings.click();
        return new SettingsScreen(driver);
    }

    /**
     * @return
     */
    @Step("open 'my list' screen from menu options")
    public MyListAddScreen openMyListScreen() {
        mMenu.click();
        new Menu1().mMyList.click();
        return new MyListAddScreen(driver);
    }

    @Override
    public String getCurrentActivity() {
        return ".MainActivity";
    }

    /**
     * class for change name alert
     */
    private class AlertDialog1 extends PageFactoryObject {

        @AndroidFindBy(id = "android:id/button1")
        MobileElement mYes;

        @AndroidFindBy(id = "android:id/button2")
        MobileElement mNo;

        @AndroidFindBy(className = "android.widget.EditText")
        MobileElement mEditName;

        AlertDialog1() {
            super(driver);
        }
    }

    /**
     * class for delete alert
     */
    private class AlertDialog2 extends PageFactoryObject {

        @AndroidFindBy(id = "android:id/button1")
        MobileElement mYes;

        @AndroidFindBy(id = "android:id/button2")
        MobileElement mNo;

        AlertDialog2() {
            super(driver);
        }
    }

    /**
     * class for shopping list
     */
    private class ListView extends PageFactoryObject {

        @AndroidFindBy(id = "com.slava.buylist:id/rr1")
        List<MobileElement> mItems;

        ListView() {
            super(driver);
        }

        /**
         * @return
         */
        List<String> itemNames() {
            return mItems.stream().map(e -> e.findElement(MobileBy.id("com.slava.buylist:id/title")).getText()).collect(toList());
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
         * @param element
         * @return
         */
        MobileElement getItemEditIcon(MobileElement element) {
            return element.findElement(MobileBy.id("com.slava.buylist:id/imageView2"));
        }

        /**
         * @param element
         * @return
         */
        MobileElement getItemDeleteIcon(MobileElement element) {
            return element.findElement(MobileBy.id("com.slava.buylist:id/imageView1"));
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

    private class Menu1 extends PageFactoryObject {

        @AndroidFindBy(xpath = "//android.widget.TextView[@text='Settings']")
        MobileElement mSettings;

        @AndroidFindBy(xpath = "//android.widget.TextView[@text='My List']")
        MobileElement mMyList;

        Menu1() {
            super(driver);
        }
    }

}
