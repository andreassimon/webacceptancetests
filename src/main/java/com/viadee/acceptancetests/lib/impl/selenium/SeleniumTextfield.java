package com.viadee.acceptancetests.lib.impl.selenium;

import com.thoughtworks.selenium.Selenium;
import com.viadee.acceptancetests.lib.api.AbstractComponent;
import com.viadee.acceptancetests.lib.api.AbstractCondition;
import com.viadee.acceptancetests.lib.api.AbstractTextfield;


public class SeleniumTextfield extends AbstractTextfield {

  private Selenium _selenium;
  private String _textfieldLabel;

  public SeleniumTextfield(Selenium selenium, String textfieldLabel) {
    super();
    _selenium = selenium;
    _textfieldLabel = textfieldLabel;
  }

  @Override
  public String toString() {
    String textfieldId = _selenium.getValue(String.format(
        "//label[contains(text(), \"%s\")]/@for", _textfieldLabel));
    String textfieldLocator = String.format("//input[@id=\"%s\"]", textfieldId);
    return textfieldLocator;
  }

  @Override
  public AbstractCondition isPresent() {
    return new IsComponentPresentCondition(_selenium, this);
  }

  @Override
  public AbstractCondition isNotPresent() {
    return new IsComponentNotPresentCondition(_selenium, this);
  }

}
