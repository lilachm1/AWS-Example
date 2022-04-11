package sanitytest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.testng.Assert.*;


// This Class is the main sanity tests of the DEV version.


@Listeners(listeners.class)
public class UnpplugedDev {


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
        dc.setCapability(MobileCapabilityType.UDID, "R9PR900B9GL");
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.unplugged.store");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".splash.SplashActivity");
  //    dc.setCapability("noSign", true);
        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
        username = generateRandomUsername();
        password = "lilach123";


    }

    @Test(description = "Test 01: Singin with old user and password (not generateRandomUsername and string password)")
    @Description("Test Description: Singin with old user and password")
    public void sing_in_manual() {
        driver.resetApp();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.findElement(By.id("sign_in_btn")).click();
        driver.findElement(By.id("username_input")).sendKeys("ty97.test");
        driver.findElement(By.id("password_input")).sendKeys("ty97");
        driver.findElement(By.id("sign_in_btn")).click();
    }

    @Test(description = "Test 02: Register plus email and phone")
    @Description("Test Description: Registration with email and password with generateRandomUsername and string password." +
            "Then sing in.")
    public void register_plus_email_and_phone() {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.findElement(By.id("register_btn")).click();
        driver.findElement(By.id("first_name_input")).sendKeys("lilach");
        driver.findElement(By.id("last_name_input")).sendKeys("test");
        driver.findElement(By.id("username_input")).sendKeys(username);
        driver.findElement(By.id("email_input")).sendKeys("lilach@unplugged-systems.com");
        driver.findElement(By.id("phone_number_input")).sendKeys("972526623377");
        driver.findElement(By.id("password_input")).sendKeys(password);
        driver.findElement(By.id("confirm_password_input")).sendKeys(password);
        driver.findElement(By.id("register_btn")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("button1")));
        driver.findElement(By.id("button1")).click();

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        sing_in();

        //  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        //  driver.findElement(By.id("payment_btn")).click();

        //  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        //   driver.findElement(By.id("button1")).click();

    }

    @Test(description = "Test 03: Subscription Free")
    @Description("Test Description: At the SubscriptionPage page choose free, then verify the" +
            " subscription description text and press ok with no money")
    public void subscription_free() {
        WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dropdown_item_tv")));
        assertEquals(driver.findElement(By.id("dropdown_item_tv")).getText(),"FREE - 0.0$");
       assertEquals(driver.findElement(By.id("subscription_description_tv")).getText(),"Free subscription " +
        "get basic functionality like, full access to UP Store, limited VPN services and basic messenger application.");

        driver.findElement(By.id("payment_btn")).click();
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
     driver.findElement(By.id("button1")).click();
       // driver.resetApp();
    }

    @Test(description = "Test 04: Subscription Month")
    @Description("Test Description: At the subscription page click month then verify the" +
            " subscription description text then add cupon and verify" +
            " the total price, then press pay with credit card")
    public void subscription_month () {
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dropdown_item_tv")));
        driver.findElement(By.id("dropdown_item_tv")).click();
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@text='MONTH - 9.9$']")).click();
        assertEquals(driver.findElement(By.xpath("//*[@text='MONTH - 9.9$']")).getText(),"MONTH - 9.9$");
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        assertEquals(driver.findElement(By.id("subscription_description_tv")).getText(),"Pro subscription get full access to all Unplugged Systems services for month.");
        driver.findElement(By.id("coupon_cb")).click();
        driver.findElement(By.id("coupon_input")).sendKeys("UPC5");
        driver.findElement(By.id("apply_coupon_btn")).click();
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("total_price_tv")));
        assertEquals(driver.findElement(By.id("total_price_tv")).getText(),"Item total:               $9.90\n" +
                "Coupon discount: -$5.00\n" +
                "Total:                       $4.90");
        driver.findElement(By.id("payment_btn")).click();
      driver.resetApp();
    }

    @Test(description = "Test 05: Subscription year")
    @Description("Test Description:At the subscription page click year then verify the" +
            "subscription description text then add cupon and verify" +
            "the total price, then press pay with credit card")
    public void subscription_year (){
    WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dropdown_item_tv")));
          driver.findElement(By.id("dropdown_item_tv")).click();
        driver.findElement(By.xpath("//*[@text='YEAR - 99.9$']")).click();
        assertEquals(driver.findElement(By.xpath("//*[@text='YEAR - 99.9$']")).getText(),"YEAR - 99.9$");
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        assertEquals(driver.findElement(By.id("subscription_description_tv")).getText(),"Pro subscription get full access to all Unplugged Systems services for year.");

        driver.findElement(By.id("coupon_cb")).click();
        driver.findElement(By.id("coupon_input")).sendKeys("UPC30");
        driver.findElement(By.id("apply_coupon_btn")).click();
        assertEquals(driver.findElement(By.id("total_price_tv")).getText(), "Item total:               $99.90\n" +
                        "Coupon discount: -$30.00\n" +
                        "Total:                       $69.90");

        driver.findElement(By.id("payment_btn")).click();
        driver.resetApp();
    }

        @Test(description = "Test 06: Click on messenger app")
        @Description("Test Description: At the store page click on the Messenger App and press on the download button")
                public void click_on_messenger_app () {
        //    WebDriverWait wait = new WebDriverWait(driver, 10);
         //   wait.until(ExpectedConditions.presenceOfElementLocated(By.id("search_apps_input")));
        //    driver.findElement(By.id("search_apps_input")).sendKeys("up messenger");
           WebDriverWait wait = new WebDriverWait(driver, 40);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("apps_rv")));
            //driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
            AndroidElement recyclerView = driver.findElement(By.id("apps_rv"));

            MobileElement messengerElement = recyclerView.findElementByAccessibilityId("com.unplugged.messenger");
            if (messengerElement != null) {

                messengerElement.click();
            }

            // driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("install_app_btn")));

            driver.findElement(By.id("install_app_btn")).click();
            driver.resetApp();

        }

        @Test(description = "Test 07: Click on messenger app2")
        @Description("Test Description: At the store page , click on messenger app and press on the close button.")
        public void click_on_messenger_app2 () {
            //    WebDriverWait wait = new WebDriverWait(driver, 10);
            //   wait.until(ExpectedConditions.presenceOfElementLocated(By.id("search_apps_input")));
            //    driver.findElement(By.id("search_apps_input")).sendKeys("up messenger");
            WebDriverWait wait = new WebDriverWait(driver, 40);
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


      /*  @Test(description = "Test 08: logout")
       @Description("Test Description: At the store page , click logout")
        public void logout () {

            driver.findElement(By.className("android.widget.ImageButton")).click();
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            driver.findElement(By.id("action_logout")).click();
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            driver.findElement(By.xpath("//*[@text='OK']")).click();
        }*/


        @Test(description = "Test 09: Register without email and phone")
        @Description("Test Description: Register without email and phone with generateRandomUsername and string password the sing in")
        public void register_without_email_and_phone () {
            driver.findElement(By.id("register_btn")).click();
            driver.findElement(By.id("first_name_input")).sendKeys("lilach");
            driver.findElement(By.id("last_name_input")).sendKeys("test");
            driver.findElement(By.id("username_input")).sendKeys(username);
            driver.findElement(By.id("password_input")).sendKeys(password);
            driver.findElement(By.id("confirm_password_input")).sendKeys(password);
            driver.findElement(By.id("register_btn")).click();
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("button1")));
            driver.findElement(By.id("button1")).click();

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.findElement(By.xpath("//*[@text='OK']")).click();
            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            sing_in();

        }

                @Test(description ="Test 10: Register and forgot my password")
        @Description("Test Description:Registration with email and password with generateRandomUsername and string password." +
                "Then press on forgot my password and then manually verify that The email has been sent")
        public void register_and_forgot_my_password() {
            WebDriverWait wait = new WebDriverWait(driver, 80);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("register_btn")));
            driver.findElement(By.id("register_btn")).click();
            driver.findElement(By.id("first_name_input")).sendKeys("lilach");
            driver.findElement(By.id("last_name_input")).sendKeys("test");
            driver.findElement(By.id("username_input")).sendKeys(username);
            driver.findElement(By.id("email_input")).sendKeys("lilach@unplugged-systems.com");
            driver.findElement(By.id("phone_number_input")).sendKeys("972526623377");
            driver.findElement(By.id("password_input")).sendKeys(password);
            driver.findElement(By.id("confirm_password_input")).sendKeys(password);
            driver.findElement(By.id("register_btn")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@text='OK']")));
            driver.findElement(By.xpath("//*[@text='OK']")).click();

            driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
            forgot_my_Password();
            driver.resetApp();
        }

        @Test(description = "Test 11: Press on my Apps then press Up Apps")
        @Description("Test Description: At the up store page click my Apps then Up Apps the logout")
        public void my_Apps_plus_up_Apps_plus_logout ()
        {
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("android.widget.ImageButton")));
            driver.findElement(By.className("android.widget.ImageButton")).click();
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            driver.findElement(By.id("action_my_apps")).click();
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            driver.findElement(By.className("android.widget.ImageButton")).click();
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            driver.findElement(By.id("action_apps_for_you")).click();
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            driver.findElement(By.className("android.widget.ImageButton")).click();
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            driver.findElement(By.id("action_logout")).click();
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            driver.findElement(By.xpath("//*[@text='OK']")).click();

        }

        @Test(description = "Test 12: Press on experimental button")
        @Description("Test Description: At the up store page click on experimental button then ")
        public void experimental_button (){
    WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("android.widget.ImageButton")));
           driver.findElement(By.className("android.widget.ImageButton")).click();
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            driver.findElement(By.id("action_experimental")).click();
          driver.resetApp();

        }
    @Test(description = "Test 13: Click on vpn app")
    @Description("Test Description: At the store page, click on vpn app and press on the close button.")
    public void click_on_vpn_app () {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("apps_rv")));
        AndroidElement recyclerView = driver.findElement(By.id("apps_rv"));

        MobileElement vpnElement = recyclerView.findElementByAccessibilityId("com.unplugged.vpn");
        if (vpnElement != null) {

            vpnElement.click();
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='Close']")));
        driver.findElement(By.id("close_tv")).click();
    }

    @Test(description = "Test 14: Click on vpn app")
    @Description("Test Description: At the store page, click on vpn app and press on on the download button.")
    public void click_on_vpn_app2 () {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("apps_rv")));
        AndroidElement recyclerView = driver.findElement(By.id("apps_rv"));

        MobileElement vpnElement = recyclerView.findElementByAccessibilityId("com.unplugged.vpn");
        if (vpnElement != null) {

            vpnElement.click();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("install_app_btn")));

        driver.findElement(By.id("install_app_btn")).click();
        driver.resetApp();

    }



    @Test (description = "Test 15: search app ")
    @Description("Test Description: At the store page, typ the letter at the search filed and press at the search bottom .")
    public void search_app()
    {
        WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("search_input_overlay")));
