package com.viadee.acceptancetests.lib.impl.selenium;

import com.thoughtworks.selenium.Selenium;


public class SeleniumButtonWithAltText extends SeleniumButton {

  private String _altText;

  protected SeleniumButtonWithAltText(Selenium selenium, String altText) {
    super(selenium);

    _altText = altText;
  }

  @Override
  public String toString() {
    return String.format("//input[contains(@alt, \"%s\")]", _altText);
  }

}
