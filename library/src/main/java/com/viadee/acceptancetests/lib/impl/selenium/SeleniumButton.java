package com.viadee.acceptancetests.lib.impl.selenium;

import com.thoughtworks.selenium.Selenium;
import com.viadee.acceptancetests.lib.api.AbstractButton;
import com.viadee.acceptancetests.lib.api.AbstractCondition;


public abstract class SeleniumButton extends AbstractButton {

  private Selenium _selenium;

  public SeleniumButton(Selenium selenium) {
    _selenium = selenium;
  }
  
  @Override
  public AbstractCondition isPresent() {
    return new IsComponentPresentCondition(_selenium, this);
  }
  
  @Override
  public AbstractCondition isNotPresent() {
    return new IsComponentNotPresentCondition(_selenium, this);
  }

  @Override
  public abstract String toString();

}
