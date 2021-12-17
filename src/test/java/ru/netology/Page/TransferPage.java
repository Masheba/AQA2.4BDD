package ru.netology.Page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.Data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement amountInput = $("[data-test-id='amount'] input");
    private SelenideElement fromInput = $("[data-test-id='from'] input");
    private SelenideElement transferHead = $(byText("Пополнение карты"));

    public TransferPage() {
        transferHead.shouldBe(visible);
    }

    public DashboardPage makeTransfer(int amountToTransfer, DataHelper.CardInfo cardInfo) {
        amountInput.setValue(String.valueOf(amountToTransfer));
        fromInput.setValue(cardInfo.getCardNumber());
        transferButton.click();
        return new DashboardPage();
    }
}
