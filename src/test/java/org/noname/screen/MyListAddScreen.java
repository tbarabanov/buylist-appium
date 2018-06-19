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
public class MyListAddScreen extends BaseScreen {

    @AndroidFindBy(id = "com.slava.buylist:id/editText1")
    private MobileElement mProductName;

    @AndroidFindBy(id = "com.slava.buylist:id/button2")
    private MobileElement mSearchPlusButton;

    @AndroidFindBy(id = "com.slava.buylist:id/editText2")
    private MobileElement mPrice;

    @AndroidFindBy(id = "com.slava.buylist:id/value")
    private MobileElement mCurrency;

    @AndroidFindBy(id = "com.slava.buylist:id/editText3")
    private MobileElement mNumber;

    @AndroidFindBy(id = "com.slava.buylist:id/spinner1")
    private MobileElement mSpinner1;

    @AndroidFindBy(id = "com.slava.buylist:id/editText4")
    private MobileElement mComments;

    @AndroidFindBy(id = "com.slava.buylist:id/spinner2")
    private MobileElement mSpinner2;

    @AndroidFindBy(id = "com.slava.buylist:id/textView2")
    private MobileElement mTotal;

    @AndroidFindBy(id = "com.slava.buylist:id/button1")
    private MobileElement mMenu;

    private ListView listView = new ListView();


    /**
     * @param driver
     */
    public MyListAddScreen(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    /**
     * @param name
     * @param price
     * @param number
     * @param units
     * @param comments
     * @param category
     */
    @Step("create product with name '{0}'\nprice '{1}'\n number '{2}'\n units '{3}'\n comments '{4}'\ncategory '{5}'")
    public void createProduct(String name,
                              int price,
                              int number,
                              Unit units,
                              String comments,
                              Category category) {
        sendKeys(mProductName, name);
        sendKeys(mPrice, String.valueOf(price));
        sendKeys(mNumber, String.valueOf(number));
        // >>>
        mSpinner1.click();
        new Spinner1().scrollIntoViewItem(units.toString()).click();
        sendKeys(mComments, comments);
        // >>>
        mSpinner2.click();
        new Spinner1().scrollIntoViewItem(category.toString()).click();
        // other
        mSearchPlusButton.click();
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

    /**
     * @param name
     */
    public void crossOutProduct(String name) {
    }

    /**
     * @param index
     */
    public void crossOutProduct(int index) {
    }

    /**
     * @param name
     * @return
     */
    public boolean isProductCrossedOut(String name) {
        return false;
    }

    /**
     * @param index
     * @return
     */
    public boolean isProductCrossedOut(int index) {
        return false;
    }

    /**
     * @param name
     * @return
     */
    public String getProductComments(String name) {
        listView.scrollIntoViewItem(name);
        return listView.getItemComments(name);
    }

    /**
     * @param index
     * @return
     */
    public String getProductComments(int index) {
        return listView.getItemComments(index);
    }

    /**
     * @param name
     * @return
     */
    public String getProductNumberSummary(String name) {
        listView.scrollIntoViewItem(name);
        return listView.getItemNumberSummary(name);
    }

    /**
     * @param index
     * @return
     */
    public String getProductNumberSummary(int index) {
        return listView.getItemNumberSummary(index);
    }

    /**
     * @param name
     * @return
     */
    public String getProductPriceSummary(String name) {
        listView.scrollIntoViewItem(name);
        return listView.getItemPriceSummary(name);
    }

    /**
     * @return
     */
    public String getTotal() {
        return mTotal.getText();
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

    @Override
    public String getCurrentActivity() {
        return ".ListAddActivity";
    }

    /**
     * units
     */
    public enum Unit {
        PCS("pcs."),
        TEN("ten"),
        BOTTLES("bottles");

        private final String unit;

        Unit(String unit) {
            this.unit = unit;
        }

        @Override
        public String toString() {
            return unit;
        }
    }

    public enum Category {
        CHILD_PRODUCTS("Child products"),
        ALCOHOL_TOBACCO("Alcohol, tobacco");

        private final String category;

        Category(String category) {
            this.category = category;
        }

        @Override
        public String toString() {
            return category;
        }
    }

    /**
     * class for products list
     */
    protected class ListView extends PageFactoryObject {

        @AndroidFindBy(id = "com.slava.buylist:id/item")
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
         * @param element
         * @return
         */
        MobileElement getItemCartIcon(MobileElement element) {
            return element.findElement(MobileBy.id("com.slava.buylist:id/imageView1"));
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
        String getItemComments(String name) {
            return getItemByName(name).findElement(MobileBy.id("com.slava.buylist:id/str1")).getText();
        }

        /**
         * @param index
         * @return
         */
        String getItemComments(int index) {
            return mItems.get(index).findElement(MobileBy.id("com.slava.buylist:id/str1")).getText();
        }

        /**
         * @param name
         * @return
         */
        String getItemNumberSummary(String name) {
            return getItemByName(name).findElement(MobileBy.id("com.slava.buylist:id/TextView01")).getText();
        }

        /**
         * @param index
         * @return
         */
        String getItemNumberSummary(int index) {
            return mItems.get(index).findElement(MobileBy.id("com.slava.buylist:id/TextView01")).getText();
        }

        /**
         * @param name
         * @return
         */
        String getItemPriceSummary(String name) {
            return getItemByName(name).findElement(MobileBy.id("com.slava.buylist:id/textView1")).getText();
        }

        /**
         * @param index
         * @return
         */
        String getItemPriceSummary(int index) {
            return mItems.get(index).findElement(MobileBy.id("com.slava.buylist:id/textView1")).getText();
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
    }

    /**
     * spinner for number and category
     */
    protected class Spinner1 extends PageFactoryObject {

        @AndroidFindBy(id = "android:id/text1")
        List<MobileElement> mItems;

        Spinner1() {
            super(driver);
        }

        /**
         * @param name
         * @return
         */
        MobileElement scrollIntoViewItem(String name) {
            return driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"android:id/select_dialog_listview\")).scrollIntoView(new UiSelector().resourceId(\"android:id/text1\").text(\"" + name + "\"))"));
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

        @AndroidFindBy(xpath = "//android.widget.TextView[@text='Remind']")
        MobileElement mRemind;

        Menu1() {
            super(driver);
        }
    }
}
