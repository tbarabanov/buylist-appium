package org.noname.core.allure;

import io.qameta.allure.Attachment;
import org.noname.test.common.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * @author Timofei B.
 */
public class TakingScreenshotOnTestFailureListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult tr) {
        if (BaseTest.class.isAssignableFrom(tr.getInstance().getClass())) {
            takeScreenshot(BaseTest.class.cast(tr.getInstance()).getDriver());
        }
    }

    @Attachment(value = "Page Screen", type = "image/png")
    public byte[] takeScreenshot(RemoteWebDriver driver) {
        return driver.getScreenshotAs(OutputType.BYTES);
    }
}
