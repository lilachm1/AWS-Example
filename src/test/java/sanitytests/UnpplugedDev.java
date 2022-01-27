package sanitytests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

@Listeners(listeners.class)
public class UnpplugedDev {


        private String reportDirectory = "reports";
        private String reportFormat = "xml";
        private String testName = "test01";
        protected AndroidDriver<AndroidElement> driver = null;

        DesiredCapabilities dc = new DesiredCapabilities();

        @BeforeClass
        public void setUp() throws MalformedURLException {
            dc.setCapability("reportDirectory", reportDirectory);
            dc.setCapability("reportFormat", reportFormat);
            dc.setCapability("testName", testName);
            dc.setCapability(MobileCapabilityType.UDID, "R9PR900B9GL");
            dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.unplugged.store");
            dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".splash.SplashActivity");
            driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), dc);

            //  driver.setLogLevel(Level.INFO);

        }

    /*@AfterMethod
    public void resetApp() {
        driver.resetApp();
    }*/

        @Test
        public void test01_register_plus_email_and_phone() {

            driver.findElement(By.id("register_btn")).click();
            driver.findElement(By.id("first_name_input")).sendKeys("tal34");
            driver.findElement(By.id("last_name_input")).sendKeys("test");
            driver.findElement(By.id("email_input")).sendKeys("lilach@unplugged-systems.com");
            driver.findElement(By.id("phone_number_input")).sendKeys("972526623377");
            driver.findElement(By.id("password_input")).sendKeys("lilach5");
            driver.findElement(By.id("confirm_password_input")).sendKeys("lilach5");
            driver.findElement(By.id("register_btn")).click();
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("button1")));
            driver.findElement(By.id("button1")).click();
        }

    @Test
    public void test02_forgotmyPassword() {
        driver.findElement(By.id("sign_in_btn")).click();
        driver.findElement(By.id("username_input")).sendKeys("tal32.test");
        driver.findElement(By.id("forgot_password_tv")).click();
        driver.findElement(By.id("username_input")).sendKeys("tal32.test");
        driver.findElement(By.id("password_input")).click();
    }
            @Test
            public void test03_sing_in() {
                driver.findElement(By.id("sign_in_btn")).click();
                driver.findElement(By.id("username_input")).sendKeys("tal23.test");
                driver.findElement(By.id("password_input")).sendKeys("lilach5");
                driver.findElement(By.id("sign_in_btn")).click();

            }




             @Test
        public void test04_register_witout_email_and_phone() {

            driver.findElement(By.id("register_btn")).click();
            driver.findElement(By.id("first_name_input")).sendKeys("tal34");
            driver.findElement(By.id("last_name_input")).sendKeys("test");
            driver.findElement(By.id("password_input")).sendKeys("lilach5");
            driver.findElement(By.id("confirm_password_input")).sendKeys("lilach5");
            driver.findElement(By.id("register_btn")).click();
            driver.findElement(By.id("button1")).click();

        }





        }

    /*@AfterClass
    public void close_app() {
        driver.quit();

    }*/








