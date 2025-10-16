package steps;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class OrderPageSteps {
    private final WebDriver driver;

    // Селекторы для полей формы - данные заказа
    private static final By DATE_FIELD = By.cssSelector("input[placeholder='* Когда привезти самокат']");
    private static final By RENTAL_PERIOD_DROPDOWN = By.cssSelector("div[class='Dropdown-placeholder']");
    private static final By COLOR_GREY = By.cssSelector("input[id='grey']");
    private static final By COLOR_BLACK = By.cssSelector("input[id='black']");
    private static final By COMMENT_FIELD = By.cssSelector("input[placeholder='Комментарий для курьера']");

    // Селектор для выбора опции
    private static final By DATE_OCTOBER_24 = By.cssSelector("div[aria-label='Choose пятница, 24-е октября 2025 г.']");


    //Кнопка Заказать
    private static final By NEXT_BUTTON = By.cssSelector("button[class='Button_Button__ra12g Button_Middle__1CSJM']");
    //Селектор кнопки "Да"
    private static final By CONFIRM_BUTTON = By.xpath("//button[contains(text(), 'Да')]");
    // Селекторы для проверки результата
    private static final By ORDER_CONFIRMATION_MODAL = By.xpath("//div[contains(@class, 'Order_ModalHeader__3FDaJ') and contains(text(), 'Заказ оформлен')]");



    public OrderPageSteps(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriverWait createWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(20));
    }


    public void fillOrderDetails(String date, String period, String color, String comment) {

        clickCalendarDate();


        selectRentalPeriod(period);
        selectScooterColor(color);
        driver.findElement(COMMENT_FIELD).sendKeys(comment);
    }


    public void clickCalendarDate() {
        WebDriverWait wait = createWait();
        WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(DATE_FIELD));
        dateElement.click();

        WebElement specificDate = wait.until(ExpectedConditions.elementToBeClickable(DATE_OCTOBER_24));
        specificDate.click();
    }


    public void selectRentalPeriod(String period) {
        // Добавляем ожидание появления dropdown
        WebDriverWait wait = createWait();

        // Кликаем на dropdown
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(RENTAL_PERIOD_DROPDOWN));
        dropdown.click();

        // Формируем новый локатор с учетом возможных пробелов
        By periodLocator = By.xpath("//div[contains(text(),'" + period + "')]");

        // Добавляем ожидание появления опции
        WebElement periodElement = wait.until(ExpectedConditions.elementToBeClickable(periodLocator));
        periodElement.click();
    }

    //Выбор цвета самоката
    public void selectScooterColor(String colorType) {
        By colorLocator;
        if ("black".equals(colorType)) {
            colorLocator = COLOR_BLACK;
        } else {
            colorLocator = COLOR_GREY; // по умолчанию серый
        }
        WebDriverWait wait = createWait();
        WebElement colorElement = wait.until(ExpectedConditions.elementToBeClickable(colorLocator));
        colorElement.click();
    }

    //Подтверждение заказа
    public void confirmOrder() {
        WebDriverWait wait = createWait();

        try {
            // Ожидание кнопки "Далее"
            WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(NEXT_BUTTON));
            nextButton.click();

            // Ждем появления кнопки "Да" и кликаем
            WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(CONFIRM_BUTTON));
            confirmButton.click();

            // Ждем появления модального окна с успешным оформлением
            verifyOrderSuccess();

        } catch (TimeoutException e) {
            throw new AssertionError("Ошибка при подтверждении заказа", e);
        } catch (Exception e) {
            throw new AssertionError("Ошибка при обработке заказа", e);
        }
    }



    //Проверка подтверждения заказа

    public boolean verifyOrderSuccess() {
        WebDriverWait wait = createWait();

        try {
            // Ждем появления модального окна с сообщением об успешном оформлении
            WebElement confirmationModal = wait.until(ExpectedConditions.visibilityOfElementLocated(ORDER_CONFIRMATION_MODAL));

            // Проверяем текст в модальном окне
            String actualText = confirmationModal.getText().trim();
            if (!actualText.contains("Заказ оформлен")) {
                throw new AssertionError("Неверное сообщение в модальном окне: " + actualText);
            }

        } catch (TimeoutException e) {
            throw new AssertionError("Модальное окно с сообщением об успешном оформлении не появилось", e);
        } catch (Exception e) {
            throw new AssertionError("Ошибка при проверке модального окна", e);
        }
        return true;
    }
}
