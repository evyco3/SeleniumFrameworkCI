package org.evy.framework.objets.authentication;

import org.evy.framework.objets.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class NavigateToAuthenticationSection extends BasePage {
    @FindBy(css = "div[id='menu'] a[href*='login']")
    private WebElement loginLink;

    @FindBy(css = "div[id='menu'] a[href*='register']")
    private WebElement registerLink;


    public LoginPage navigateToLoginPage(){
        clickNavigateToPageAndLog(this.loginLink,"login link","login","LoginPage");
        return new LoginPage();
    }

    public RegisterPage navigateToRegisterPage(){
        clickNavigateToPageAndLog(this.registerLink,"register link","Register","RegisterPage");
        return new RegisterPage();
    }

}
