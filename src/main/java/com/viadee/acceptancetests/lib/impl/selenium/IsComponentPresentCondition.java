package com.viadee.acceptancetests.lib.impl.selenium;

import com.thoughtworks.selenium.Selenium;
import com.viadee.acceptancetests.lib.api.AbstractComponent;
import com.viadee.acceptancetests.lib.api.AbstractCondition;


public class IsComponentPresentCondition extends AbstractCondition {

  private Selenium _selenium;
  private AbstractComponent _component;

  public IsComponentPresentCondition(Selenium selenium,
      AbstractComponent component) {
    _selenium = selenium;
    _component = component;
  }

  @Override
  public String failureMessage() {
    return "Component is not present!";
  }

  @Override
  public boolean value() {
    return !(_selenium.getText(_component.toString()).length() == 0);
  }

}
