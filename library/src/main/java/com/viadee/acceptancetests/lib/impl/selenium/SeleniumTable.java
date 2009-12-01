package com.viadee.acceptancetests.lib.impl.selenium;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.selenium.Selenium;
import com.viadee.acceptancetests.lib.api.AbstractCondition;
import com.viadee.acceptancetests.lib.api.AbstractCriterion;
import com.viadee.acceptancetests.lib.api.AbstractTable;


public final class SeleniumTable extends AbstractTable {

  private Selenium _selenium;
  private AbstractCriterion[] _hasColumnWithGivenCaptionCriteria;
  private String[] _columnCaptions;

  protected SeleniumTable(Selenium selenium, String[] columnCaptions) {
    _selenium = selenium;
    _columnCaptions = columnCaptions;
  }

  @Override
  public AbstractCondition isPresent() {
    return new IsComponentPresentCondition(_selenium, this);
  }
  
  @Override
  public AbstractCondition isNotPresent() {
    return new IsComponentNotPresentCondition(_selenium, this);
  }

  /*
   * (non-Javadoc)
   * 
   * @seede.viadee.lib.acceptancetests.impl.selenium.AbstractTableModel#
   * hasExactlyOneRowWhere(de.viadee.lib.acceptancetests.api.AbstractCriterion)
   */
  public AbstractCondition hasExactlyOneRowWhere(AbstractCriterion... criteria) {
    return new SeleniumHasExactlyOneRowWhereCondition(_selenium, this, criteria);
  }

  @Override
  public String toString() {
    return String.format("//table[%s]",
        joinCriteriaUsingAnd(hasColumnWithGivenCaptionCriteria()));
  }

  private AbstractCriterion[] hasColumnWithGivenCaptionCriteria() {
    if (_hasColumnWithGivenCaptionCriteria == null) {
      List<HasColumnWithGivenCaptionCriterion> hasColumnWithGivenCaptionCriteriaList = new ArrayList<HasColumnWithGivenCaptionCriterion>(
          _columnCaptions.length);
      for (String columnCaption : _columnCaptions) {
        hasColumnWithGivenCaptionCriteriaList
            .add(new HasColumnWithGivenCaptionCriterion(columnCaption));
      }
      _hasColumnWithGivenCaptionCriteria = hasColumnWithGivenCaptionCriteriaList
          .toArray(new AbstractCriterion[0]);
    }
    return _hasColumnWithGivenCaptionCriteria;
  }

  public static String joinCriteriaUsingAnd(AbstractCriterion... criteria) {
    String result = "";
    for (AbstractCriterion criterion : criteria) {
      result += " and " + criterion.toString();
    }
    if ((result.length() == 0)) {
      return result;
    } else {
      return result.substring(5);
    }
  }

  @Override
  public AbstractCondition isEmpty() {
    return new TableIsEmptyCondition(_selenium, this);
  }

  @Override
  public AbstractCondition isNotEmpty() {
    return new TableIsNotEmptyCondition(_selenium, this);
  }

}