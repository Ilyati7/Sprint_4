package steps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;



public class FillAboutUser {

    private final WebDriver driver;

    // Селекторы для полей формы - личные данные
    private static final By NAME_FIELD = By.cssSelector("input[placeholder='* Имя']");
    private static final By SURNAME_FIELD = By.cssSelector("input[placeholder='* Фамилия']");
    private static final By ADDRESS_FIELD = By.cssSelector("input[placeholder='* Адрес: куда привезти заказ']");
    private static final By METRO_FIELD = By.cssSelector("input[placeholder='* Станция метро']");
    private static final By PHONE_FIELD = By.cssSelector("input[placeholder='* Телефон: на него позвонит курьер']");
    //Селектор кнопки далее
    private static final By NEXT_BUTTON = By.cssSelector("button[class='Button_Button__ra12g Button_Middle__1CSJM']");


    //Конструктор класса
    public FillAboutUser(WebDriver driver) {
        this.driver = driver;
    }

    private WebDriverWait createWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public FillAboutUser fillPersonalData(String name, String surname, String address, String metro, String phone) {
        driver.findElement(NAME_FIELD).sendKeys(name);
        driver.findElement(SURNAME_FIELD).sendKeys(surname);
        driver.findElement(ADDRESS_FIELD).sendKeys(address);
        driver.findElement(METRO_FIELD).click();
        selectMetroStation(metro);
        driver.findElement(PHONE_FIELD).sendKeys(phone);
        return this;
    }

    //Переход к деталям заказа
    public void proceedToOrderDetails () {
        WebDriverWait wait = createWait();
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(NEXT_BUTTON));
        nextButton.click();
    }
    public void selectMetroStation(String stationName) {
        By metroStationLocator = By.xpath("//div[text()='" + stationName + "']");
        WebDriverWait wait = createWait();
        WebElement metroStation = wait.until(ExpectedConditions.elementToBeClickable(metroStationLocator));
        metroStation.click();
    }
}
