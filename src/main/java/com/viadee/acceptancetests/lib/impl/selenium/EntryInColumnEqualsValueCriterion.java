package com.viadee.acceptancetests.lib.impl.selenium;

import com.thoughtworks.selenium.Selenium;
import com.viadee.acceptancetests.lib.api.AbstractCriterion;


public final class EntryInColumnEqualsValueCriterion extends AbstractCriterion {

  private String _columnName;
  private String _columnEntry;
  private Selenium _selenium;
  private int _columnIndex;

  protected EntryInColumnEqualsValueCriterion(Selenium selenium,
      String columnName, String columnEntry) {
    _selenium = selenium;
    _columnName = columnName;
    _columnEntry = columnEntry;
  }

  @Override
  public String toString() {
    return String.format("td[position()=%s and text()=\"%s\"]", columnIndex(),
        _columnEntry);
  }

  private int columnIndex() {
    if (_columnIndex == 0) {
      for (int i = 1;; i++) {
        String columnHeader = _selenium.getText(String.format("//tr[th]/th[%d]", i));
        if (_columnName.equals(columnHeader)) {
          _columnIndex = i;
          break;
        }
      }
    }
    return _columnIndex;
  }

}