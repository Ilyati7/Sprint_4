import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import page.SamokatMainPage;


import java.util.Arrays;
import java.util.Collection;


//Параметризованные тесты для FAQ аккордеона
@RunWith(Parameterized.class)
public class TestPageSamokatFAQ extends BaseTests {


    // Поля для параметризованных тестов
    private final String faqId;
    private final String expectedQuestion;
    private final String panelId;
    private final String expectedAnswer;

    // Конструктор для параметризованного теста FAQ
    public TestPageSamokatFAQ(String faqId, String expectedQuestion, String panelId, String expectedAnswer) {
        this.faqId = faqId;
        this.expectedQuestion = expectedQuestion;
        this.panelId = panelId;
        this.expectedAnswer = expectedAnswer;
    }

    //Провайдер тестовых данных для FAQ
    @Parameterized.Parameters(name = "FAQ тест {index}: {1}")
    public static Collection<Object[]> faqTestData() {
        return Arrays.asList(new Object[][]{
                {"accordion__heading-0", "Сколько это стоит? И как оплатить?",
                        "accordion__panel-0", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {"accordion__heading-1", "Хочу сразу несколько самокатов! Так можно?",
                        "accordion__panel-1", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {"accordion__heading-2", "Как рассчитывается время аренды?",
                        "accordion__panel-2", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {"accordion__heading-3", "Можно ли заказать самокат прямо на сегодня?",
                        "accordion__panel-3", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {"accordion__heading-4", "Можно ли продлить заказ или вернуть самокат раньше?",
                        "accordion__panel-4", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {"accordion__heading-5", "Вы привозите зарядку вместе с самокатом?",
                        "accordion__panel-5", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {"accordion__heading-6", "Можно ли отменить заказ?",
                        "accordion__panel-6", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {"accordion__heading-7", "Я жизу за МКАДом, привезёте?",
                        "accordion__panel-7", "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        });
    }

    //Тест работы с FAQ аккордеоном
    @Test
    public void faqAccordionTest() {
        SamokatMainPage page = new SamokatMainPage(driver);
        page.openMainPage();
        page.handleCookieConsent();
        page.clickFaqAccordionItem(faqId);
        page.verifyFaqQuestion(faqId,expectedQuestion);
        page.verifyFaqAnswer(panelId,expectedAnswer);

    }
}
