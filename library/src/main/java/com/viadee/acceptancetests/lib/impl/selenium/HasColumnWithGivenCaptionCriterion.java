package com.viadee.acceptancetests.lib.impl.selenium;

import com.viadee.acceptancetests.lib.api.AbstractCriterion;

public final class HasColumnWithGivenCaptionCriterion extends AbstractCriterion {

  private String _caption;

  protected HasColumnWithGivenCaptionCriterion(String caption) {
    _caption = caption;
  }

  @Override
  public String toString() {
    return String.format("//th[text()=\"%s\"]", _caption);
  }

}