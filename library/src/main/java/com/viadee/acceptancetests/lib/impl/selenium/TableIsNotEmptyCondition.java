package com.viadee.acceptancetests.lib.impl.selenium;

import com.thoughtworks.selenium.Selenium;

public class TableIsNotEmptyCondition extends TableIsEmptyCondition {

  public TableIsNotEmptyCondition(Selenium selenium, SeleniumTable tableContext) {
    super(selenium, tableContext);
  }

  @Override
  public String failureMessage() {
    return "The table should not be empty";
  }

  @Override
  public boolean value() {
    return !super.value();
  }

}
