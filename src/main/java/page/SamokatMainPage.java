package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SamokatMainPage {
    private final WebDriver driver;

    // URL константы
    private static final String MAIN_PAGE_URL = "https://qa-scooter.praktikum-services.ru/";
    // Селекторы для cookies
    private static final By COOKIE_CONSENT_BUTTON = By.id("rcc-confirm-button");
    // Селекторы для кнопок заказа
    private final By HEADER_ORDER_BUTTON = By.cssSelector("button[class='Button_Button__ra12g']");
    private final By MIDDLE_ORDER_BUTTON = By.cssSelector("button[class='Button_Button__ra12g Button_Middle__1CSJM']");
    // Константы для ожиданий
    private static final int DEFAULT_WAIT_SECONDS = 10;

    public SamokatMainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Открыть сайт
    public void openMainPage() {
        driver.get(MAIN_PAGE_URL);
    }

    // Обработка куки
    public void handleCookieConsent() {
        WebDriverWait wait = createWait();
        try {
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(COOKIE_CONSENT_BUTTON));
            cookieButton.click();
        } catch (Exception e) {
            System.out.println("Cookie consent window not found or already accepted");
        }
    }

    // Создание объекта WebDriverWait с настройками по умолчанию
    private WebDriverWait createWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_SECONDS));
    }

    // Метод для клика по кнопке заказа в шапке сайта
    public void clickHeaderOrderButton() {
        // Создаем объект ожидания с заданными параметрами
        WebDriverWait wait = createWait();

        // Ждем, пока элемент станет кликабельным, и получаем его
        WebElement orderButton = wait.until(
                ExpectedConditions.elementToBeClickable(HEADER_ORDER_BUTTON)
        );

        // Выполняем клик по кнопке
        orderButton.click();
    }

    // Метод для клика по кнопке заказа в центральной части сайта
    public void clickMiddleOrderButton() {
        // Создаем объект ожидания
        WebDriverWait wait = createWait();

        // Ждем появления и кликабельности кнопки в центре страницы
        WebElement orderButton = wait.until(
                ExpectedConditions.elementToBeClickable(MIDDLE_ORDER_BUTTON)
        );

        // Выполняем действие клика
        orderButton.click();
    }

    // Скроллинг до элемента с дополнительным отступом 30 пикселей
    public void scrollToElement(WebElement element) {
        try {
            // Первый вариант скроллинга
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });",
                    element
            );

            // Второй вариант с дополнительным смещением
            ((JavascriptExecutor) driver).executeScript(
                    "window.scrollBy(0, -100);",
                    element
            );

            // Добавить явное ожидание после скролла
            Thread.sleep(500); // Добавить паузу для стабилизации
        } catch (Exception e) {
            System.out.println("Ошибка при скролле: " + e.getMessage());
        }
    }


    // Нажатие на элемент аккордеона FAQ с предварительным скроллингом
    public void clickFaqAccordionItem(String faqId) {
        By faqLocator = By.xpath("//div[@id='" + faqId + "']");
        WebDriverWait wait = createWait();
        try {
            WebElement faqElement = wait.until(ExpectedConditions.presenceOfElementLocated(faqLocator));
            scrollToElement(faqElement);

            wait.until(ExpectedConditions.elementToBeClickable(faqElement));
            faqElement.click();
        } catch (Exception e) {
            System.out.println("Ошибка при клике на FAQ: " + e.getMessage());
            throw new RuntimeException("Не удалось кликнуть на элемент FAQ", e);

        }
    }

    // Проверка текста вопроса FAQ
    public void verifyFaqQuestion(String faqId, String expectedQuestion) {
        By faqLocator = By.xpath("//div[@id='" + faqId + "']");
        WebElement faqElement = driver.findElement(faqLocator);
        String actualQuestion = faqElement.getText();
        assertTrue("FAQ вопрос должен содержать ожидаемый текст", actualQuestion.contains(expectedQuestion));
    }
    // Проверка текста ответа в панели аккордеона FAQ
    public void verifyFaqAnswer(String panelId, String expectedAnswer) {
        WebDriverWait wait = createWait();

        // Ожидание появления панели
        By panelLocator = By.xpath("//div[@id='" + panelId + "']//p");
        WebElement answerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(panelLocator));

        // Скролл и ожидание видимости текста
        scrollToElement(answerElement);
        wait.until(ExpectedConditions.textToBePresentInElement(answerElement, expectedAnswer));

        String actualAnswer = answerElement.getText();
        assertTrue("FAQ ответ должен содержать ожидаемый текст", actualAnswer.contains(expectedAnswer));
    }




    // Метод для assertTrue
    public void assertTrue(String message, boolean condition) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
    public void scrollToMiddleOrderButton() {
        // Получаем элемент кнопки
        WebElement element = driver.findElement(By.cssSelector("button[class='Button_Button__ra12g Button_Middle__1CSJM']"));

        // Выполняем JavaScript для прокрутки
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });",
                element
        );
    }
}

