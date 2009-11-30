package com.viadee.acceptancetests.lib.impl.selenium;

import com.thoughtworks.selenium.Selenium;


public class SeleniumLinkWithCaption extends SeleniumLink {

  private String _caption;

  public SeleniumLinkWithCaption(Selenium selenium, String caption) {
    super(selenium);
    
    _caption = caption;
  }

  @Override
  public String toString() {
    return String.format("//a[contains(text(), \"%s\")]", _caption);
  }

}
