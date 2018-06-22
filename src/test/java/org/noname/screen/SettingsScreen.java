package org.noname.screen;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.noname.screen.common.BaseScreen;
import org.noname.screen.common.PageFactoryObject;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Timofei B.
 */
public class SettingsScreen extends BaseScreen {

    // Main
    private static final String SORT_LIST = "Sort list";
    private static final String CURRENCY = "Currency";
    private static final String OWN_CURRENCY = "Own currency";
    private static final String FONT_SIZE = "Font size";
    private static final String ORIENTATION = "Orientation";

    // Display
    private static final String UNITS = "Units";
    private static final String COMMENT = "Comment";
    private static final String PRICE = "Price";

    // Other
    private static final String DO_NOT_TURN_OFF_THE_SCREEN = "Do not turn off the screen";
    private static final String MOVE_BOUGHT_TO_THE_END_OF_THE_LIST = "Move bought to the end of the list";
    private static final String MY_LIST = "My List";
    private static final String CATEGORIES_LIST = "Categories List";
    private static final String SKINS = "Skins";

    private ListView listView = new ListView();

    /**
     * @param driver
     */
    public SettingsScreen(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    /**
     * @param sortList
     */
    @Step("set sort order to '{0}'")
    public void setSortList(SortingOrder sortList) {
        setItem(SORT_LIST, sortList.toString());
    }

    /**
     * @return
     */
    public String getSortListSummary() {
        return getItemSummary(SORT_LIST);
    }

    /**
     * @param currency
     */
    @Step("set currency to '{0}'")
    public void setCurrency(Currency currency) {
        setItem(CURRENCY, currency.toString());
    }

    /**
     * @return
     */
    public String getCurrencySummary() {
        return getItemSummary(CURRENCY);
    }

    /**
     * @param name
     */
    @Step("create own currency with name '{0}'")
    public void createOwnCurrency(String name) {
        listView.scrollIntoViewItem(OWN_CURRENCY).click();
        AlertDialog2 alertDialog2 = new AlertDialog2();
        sendKeys(alertDialog2.mOwnCurrencyName, name);
        alertDialog2.mYes.click();
    }

    /**
     * @param fontSize
     */
    @Step("set font size '{0}'")
    public void setFontSize(FontSize fontSize) {
        setItem(FONT_SIZE, fontSize.toString());
    }

    /**
     * @param orientation
     */
    @Step("set orientation '{0}'")
    public void setOrientation(Orientation orientation) {
        setItem(ORIENTATION, orientation.toString());
    }

    /**
     * @return
     */
    public boolean isUnits() {
        return listView.isItemChecked(UNITS);
    }

    /**
     * @param mark
     */
    @Step("check units '{0}'")
    public void setUnits(boolean mark) {
        setItem(UNITS, mark);
    }

    /**
     * @return
     */
    public boolean isComments() {
        return listView.isItemChecked(COMMENT);
    }

    /**
     * @param mark
     */
    @Step("check comments '{0}'")
    public void setComments(boolean mark) {
        setItem(COMMENT, mark);
    }

    /**
     * @return
     */
    public boolean isPrice() {
        return listView.isItemChecked(PRICE);
    }

    /**
     * @param mark
     */
    @Step("check price '{0}'")
    public void setPrice(boolean mark) {
        setItem(PRICE, mark);
    }

    /**
     * @return
     */
    public boolean isDoNotTurnOffTheScreen() {
        return listView.isItemChecked(DO_NOT_TURN_OFF_THE_SCREEN);
    }

    /**
     * @param mark
     */
    @Step("check do not turn off the screen '{0}'")
    public void setDoNotTurnOffTheScreen(boolean mark) {
        setItem(DO_NOT_TURN_OFF_THE_SCREEN, mark);
    }

    /**
     * @return
     */
    public boolean isMoveBoughtToTheEndOfTheList() {
        return listView.isItemChecked(MOVE_BOUGHT_TO_THE_END_OF_THE_LIST);
    }

    /**
     * @param mark
     */
    @Step("check move bought to the end of the list '{0}'")
    public void setMoveBoughtToTheEndOfTheList(boolean mark) {
        setItem(MOVE_BOUGHT_TO_THE_END_OF_THE_LIST, mark);
    }

    /**
     * @return
     */
    @Step("open 'my list' screen")
    public MyListAddScreen openMyListScreen() {
        listView.scrollIntoViewItem(MY_LIST).click();
        return new MyListAddScreen(driver);
    }

    /**
     * @return
     */
    public String getMyListSummary() {
        return getItemSummary(MY_LIST);
    }

    /**
     * @return
     */
    @Step("open 'categories' screen")
    public CategoriesScreen openCategoriesScreen() {
        listView.scrollIntoViewItem(CATEGORIES_LIST).click();
        return new CategoriesScreen(driver);
    }

    /**
     * @return
     */
    public String getCategoriesSummary() {
        return getItemSummary(CATEGORIES_LIST);
    }

    /**
     * @return
     */
    @Step("open 'skins' screen")
    public SkinsScreen openSkinsScreen() {
        listView.scrollIntoViewItem(SKINS).click();
        return new SkinsScreen(driver);
    }

    /**
     * @return
     */
    public String getSkinsSummary() {
        return getItemSummary(SKINS);
    }

    @Override
    protected String getCurrentActivity() {
        return ".SettingsScreen";
    }

    /**
     * @param name
     * @return
     */
    private String getItemSummary(String name) {
        listView.scrollIntoViewItem(name);
        return listView.getItemSummary(name);
    }

    /**
     * @param name
     * @param value
     */
    private void setItem(String name, String value) {
        listView.scrollIntoViewItem(name).click();

        AlertDialog1 alertDialog1 = new AlertDialog1();
        alertDialog1.scrollIntoViewItem(value);

        if (!alertDialog1.isItemChecked(value))
            alertDialog1.getItemByName(value).click();
    }

    /**
     * @param name
     * @param mark
     */
    private void setItem(String name, boolean mark) {
        listView.scrollIntoViewItem(name);
        boolean isItemChecked = listView.isItemChecked(name);
        if ((mark && !isItemChecked)
                || (!mark && isItemChecked)) {
            listView.getItemByName(name).click();
        }
    }

    public enum SortingOrder {}

    public enum Currency {
        GBP("£"),
        USD("$"),
        OTHER("Other");

        private final String currency;

        Currency(String currency) {
            this.currency = currency;
        }

        @Override
        public String toString() {
            return currency;
        }
    }

    public enum FontSize {

    }

    public enum Orientation {
        HORIZONTAL("Horizontal"),
        VERTICAL("Vertical"),
        AUTO("Auto");

        private final String orientation;

        Orientation(String orientation) {
            this.orientation = orientation;
        }

        @Override
        public String toString() {
            return orientation;
        }

    }

    /**
     * alert for settings screen
     */
    private class AlertDialog1 extends PageFactoryObject {

        @AndroidFindBy(id = "android:id/text1")
        List<MobileElement> mItems;

        @AndroidFindBy(id = "android:id/button2")
        MobileElement mNo;

        AlertDialog1() {
            super(driver);
        }

        /**
         * @param name
         * @return
         */
        boolean isItemChecked(String name) {
            return Boolean.parseBoolean(getItemByName(name).getAttribute("checked"));
        }

        /**
         * @param index
         * @return
         */
        boolean isItemChecked(int index) {
            return Boolean.parseBoolean(mItems.get(index).getAttribute("checked"));
        }

        /**
         * @param name
         * @return
         */
        MobileElement scrollIntoViewItem(String name) {
            return driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"android:id/select_dialog_listview\")).scrollIntoView(new UiSelector().resourceId(\"android:id/text1\").text(\"" + name + "\"))"));
        }

