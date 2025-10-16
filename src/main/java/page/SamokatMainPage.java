package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SamokatMainPage {
    private final WebDriver driver;

    // URL константы
    private static final String MAIN_PAGE_URL = "https://qa-scooter.praktikum-services.ru/";
    // Селекторы для cookies
    private static final By COOKIE_CONSENT_BUTTON = By.id("rcc-confirm-button");
    // Селекторы для кнопок заказа
    private static final By HEADER_ORDER_BUTTON = By.cssSelector("button[class='Button_Button__ra12g']");
    private static final By MIDDLE_ORDER_BUTTON = By.cssSelector("button[class='Button_Button__ra12g Button_Middle__1CSJM']");
    // Обновленные селекторы для FAQ
    private static final By faqHeaders = By.cssSelector(".accordion__heading");
    private static final By faqPanels = By.cssSelector(".accordion__panel");
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
            // Явное ожидание видимости элемента
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            System.out.println("Ошибка при ожидании видимости элемента: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при скролле элемента: " + e.getMessage());
        }
    }

    // Проверка FAQ с использованием индексов
    public void checkFaqItem(int questionIndex, String expectedQuestion, String expectedAnswer) {
        WebDriverWait wait = createWait();
        WebElement question = driver.findElements(By.cssSelector(".accordion__heading")).get(questionIndex);

        try {

            wait.until(ExpectedConditions.elementToBeClickable(question));
            question.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", question);
        }
        // Получаем все заголовки и панели FAQ
        List<WebElement> headers = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(faqHeaders));
        List<WebElement> panels = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(faqPanels));

        // Проверяем корректность индекса
        if (questionIndex >= headers.size() || questionIndex >= panels.size()) {
            throw new RuntimeException("Неверный индекс FAQ: " + questionIndex);
        }

        // Кликаем по нужному заголовку
        WebElement header = headers.get(questionIndex);
        scrollToElement(header);
        header.click();

        // Проверяем вопрос
        String actualQuestion = header.getText();
        assertTrue("Неверный текст вопроса", actualQuestion.contains(expectedQuestion));

        // Проверяем ответ
        WebElement panel = panels.get(questionIndex);
        String actualAnswer = panel.getText();
        assertTrue("Неверный текст ответа", actualAnswer.contains(expectedAnswer));
    }

    // Метод для assertTrue с улучшенной логировкой
    public void assertTrue(String message, boolean condition) {
        if (!condition) {
            System.out.println("Ошибка проверки: " + message);
            throw new AssertionError(message);
        }
    }

    public void scrollToMiddleOrderButton() {
        WebDriverWait wait = createWait();
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(MIDDLE_ORDER_BUTTON));

        // Выполняем JavaScript для прокрутки
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });",
                element
        );
    }
}