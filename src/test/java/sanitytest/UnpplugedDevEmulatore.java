package sanitytest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.boon.core.Sys;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;


// This Class is the main sanity tests of the DEV version.


@Listeners(listeners.class)
public class UnpplugedDevEmulatore {


    private String reportDirectory = "reports";
    private String reportFormat = "xml";
    private String testName = "test01";
    protected AndroidDriver<AndroidElement> driver = null;
    private String username;
    private String password;

    DesiredCapabilities dc = new DesiredCapabilities();


    /*
        ##################################################################
        This method receives Platform parameter (mobile:) and then calls
        another method to initiate its driver
        ##################################################################

     */
    @BeforeClass
    public void setUp() throws MalformedURLException {
        dc.setCapability("reportDirectory", reportDirectory);
        dc.setCapability("reportFormat", reportFormat);
        dc.setCapability("testName", testName);
        dc.setCapability(MobileCapabilityType.UDID, "emulator-5554");
        dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
        dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11");
        dc.setCapability(MobileCapabilityType.APP, "C:\\Automation\\UnpplugedAutomation\\src\\apks\\up_store_v0.5.15.apk");
        dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.unplugged.store");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".splash.SplashActivity");
        dc.setCapability(MobileCapabilityType.DEVICE_NAME, "pixel 5 API 30");
        dc.setCapability(MobileCapabilityType.NO_RESET,true);
 //  dc.setCapability("isHeadless", true);
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), dc);
        username = generateRandomUsername();
        password = "lilach123";


    }

         /*     ##################################################################
            Automation Script no1: Before the Messenger App is installed.
             This test check: Registration with email and password with generateRandomUsername and string password .
             Then sing in. Then at the payment page press ok with no money.
             ##################################################################

*/


    @Test
    public void register_plus_email_and_phone_01() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.id("register_btn")).click();
        driver.findElement(By.id("first_name_input")).sendKeys("lilach");
        driver.findElement(By.id("last_name_input")).sendKeys("test");
        driver.findElement(By.id("username_input")).sendKeys(username);
        driver.findElement(By.id("email_input")).sendKeys("lilach@unplugged-systems.com");
        driver.findElement(By.id("phone_number_input")).sendKeys("972526623377");
        driver.findElement(By.id("password_input")).sendKeys(password);
        driver.findElement(By.id("confirm_password_input")).sendKeys(password);
        driver.findElement(By.id("register_btn")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='OK']")));

        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button1")));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@text='OK']")).click();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        sing_in();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.id("payment_btn")).click();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@text='OK']")).click();

          }

          /* #############################################################################
          Automation Script no2: Before the Messenger App is installed.
          At the store page , click on the Messenger App and press on the download button. Then manually( because
             its take long time ) press on install and then open or done.
             #############################################################################
          */


    @Test
    public void click_on_messenger_app_02() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("apps_rv")));
        //driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        AndroidElement recyclerView = driver.findElement(By.id("apps_rv"));

        MobileElement messengerElement = recyclerView.findElementByAccessibilityId("com.unplugged.messenger");
        if (messengerElement != null) {

            messengerElement.click();
        }

        // driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='DOWNLOAD']")));

        driver.findElement(By.id("install_app_btn")).click();
        driver.resetApp();


    }

     /* #############################################################################
          Automation Script no3: At the store page , click on messenger app
             and press on the close button .
        #############################################################################
          */

    @Test
    public void click_on_messenger_app_03() {

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("apps_rv")));
        //driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        AndroidElement recyclerView = driver.findElement(By.id("apps_rv"));

        MobileElement messengerElement = recyclerView.findElementByAccessibilityId("com.unplugged.messenger");
        if (messengerElement != null) {

            messengerElement.click();
        }

        // driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='Close']")));

        driver.findElement(By.id("close_tv")).click();
    }

      /* #############################################################################
          Automation Script no4: At the store page , click logout

        #############################################################################
          */

    @Test
    public void logout_04() {

      //  driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.findElement(By.className("android.widget.ImageButton")).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.id("action_logout")).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@text='OK']")).click();
    }



    /*
         ##################################################################
          Automation Script no5:Registration without email and password
          with generateRandomUsername and string password .

          ##################################################################

            */
    @Test
    public void register_without_email_and_phone_05() {

        driver.findElement(By.id("register_btn")).click();
        driver.findElement(By.id("first_name_input")).sendKeys("lilach");
        driver.findElement(By.id("last_name_input")).sendKeys("test");
        driver.findElement(By.id("username_input")).sendKeys(username);
        driver.findElement(By.id("password_input")).sendKeys(password);
        driver.findElement(By.id("confirm_password_input")).sendKeys(password);
        driver.findElement(By.id("register_btn")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@text='OK']")));
        driver.findElement(By.xpath("//*[@text='OK']")).click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@text='OK']")).click();

    }

/*
 ##################################################################
    Automation Script no6: After the messenger App is installed this test check :
    Registration with email and password with generateRandomUsername and string password.
    Then press on forgot my password and then manually verify that The email has been sent

  ##################################################################
       */

    @Test
    public void register_and_forgot_my_password_06() {

        driver.findElement(By.id("register_btn")).click();
        driver.findElement(By.id("first_name_input")).sendKeys("lilach");
        driver.findElement(By.id("last_name_input")).sendKeys("test");
        driver.findElement(By.id("username_input")).sendKeys(username);
        driver.findElement(By.id("email_input")).sendKeys("lilach@unplugged-systems.com");
        driver.findElement(By.id("phone_number_input")).sendKeys("972526623377");
        driver.findElement(By.id("password_input")).sendKeys(password);
        driver.findElement(By.id("confirm_password_input")).sendKeys(password);
        driver.findElement(By.id("register_btn")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@text='OK']")));
        driver.findElement(By.xpath("//*[@text='OK']")).click();


        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        forgot_my_Password();
    }


    public void forgot_my_Password() {
        driver.findElement(By.id("sign_in_btn")).click();
        driver.findElement(By.id("username_input")).sendKeys(username);
        driver.findElement(By.id("forgot_password_tv")).click();
        driver.findElement(By.id("username_input")).sendKeys(username);
        driver.findElement(By.id("password_input")).click();

    }


    public void sing_in() {
        driver.findElement(By.id("sign_in_btn")).click();
        driver.findElement(By.id("username_input")).sendKeys(username);
        driver.findElement(By.id("password_input")).sendKeys(password);
        driver.findElement(By.id("sign_in_btn")).click();
    }


    /**
     * Generate a random string.
     */
    private String generateRandomUsername() {
        String username = new RandomString().nextString();
        System.out.println("generateRandomUsername called: " + username);
        return username.toLowerCase();
    }


}








