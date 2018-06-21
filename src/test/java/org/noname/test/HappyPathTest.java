package org.noname.test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.noname.core.allure.TakingScreenshotOnTestFailureListener;
import org.noname.screen.*;
import org.noname.screen.SettingsScreen.Currency;
import org.noname.test.common.TestBase;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

import static org.noname.screen.MyListAddScreen.Category.ALCOHOL_TOBACCO;
import static org.noname.screen.MyListAddScreen.Category.CHILD_PRODUCTS;
import static org.noname.screen.MyListAddScreen.Unit.BOTTLES;

@Epic("GridU Mobile Test Automation")
@Feature("HappyPath Main Features")
@Listeners({TakingScreenshotOnTestFailureListener.class})
public class HappyPathTest extends TestBase {

    @Description("create a new empty shopping list")
    @Story("B-12345")
    @Test
    public void create_new_shopping_list() {

        final String name = "Shopping List A";

        MainScreen mainScreen = new MainScreen(getDriver());
        mainScreen
                .createShoppingList(name)
                .navigateBack();

        Assert.assertTrue(mainScreen.hasShoppingList(name));
    }

    @Description("create a product in a shopping list")
    @Story("B-12345")
    @Test
    public void add_product_in_shopping_list() {
        final String listName = "Shopping List A";
        final String productName = "Milk";

        MainScreen mainScreen = new MainScreen(getDriver());
        AddScreen addScreen = mainScreen.createShoppingList(listName);

        addScreen.createProduct(productName,
                10,
                2,
                BOTTLES,
                "",
                CHILD_PRODUCTS);

        Assert.assertTrue(addScreen.hasProduct(productName));
    }

    @Description("change currency on existing")
    @Story("B-707707")
    @Test(priority = 1)
    public void change_currency() {

        final String listName = "Long List Name 0";
        MainScreen mainScreen = new MainScreen(getDriver());

        AddScreen addScreen = mainScreen.createShoppingList(listName);
        addScreen.createProduct("Product",
                2,
                2,
                AddScreen.Unit.PCS,
                "comments",
                ALCOHOL_TOBACCO);

        addScreen.navigateBack();

        mainScreen = new MainScreen(getDriver());

        SettingsScreen settingsScreen = mainScreen.openSettingsScreen();
        settingsScreen.setCurrency(Currency.USD);

        // navigate back
        settingsScreen.navigateBack();

        mainScreen = new MainScreen(getDriver());
        Assert.assertTrue(mainScreen.getShoppingListSummary(listName)
                .contains(Currency.USD.toString()));
    }

    @Description("change name of a shopping list")
    @Story("B-12340")
    @Test
    public void change_shopping_list_name() {

        final String oldName = "Old name";
        final String newName = "New name";

        MainScreen mainScreen = new MainScreen(getDriver());

        AddScreen addScreen = mainScreen.createShoppingList(oldName);
        addScreen.navigateBack();

        mainScreen = new MainScreen(getDriver());
        mainScreen.editShoppingListName(oldName, newName);

        Assert.assertTrue(mainScreen.hasShoppingList(newName));
    }

    @Description("swap categories in categories screen")
    @Test
    public void swap_categories() {

        MainScreen mainScreen = new MainScreen(getDriver());
        SettingsScreen settingsScreen = mainScreen.openSettingsScreen();

        CategoriesScreen categoriesScreen = settingsScreen.openCategoriesScreen();

        List<String> names = categoriesScreen.getCategoryNames();

        final String current = names.get(0);
        final String next = names.get(1);

        // swap
        categoriesScreen.moveCategory(0, 1);

        // new categories order
        names = categoriesScreen.getCategoryNames();

        // check that swapped
        Assert.assertTrue(names.get(1).equals(current));
        Assert.assertTrue(names.get(0).equals(next));
    }

    @Description("create new products category")
    @Story("B-707707")
    @Test(priority = 1)
    public void create_new_category() {

        MainScreen mainScreen = new MainScreen(getDriver());

        SettingsScreen settingsScreen = mainScreen.openSettingsScreen();
        CategoriesScreen categoriesScreen = settingsScreen.openCategoriesScreen();

        final String categoryName = "Perviy";
        categoriesScreen.createCategory(categoryName);

        Assert.assertTrue(categoriesScreen.hasCategory(categoryName));
    }

    @Description("create a product in my list")
    @Test
    public void my_list_screen() {

        MainScreen mainScreen = new MainScreen(getDriver());

        SettingsScreen settingsScreen = mainScreen.openSettingsScreen();

        MyListAddScreen myListAddScreen = settingsScreen.openMyListScreen();

        final String productName = "Product";

        myListAddScreen.createProduct(productName, 100, 5, MyListAddScreen.Unit.BOTTLES,
                "happy path", CHILD_PRODUCTS);

        myListAddScreen.navigateBack();

        settingsScreen = new SettingsScreen(getDriver());
        myListAddScreen = settingsScreen.openMyListScreen();

        Assert.assertTrue(myListAddScreen.hasProduct(productName));
    }

    @Description("delete shopping list from mains screen")
    @Story("B-12340")
    @Test
    public void delete_shopping_list() {

        final String listName = "List Name";
        MainScreen mainScreen = new MainScreen(getDriver());

        mainScreen.createShoppingList(listName).navigateBack();

        mainScreen = new MainScreen(getDriver());
        mainScreen.deleteShoppingList(listName);

        Assert.assertFalse(mainScreen.hasShoppingList(listName));
    }

    @Description("add all items from my list to a new one")
    @Story("B-12349")
    @Test
    public void add_all_from_my_list() {

        MainScreen mainScreen = new MainScreen(getDriver());
        MyListAddScreen myListAddScreen = mainScreen.openMyListScreen();

        final String productName = "Product";

        myListAddScreen.createProduct(productName, 100, 5, MyListAddScreen.Unit.BOTTLES,
                "happy path", CHILD_PRODUCTS);

        myListAddScreen.navigateBack();

        final String listName = "List Name";
        mainScreen = new MainScreen(getDriver());
        AddScreen addScreen = mainScreen.createShoppingList(listName);

        MyListScreen myListScreen = addScreen.openMyListScreen();
        addScreen = myListScreen.addAll();

        Assert.assertTrue(addScreen.hasProduct(productName));
    }

    @Description("create own currency and check how it is displayed on the main screen")
    @Story("B-707707")
    @Test(priority = 2)
    public void create_own_currency() {

        final String currency = "RUR";
        MainScreen mainScreen = new MainScreen(getDriver());

        SettingsScreen settingsScreen = mainScreen.openSettingsScreen();
        settingsScreen.setCurrency(Currency.OTHER);
        settingsScreen.createOwnCurrency(currency);

        settingsScreen.navigateBack();

        final String listName = "List Name";

        mainScreen = new MainScreen(getDriver());
        AddScreen addScreen = mainScreen.createShoppingList(listName);

        final String productName = "Product";

        addScreen.createProduct(productName, 100, 5, MyListAddScreen.Unit.BOTTLES,
                "happy path", CHILD_PRODUCTS);

        addScreen.navigateBack();

        mainScreen = new MainScreen(getDriver());

        // takeScreenshot();
        Assert.assertTrue(mainScreen.getShoppingListSummary(0).contains(currency));
    }

}
