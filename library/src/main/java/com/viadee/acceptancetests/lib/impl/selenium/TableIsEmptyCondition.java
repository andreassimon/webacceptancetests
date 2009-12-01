package com.viadee.acceptancetests.lib.impl.selenium;

import com.thoughtworks.selenium.Selenium;
import com.viadee.acceptancetests.lib.api.AbstractCondition;


public class TableIsEmptyCondition extends AbstractCondition {

  private static final Integer ZERO = Integer.valueOf(0);
  private Selenium _selenium;
  private SeleniumTable _tableContext;

  public TableIsEmptyCondition(Selenium selenium, SeleniumTable tableContext) {
    super();

    _selenium = selenium;
    _tableContext = tableContext;
  }

  @Override
  public String failureMessage() {
    return String.format("The table should be empty, but found %d non-empty rows!", actualNonEmptyRowCount());
  }

  @Override
  public boolean value() {
    return ZERO.equals(actualNonEmptyRowCount());
  }

  private Number actualNonEmptyRowCount() {
    return _selenium.getXpathCount(tableRowSelector());
  }

  private String tableRowSelector() {
    return _tableContext.toString() + "//tr[@class!=\"footer\"]";
  }

}
