import org.junit.Test;
import org.junit.runners.Parameterized;
import page.SamokatMainPage;
import org.junit.runner.RunWith;
import steps.FillAboutUser;
import steps.OrderPageSteps;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestPageSamokat extends BaseTests {

    // Поля для параметризованных тестов
    private final String name;
    private final String surname;
    private final String address;
    private final String phone;
    private final String metro;
    private final String comment;
    private final String color;
    private final boolean isHeader; // Флаг для определения типа кнопки

    // Конструктор для параметризованного теста
    public TestPageSamokat(String name, String surname, String address, String phone, String metro, String comment, String color, boolean isHeader) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.metro = metro;
        this.comment = comment;
        this.color = color;
        this.isHeader = isHeader;
    }

    // Единый провайдер тестовых данных
    @Parameterized.Parameters(name = "Тест {index}: {0} {1} ({7})")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {"Иван", "Иванов", "ул. Ленина, 1", "79001234567", "Черкизовская", "Позвоните за час", "grey", true},
                {"Мария", "Петрова", "пр. Мира, 15", "79007654321", "Сокольники", "Оставить у двери", "black", false}
        });
    }

    @Test
    public void testOrderProcess() {
        SamokatMainPage page = new SamokatMainPage(driver);
        page.openMainPage();
        page.handleCookieConsent();

        // Выбор кнопки заказа в зависимости от флага
        if (isHeader) {
            page.clickHeaderOrderButton();
        } else {
            page.scrollToMiddleOrderButton();
            page.clickMiddleOrderButton();
        }

        // Заполняем личные данные
        FillAboutUser fillUser = new FillAboutUser(driver);
        fillUser.fillPersonalData(name, surname, address, metro, phone)
                .proceedToOrderDetails();

        // Заполняем данные заказа
        OrderPageSteps orderSteps = new OrderPageSteps(driver);
        orderSteps.fillOrderDetails("24.10.2025", "сутки", color, comment);
        orderSteps.confirmOrder();
        orderSteps.verifyOrderSuccess();
    }
}
