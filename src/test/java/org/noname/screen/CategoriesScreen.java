package org.noname.screen;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.noname.screen.common.BaseScreen;
import org.noname.screen.common.PageFactoryObject;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class CategoriesScreen extends BaseScreen {

    @AndroidFindBy(id = "com.slava.buylist:id/button2")
    private MobileElement mAddNewButton;

    private LitView listView = new LitView();

    /**
     * @param driver
     */
    public CategoriesScreen(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    /**
     * @param name
     */
    @Step("create new category with name '{0}'")
    public void createCategory(String name) {
        mAddNewButton.click();
        AlertDialog alertDialog = new AlertDialog();
        sendKeys(alertDialog.mEditName, name);
        alertDialog.mYes.click();
    }

    /**
     * @param src
     * @param dst
     */
    @Step("move category with index '{0}' on category with index '{1}'")
    public void moveCategory(int src, int dst) {
        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(listView.getItemDragAndDropIcon(src))
                .moveTo(listView.getItemDragAndDropIcon(dst)).release().perform();
    }

    /**
     * @param src
     * @param dst
     */
    @Step("move category with name '{0}' on category with name '{1}'")
    public void moveCategory(String src, String dst) {
        listView.scrollIntoViewItem(src);
        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(listView.getItemDragAndDropIcon(src))
                .moveTo(listView.getItemDragAndDropIcon(dst)).release().perform();
    }

    /**
     * @param name
     * @return
     */
    public boolean hasCategory(String name) {
        boolean result = false;
        try {
            listView.scrollIntoViewItem(name);
            result = true;
        } catch (NoSuchElementException ignored) {
        }
        return result;
    }

    /**
     * @return
     */
    public List<String> getCategoryNames() {
        return listView.itemNames();
    }

    @Override
    protected String getCurrentActivity() {
        return ".CatListActivity";
    }

    /**
     * class for listview in category screen
     */
    private class LitView extends PageFactoryObject {

        @AndroidFindBy(id = "com.slava.buylist:id/rr1")
        List<MobileElement> mItems;

        LitView() {
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
         * @param element
         * @return
         */
        MobileElement getItemDragAndDropIcon(MobileElement element) {
            return element.findElement(MobileBy.id("com.slava.buylist:id/dicon"));
        }

        /**
         * @param name
         * @return
         */
        MobileElement getItemDragAndDropIcon(String name) {
            return getItemDragAndDropIcon(getItemByName(name));
        }

        /**
         * @param index
         * @return
         */
        MobileElement getItemDragAndDropIcon(int index) {
            return getItemDragAndDropIcon(getItemByIndex(index));
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

    /**
     * class for alert in categories screen
     */
    private class AlertDialog extends PageFactoryObject {

        @AndroidFindBy(id = "android:id/button1")
        MobileElement mYes;

        @AndroidFindBy(id = "android:id/button2")
        MobileElement mNo;

        @AndroidFindBy(className = "android.widget.EditText")
        MobileElement mEditName;

        AlertDialog() {
            super(driver);
        }
    }
}
