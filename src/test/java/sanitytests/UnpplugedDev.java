package sanitytests;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.boon.core.Sys;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
        This method receives Platform parameter (mobile: ) and then calls
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
        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), dc);

        username = generateRandomUsername();
        password = "lilach123";

        //  driver.setLogLevel(Level.INFO);

    }

    /*
            ##################################################################
            This method go back to the APP HP after every The test is over
            ##################################################################

         */
  /*  @AfterMethod
    public void resetApp() {
        driver.resetApp();
   } */

    @Test
    public void test01_register_plus_email_and_phone() {

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
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button1")));
        driver.findElement(By.id("button1")).click();


        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        test02_sing();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.id("payment_btn")).click();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.id("button1")).click();

        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        click_on_messenger_app();
     // install button   driver.findElement(By.id("install_app_btn")).click();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.findElement(By.id(" home")).click();

       // close button  driver.findElement(By.id("install_app_btn")).click();



    }

    @Test
    public void click_on_messenger_app() {
      //  driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        AndroidElement recyclerView = driver.findElement(By.id("apps_rv"));

        MobileElement messengerElement = recyclerView.findElementByAccessibilityId("com.unplugged.messenger");
        if (messengerElement != null) {
            messengerElement.click();
        }
    }

    @Test
    public void test02_sing() {
        driver.findElement(By.id("sign_in_btn")).click();
        driver.findElement(By.id("username_input")).sendKeys(username);
        driver.findElement(By.id("password_input")).sendKeys(password);
        driver.findElement(By.id("sign_in_btn")).click();
    }


    @Test
    public void test03_register_witout_email_and_phone() {

        driver.findElement(By.id("register_btn")).click();
        driver.findElement(By.id("first_name_input")).sendKeys("lilach");
        driver.findElement(By.id("last_name_input")).sendKeys("test");
        driver.findElement(By.id("username_input")).sendKeys(username);
        driver.findElement(By.id("password_input")).sendKeys(password);
        driver.findElement(By.id("confirm_password_input")).sendKeys(password);
        driver.findElement(By.id("register_btn")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button1")));
        driver.findElement(By.id("button1")).click();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        test02_sing();
    }

    @Test
    public void test04_forgotmyPassword() {
        driver.findElement(By.id("sign_in_btn")).click();
        driver.findElement(By.id("username_input")).sendKeys("tal35.test");
        driver.findElement(By.id("forgot_password_tv")).click();
        driver.findElement(By.id("username_input")).sendKeys("tal35.test");
        driver.findElement(By.id("password_input")).click();

    }
    /**
     * Generate a random string.
     */
    private String generateRandomUsername(){
        String username = new RandomString().nextString();
        System.out.println("generateRandomUsername called: "+username);
        return username.toLowerCase();
    }


}




  /*
            ##################################################################
            This method close  the APP after all The tests in the class is over.
            ##################################################################

         */

    /*@AfterClass
    public void close_app() {
        driver.quit();

    }*/