driver.findElement(By.id("search_input_overlay")).click();
 driver.findElement(By.id("search_apps_input")).sendKeys("up");
driver.tap(1, 610, 1421, 500);
click_on_vpn_app2();


    }


    @Test (description = "Test 16: swipe_upstore ")
    @Description("Test Description: At the up-store page, swipe down")
    public void swipe_upstore() {
        WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("search_input_overlay")));
        driver.executeScript("seetest:client.swipe(\"Down\", 0, 2000)");
        driver.executeScript("seetest:client.swipe(\"Down\", 0, 2000)");
        driver.executeScript("seetest:client.swipe(\"Down\", 0, 2000)");
        driver.executeScript("seetest:client.swipe(\"Down\", 0, 2000)");
        driver.executeScript("seetest:client.swipe(\"Down\", 0, 2000)");
         driver.resetApp();
    }

    public void forgot_my_Password () {
            driver.findElement(By.id("sign_in_btn")).click();
            driver.findElement(By.id("username_input")).sendKeys(username);
            driver.findElement(By.id("forgot_password_tv")).click();
            driver.findElement(By.id("username_input")).sendKeys(username);
            driver.findElement(By.id("password_input")).click();


        }


        public void sing_in () {
            driver.findElement(By.id("sign_in_btn")).click();
            driver.findElement(By.id("username_input")).sendKeys(username);
            driver.findElement(By.id("password_input")).sendKeys(password);
            driver.findElement(By.id("sign_in_btn")).click();
        }


        /**
         * Generate a random string.
         */
        private String generateRandomUsername () {
            String username = new RandomString().nextString();
            System.out.println("generateRandomUsername called: " + username);
            return username.toLowerCase();
        }

    }