        /**
         * @param name
         * @return
         */
        MobileElement getItemByName(String name) {
            return mItems.stream()
                    .filter(e -> e.getText().equals(name))
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
     * class for alert own currency
     */
    private class AlertDialog2 extends PageFactoryObject {

        @AndroidFindBy(id = "android:id/button1")
        MobileElement mYes;

        @AndroidFindBy(id = "android:id/button2")
        MobileElement mNo;

        @AndroidFindBy(id = "android:id/edit")
        MobileElement mOwnCurrencyName;

        AlertDialog2() {
            super(driver);
        }

    }

    /**
     * listView of settings screen
     */
    private class ListView extends PageFactoryObject {

        @AndroidFindBy(xpath = "//android.widget.ListView//android.widget.LinearLayout")
        List<MobileElement> mItems;

        ListView() {
            super(driver);
        }

        /**
         * @param name
         * @return
         */
        MobileElement scrollIntoViewItem(String name) {
            return driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"android:id/list\")).scrollIntoView(new UiSelector().resourceId(\"android:id/title\").text(\"" + name + "\"))"));
        }

        /**
         * @param name
         * @return
         */
        boolean isItemChecked(String name) {
            return Boolean.parseBoolean(getItemByName(name).findElement(MobileBy.id("android:id/checkbox")).getAttribute("checked"));
        }

        /**
         * @param index
         * @return
         */
        boolean isItemChecked(int index) {
            return Boolean.parseBoolean(mItems.get(index).findElement(MobileBy.id("android:id/checkbox")).getAttribute("checked"));
        }

        /**
         * @param name
         * @return
         */
        String getItemSummary(String name) {
            return getItemByName(name).findElement(MobileBy.id("android:id/summary")).getText();
        }

        /**
         * @param index
         * @return
         */
        String getItemSummary(int index) {
            return mItems.get(index).findElement(MobileBy.id("android:id/summary")).getText();
        }

        /**
         * @param name
         * @return
         */
        MobileElement getItemByName(String name) {
            return mItems.stream()
                    .filter(e -> e.findElement(
                            MobileBy.id("android:id/title")).getText()
                            .equals(name))
                    .collect(toList()).get(0);
        }
    }

}
