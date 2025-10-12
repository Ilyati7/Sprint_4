import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;



public class BaseTests {
    protected WebDriver driver;



    @Before
    public void setUp() {
        // Устанавливаем кодировку UTF-8 для корректного отображения русских символов
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("console.encoding", "UTF-8");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "-start-maximized");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);

//        FirefoxOptions options = new FirefoxOptions();
//        options.addArguments("--no-sandbox",
//                "--disable-dev-shm-usage",
//                "--disable-gpu",
//                "-start-maximized");
//        WebDriverManager.firefoxdriver().setup();
//        driver = new FirefoxDriver(options);


        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }


    @After
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }

}
