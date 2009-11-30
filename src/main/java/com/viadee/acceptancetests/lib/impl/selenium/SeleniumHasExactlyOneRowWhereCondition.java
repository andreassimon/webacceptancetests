package com.viadee.acceptancetests.lib.impl.selenium;

import com.thoughtworks.selenium.Selenium;
import com.viadee.acceptancetests.lib.api.AbstractCondition;
import com.viadee.acceptancetests.lib.api.AbstractCriterion;


public class SeleniumHasExactlyOneRowWhereCondition extends AbstractCondition {

  private static final Integer ONE = Integer.valueOf(1);
  private Selenium _selenium;
  private AbstractCriterion[] _criteria;
  private SeleniumTable _tableContext;

  public SeleniumHasExactlyOneRowWhereCondition(Selenium selenium,
      SeleniumTable tableContext, AbstractCriterion[] criteria) {
    super();

    _selenium = selenium;
    _tableContext = tableContext;
    _criteria = criteria;
  }

  @Override
  public String failureMessage() {
    return String.format("Expected one row with criteria, found %d", actualRowCount());
  }

  @Override
  public boolean value() {
    return ONE.equals(actualRowCount());
  }

  private Number actualRowCount() {
    return _selenium.getXpathCount(tableRowSelectorWithCriteria(_criteria));
  }

  private String tableRowSelectorWithCriteria(AbstractCriterion... criteria) {
    return _tableContext.toString()
        + String.format("//tr[%s]", SeleniumTable
            .joinCriteriaUsingAnd(criteria));
  }

}
