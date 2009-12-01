package com.viadee.acceptancetests.lib.impl.selenium;

import com.thoughtworks.selenium.Selenium;
import com.viadee.acceptancetests.lib.api.AbstractCondition;
import com.viadee.acceptancetests.lib.api.AbstractLink;


public abstract class SeleniumLink extends AbstractLink {

  private Selenium _selenium;

  public SeleniumLink(Selenium selenium) {
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
