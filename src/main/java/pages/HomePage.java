
package pages;

import base.BaseTest;
import org.openqa.selenium.By;

public class HomePage extends BaseTest {

    private By adminLink = By.className("admin");
    private By logoLink = By.className("logo");
    private By aboutUsLink = By.linkText("About Us");
    private By servicesLink = By.linkText("Services");
    private By productsLink = By.partialLinkText("Products");
    private By locationsLink = By.partialLinkText("Locations");
    private By adminPageLink = By.linkText("Admin Page");
    private By homeLink = By.linkText("home");
    private By aboutLink = By.linkText("about");
    private By contactLink = By.linkText("contact");
    private By usernameInput = By.name("username");
    private By passwordInput = By.name("password");
    private By loginButton = By.className("button");
    private By forgotLoginLink = By.partialLinkText("Forgot login info?");
    private By registerLink = By.partialLinkText("Register");
    private By firstNameInput = By.id("customer.firstName");
    private By lastNameInput = By.id("customer.lastName");
    private By addressInput = By.id("customer.address.street");
    private By cityInput = By.id("customer.address.city");
    private By stateInput = By.id("customer.address.state");
    private By zipCodeInput = By.id("customer.address.zipCode");
    private By phoneNumberInput = By.id("customer.phoneNumber");
    private By ssnInput = By.id("customer.ssn");
    private By usernameRegInput = By.id("customer.username");
    private By passwordRegInput = By.id("customer.password");
    private By repeatedPasswordInput = By.id("repeatedPassword");
    private By registerButton = By.className("button");
    private By homeLink2 = By.partialLinkText("Home");
    private By aboutUsLink2 = By.partialLinkText("About Us");
    private By servicesLink2 = By.partialLinkText("Services");
    private By productsLink2 = By.partialLinkText("Products");
    private By locationsLink2 = By.partialLinkText("Locations");
    private By forumLink = By.partialLinkText("Forum");
    private By siteMapLink = By.partialLinkText("Site Map");
    private By contactUsLink = By.partialLinkText("Contact Us");
    private By parasoftLink = By.partialLinkText("www.parasoft.com");

    public void clickAdminLink() {
        click(adminLink);
    }

    public void clickLogoLink() {
        click(logoLink);
    }

    public void clickAboutUsLink() {
        click(aboutUsLink);
    }

    public void clickServicesLink() {
        click(servicesLink);
    }

    public void clickProductsLink() {
        click(productsLink);
    }

    public void clickLocationsLink() {
        click(locationsLink);
    }

    public void clickAdminPageLink() {
        click(adminPageLink);
    }

    public void clickHomeLink() {
        click(homeLink);
    }

    public void clickAboutLink() {
        click(aboutLink);
    }

    public void clickContactLink() {
        click(contactLink);
    }

    public void enterUsername(String username) {
        clearAndEnterText(usernameInput, username);
    }

    public void enterPassword(String password) {
        clearAndEnterText(passwordInput, password);
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public void clickForgotLoginLink() {
        click(forgotLoginLink);
    }

    public void clickRegisterLink() {
        click(registerLink);
    }

    public void enterFirstName(String firstName) {
        clearAndEnterText(firstNameInput, firstName);
    }

    public void enterLastName(String lastName) {
        clearAndEnterText(lastNameInput, lastName);
    }

    public void enterAddress(String address) {
        clearAndEnterText(addressInput, address);
    }

    public void enterCity(String city) {
        clearAndEnterText(cityInput, city);
    }

    public void enterState(String state) {
        clearAndEnterText(stateInput, state);
    }

    public void enterZipCode(String zipCode) {
        clearAndEnterText(zipCodeInput, zipCode);
    }

    public void enterPhoneNumber(String phoneNumber) {
        clearAndEnterText(phoneNumberInput, phoneNumber);
    }

    public void enterSSN(String ssn) {
        clearAndEnterText(ssnInput, ssn);
    }

    public void enterUsernameReg(String username) {
        clearAndEnterText(usernameRegInput, username);
    }

    public void enterPasswordReg(String password) {
        clearAndEnterText(passwordRegInput, password);
    }

    public void enterRepeatedPassword(String password) {
        clearAndEnterText(repeatedPasswordInput, password);
    }

    public void clickRegisterButton() {
        click(registerButton);
    }

    public void clickHomeLink2() {
        click(homeLink2);
    }

    public void clickAboutUsLink2() {
        click(aboutUsLink2);
    }

    public void clickServicesLink2() {
        click(servicesLink2);
    }

    public void clickProductsLink2() {
        click(productsLink2);
    }

    public void clickLocationsLink2() {
        click(locationsLink2);
    }

    public void clickForumLink() {
        click(forumLink);
    }

    public void clickSiteMapLink() {
        click(siteMapLink);
    }

    public void clickContactUsLink() {
        click(contactUsLink);
    }

    public void clickParasoftLink() {
        click(parasoftLink);
    }
}
