
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import page.SamokatMainPage;


import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;


//Параметризованные тесты для FAQ аккордеона
@RunWith(Parameterized.class)
public class TestPageSamokatFAQ extends BaseTests {
    private static final Logger logger = LoggerFactory.getLogger(TestPageSamokatFAQ.class);
    private final int questionNumber;
    private final String expectedQuestion;
    private final String expectedAnswer;


    public TestPageSamokatFAQ(int questionNumber, String expectedQuestion, String expectedAnswer) {
        this.questionNumber = questionNumber;
        this.expectedQuestion = expectedQuestion;
        this.expectedAnswer = expectedAnswer;
    }

    @Parameterized.Parameters(name = "FAQ тест {index}: вопрос №{0}")
    public static Collection<Object[]> faqTestData() {
        return Arrays.asList(new Object[][] {
                {0, "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Я жизу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        });
    }

    //Тест работы с FAQ аккордеоном
    @Test
    public void faqAccordionTest() {
        SamokatMainPage page = new SamokatMainPage(driver);
        page.openMainPage();
        page.handleCookieConsent();

        try {
            page.checkFaqItem(questionNumber, expectedQuestion, expectedAnswer);
            logger.info("Тест пройден успешно для вопроса №{}", questionNumber);
        } catch (Exception e) {
            logger.error("Ошибка при проверке FAQ вопроса №{}", questionNumber, e);
            throw e;
        }
    }
}