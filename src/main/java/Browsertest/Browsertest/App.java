package Browsertest.Browsertest;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class App {
    private WebDriver driver;
    private WebDriverWait wait;
    private final Duration duration = Duration.ofSeconds(10);

    @BeforeClass
    public void setup() {
        // Use WebDriverManager to automatically handle ChromeDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(duration);
        wait = new WebDriverWait(driver, duration);
    }

    @Test(priority = 1)
    public void takeTest() {
        driver.get("https://test.cignix.com/");

        // Wait for the "Take Sim Test" icon to be clickable
        WebElement testbtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='root']/div[2]/section[2]/div/div[2]/div")));

        // Scroll to the element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", testbtn);
        hardPause(2000); // Allow scrolling to complete

        // Click using JavaScript to avoid interception
        try {
            testbtn.click();
        } catch (Exception e) {
            System.out.println("Element click intercepted, using JavaScript click.");
            js.executeScript("arguments[0].click();", testbtn);
        }

        // Click the first round button
        WebElement firstrnd = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='root']/div[2]/section[7]/div[2]/div[1]/div/div/div[1]")));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", firstrnd);
        hardPause(2000); // Allow time for smooth scrolling

        try {
            firstrnd.click();
        } catch (Exception e) {
            System.out.println("Element click intercepted, using JavaScript click.");
            js.executeScript("arguments[0].click();", firstrnd);
        }
    }

    @Test(priority = 2)
    public void clickCheckboxes() {
        // Define XPaths for the checkboxes from the second one onward
        String[] checkboxXpaths = {
            "//*[@id='root']/div[2]/section[7]/div[2]/div[2]/div/div/div[1]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[3]/div/div/div[1]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[4]/div/div/div[2]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[5]/div/div/div[2]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[6]/div/div/div[3]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[7]/div/div/div[2]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[8]/div/div/div[2]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[9]/div/div/div[1]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[10]/div/div/div[2]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[11]/div/div/div[1]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[12]/div/div/div[1]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[13]/div/div/div[1]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[14]/div/div/div[1]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[15]/div/div/div[1]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[16]/div/div/div[1]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[17]/div/div/div[1]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[18]/div/div/div[1]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[19]/div/div/div[1]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[20]/div/div/div[1]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[21]/div/div/div[1]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[22]/div/div/div[1]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[23]/div/div/div[1]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[24]/div/div/div[1]",
            "//*[@id='root']/div[2]/section[7]/div[2]/div[25]/div/div/div[1]"
        };

        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (String xpath : checkboxXpaths) {
            try {
                WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

                // Scroll into view
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", checkbox);
                hardPause(1000); // Pause to allow scrolling

                // Click the checkbox (handling interception)
                try {
                    checkbox.click();
                } catch (Exception e) {
                    System.out.println("Click intercepted, using JavaScript click.");
                    js.executeScript("arguments[0].click();", checkbox);
                }

                System.out.println("Clicked checkbox: " + xpath);

            } catch (Exception e) {
                System.out.println("Checkbox not found: " + xpath);
            }
        }
        WebElement finalbtn=driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/section[7]/div[2]/button"));
        finalbtn.click();
    }
    

    @Test(priority = 3)
    public void enterdetails() {
        String mainWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.numberOfWindowsToBe(1));

        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(mainWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // Generate random email
        String randomEmail = "rishikesh" + new Random().nextInt(10000) + "@gmail.com";

        // Generate random 10-digit mobile number
        String randomMobile = "9" + (long) (Math.random() * 1000000000L); 

        // Enter Name
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='name']"))).sendKeys("Rishikesh");

        // Enter Email
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='email']"))).sendKeys(randomEmail);

        // Enter Mobile Number
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='mobile']"))).sendKeys(randomMobile);

        // Enter Password
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='password']"))).sendKeys("Sabaricignix@111");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions actions = new Actions(driver);

        // Locate DOB field
        WebElement dobField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='radix-:r86:']/article/div[2]/form/div/div[4]/div/input[1]")));
        
        // Set DOB using JavaScript (to prevent event triggers)
        String dobValue = "24-01-2003";
        js.executeScript("arguments[0].setAttribute('value', '" + dobValue + "')", dobField);
        
        // Trigger change event to ensure DOB is stored
        js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", dobField);
        js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", dobField);
        
        actions.moveToElement(dobField).click().sendKeys(Keys.TAB).perform(); // Ensures focus is lost properly

        hardPause(1000); // Wait before interacting with gender

        // Click Gender Dropdown
        WebElement genderDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='radix-:r86:']/article/div[2]/form/div/div[6]/button")));
        genderDropdown.click();
        hardPause(1000); // Allow dropdown to open

        // Select "Male"
        WebElement maleOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Male']")));
        maleOption.click();

        hardPause(1000); // Pause before re-entering DOB

        // RE-ENTER DOB AFTER GENDER SELECTION
        js.executeScript("arguments[0].setAttribute('value', '" + dobValue + "')", dobField);
        js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", dobField);
        js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", dobField);
        actions.moveToElement(dobField).click().sendKeys(Keys.TAB).perform(); // Ensure focus is removed

        // Confirm DOB value is correctly set before proceeding
        String dobAfterSelection = dobField.getAttribute("value");
        System.out.println("Final DOB Value: " + dobAfterSelection); // Debugging

        if (!dobAfterSelection.equals(dobValue)) {
            System.out.println("Warning: DOB field reset detected. Re-setting value.");
            js.executeScript("arguments[0].setAttribute('value', '" + dobValue + "')", dobField);
            js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", dobField);
            js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", dobField);
        }

        // Click "Get Started Now" button
        WebElement GSNnowbtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='radix-:r86:']/article/div[2]/button")));
        GSNnowbtn.click();
    }

    // Helper function for pauses
    private void hardPause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {    
        // driver.quit();
    
}}
