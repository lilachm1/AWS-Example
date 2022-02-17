package sanitytest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
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
     // dc.setCapability("isHeadless", true);
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".splash.SplashActivity");
        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
        username = generateRandomUsername();
        password = "lilach123";

    }

         /*  ##################################################################
            Automation Script no1: Before the Messenger App is installed.
             This test check: Registration with email and password with
             generateRandomUsername and string password .
             Then sing in. Then at the payment page press ok with no money.
             ##################################################################

*/

  @Test(description ="register plus email and phone and install messengerApp")

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

     wait.until(ExpectedConditions.elementToBeClickable(By.id("button1")));
     driver.findElement(By.id("button1")).click();

     driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
     sing_in();

     driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
     driver.findElement(By.id("payment_btn")).click();

     driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
     driver.findElement(By.id("button1")).click();

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
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("install_app_btn")));

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


    @Test(description = "register without email and phone")
    public void register_without_email_and_phone_05() {

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

     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@text='OK']")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("navigate_back_iv")).click();
        driver.resetApp();

    }


/*
 ##################################################################
    Automation Script no6: After the messenger App is installed this test check :
    Registration with email and password with generateRandomUsername and string password.
    Then press on forgot my password and then manually verify that The email has been sent

  ##################################################################
  */


    @Test(description = "register and_forgot_my_password")
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
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button1")));
        driver.findElement(By.id("button1")).click();


        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        forgot_my_Password();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.id("navigate_back_iv")).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        driver.findElement(By.id("navigate_back_iv")).click();
        driver.resetApp();

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
    private String generateRandomUsername(){
        String username = new RandomString().nextString();
        System.out.println("generateRandomUsername called: "+username);
        return username.toLowerCase();
    }



}








