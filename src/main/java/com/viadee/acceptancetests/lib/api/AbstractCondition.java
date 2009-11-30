package com.viadee.acceptancetests.lib.api;

public abstract class AbstractCondition {
  
  public abstract String failureMessage();

  public abstract boolean value();

}
