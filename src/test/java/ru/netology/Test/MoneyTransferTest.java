package ru.netology.Test;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.Data.DataHelper;
import ru.netology.Page.DashboardPage;
import ru.netology.Page.LoginPage;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    @Test
    public void shouldTransferFromFirstToSecond() {
        int amount = 500;
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        int balanceOfFirstCardBefore = DashboardPage.getCurrentBalanceOfFirstCard();
        int balanceOfSecondCardBefore = DashboardPage.getCurrentBalanceOfSecondCard();
        val transferPage = dashboardPage.secondCard();
        val cardInfo = DataHelper.getFirstCardInfo();
        transferPage.makeTransfer(amount, cardInfo);
        int balanceAfterTransferFirstCard = DataHelper.balanceOfSecondCardAfterTransfer(balanceOfSecondCardBefore, amount);
        int balanceAfterTransferSecondCard = DataHelper.balanceOfFirstCardAfterTransfer(balanceOfFirstCardBefore, amount);
        int balanceOfFirstCardAfter = DashboardPage.getCurrentBalanceOfSecondCard();
        int balanceOfSecondCardAfter = DashboardPage.getCurrentBalanceOfFirstCard();
        assertEquals(balanceAfterTransferFirstCard, balanceOfFirstCardAfter);
        assertEquals(balanceAfterTransferSecondCard, balanceOfSecondCardAfter);
    }

    @Test
    public void shouldTransferFromSecondToFirst() {
        int amount = 700;
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        int balanceOfFirstCardBefore = DashboardPage.getCurrentBalanceOfFirstCard();
        int balanceOfSecondCardBefore = DashboardPage.getCurrentBalanceOfSecondCard();
        val transferPage = dashboardPage.firstCard();
        val cardInfo = DataHelper.getSecondCardInfo();
        transferPage.makeTransfer(amount, cardInfo);
        int balanceAfterTransferFirstCard = DataHelper.balanceOfSecondCardAfterTransfer(balanceOfFirstCardBefore, amount);
        int balanceAfterTransferSecondCard = DataHelper.balanceOfFirstCardAfterTransfer(balanceOfSecondCardBefore, amount);
        int balanceOfFirstCardAfter = DashboardPage.getCurrentBalanceOfFirstCard();
        int balanceOfSecondCardAfter = DashboardPage.getCurrentBalanceOfSecondCard();
        assertEquals(balanceAfterTransferFirstCard, balanceOfFirstCardAfter);
        assertEquals(balanceAfterTransferSecondCard, balanceOfSecondCardAfter);
    }

    @Test
    void shouldTransferMoreThanCardBalance() {
        int amount = 21000;
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        DashboardPage.getCurrentBalanceOfFirstCard();
        DashboardPage.getCurrentBalanceOfSecondCard();
        val transferPage = dashboardPage.firstCard();
        val cardInfo = DataHelper.getSecondCardInfo();
        transferPage.makeTransfer(amount, cardInfo);
        $(withText("Вы ввели сумму, превышающую остаток средств на Вашем счете. Пожалуйста, введите другую сумму")).shouldBe(Condition.visible);
    }

    @Test
    void shouldTransferZeroAmount() {
        int amount = 0;
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        DashboardPage.getCurrentBalanceOfFirstCard();
        DashboardPage.getCurrentBalanceOfSecondCard();
        val transferPage = dashboardPage.firstCard();
        val cardInfo = DataHelper.getSecondCardInfo();
        transferPage.makeTransfer(amount, cardInfo);
        $(withText("Пожалуйста, укажите сумму, которую необходимо ввести")).shouldBe(Condition.visible);
    }
}
