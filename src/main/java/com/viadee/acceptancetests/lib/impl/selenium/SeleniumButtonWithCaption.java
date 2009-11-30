package com.viadee.acceptancetests.lib.impl.selenium;

import com.thoughtworks.selenium.Selenium;


public class SeleniumButtonWithCaption extends SeleniumButton {

  private String _caption;

  protected SeleniumButtonWithCaption(Selenium selenium, String caption) {
    super(selenium);
    
    _caption = caption;
  }

  @Override
  public String toString() {
    return String.format("//input[contains(@value, \"%s\")]", _caption);
  }
  
}
