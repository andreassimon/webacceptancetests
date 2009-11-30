package com.viadee.acceptancetests.lib.impl.selenium;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.viadee.acceptancetests.lib.AbstractAcceptanceTestGroup;
import com.viadee.acceptancetests.lib.api.AbstractApplicationDriver;
import com.viadee.acceptancetests.lib.api.AbstractButton;
import com.viadee.acceptancetests.lib.api.AbstractComponent;
import com.viadee.acceptancetests.lib.api.AbstractCriterion;
import com.viadee.acceptancetests.lib.api.AbstractLink;
import com.viadee.acceptancetests.lib.api.AbstractTable;
import com.viadee.acceptancetests.lib.api.AbstractTextfield;
import com.viadee.acceptancetests.lib.api.Clickable;


public class SeleniumApplicationDriver extends AbstractApplicationDriver {

  private String _applicationBaseUrl;
  protected Selenium _selenium;

  public SeleniumApplicationDriver(String applicationBaseUrl) {
    _applicationBaseUrl = applicationBaseUrl;
  }

  public void setUp() {
    _selenium = new DefaultSelenium("localhost", 4444, "*iexplore",
        _applicationBaseUrl);
    _selenium.start();
  }

  public void tearDown() {
    _selenium.stop();
  }

  public final AbstractButton buttonWithCaption(String caption) {
    return new SeleniumButtonWithCaption(_selenium, caption); 
  }

  public AbstractButton buttonWithAltText(String altText) {
    return new SeleniumButtonWithAltText(_selenium, altText);
  }

  public void typeTextIntoTextfield(String text, AbstractTextfield textfield) {
    _selenium.type(textfield.toString(), text);
  }

  public void clickClickableAndWaitForNextPage(Clickable clickable) {
    _selenium.click(clickable.toString());
    _selenium.waitForPageToLoad(AbstractAcceptanceTestGroup.MAXIMUM_WAIT_TIME);
  }

  public AbstractLink linkWithCaption(String caption) {
    return new SeleniumLinkWithCaption(_selenium, caption); 
  }

  public void open(String url) {
    _selenium.open(url);
  }

  public boolean isTextPresent(String text) {
    return _selenium.isElementPresent(String.format("//*[contains(text(), \"%s\")]", text));
  }

  public AbstractCriterion entryInColumnEqualsValue(String columnName,
      String columnEntry) {
    return new EntryInColumnEqualsValueCriterion(_selenium,
        columnName, columnEntry);
  }

  public AbstractTable tableWithColumns(String... spaltenUeberschriften) {
    return new SeleniumTable(_selenium, spaltenUeberschriften);
  }

  @Override
  public AbstractTextfield textfieldWithLabel(String label) {
    return new SeleniumTextfield(_selenium, label);
  }

}
