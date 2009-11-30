package com.viadee.acceptancetests.lib.impl.selenium;

import com.thoughtworks.selenium.Selenium;
import com.viadee.acceptancetests.lib.api.AbstractComponent;


public class IsComponentNotPresentCondition extends IsComponentPresentCondition {

  public IsComponentNotPresentCondition(Selenium selenium,
      AbstractComponent component) {
    super(selenium, component);
  }

  @Override
  public String failureMessage() {
    return "Component is present, but should not be!";
  }

  @Override
  public boolean value() {
    return !super.value();
  }

}
