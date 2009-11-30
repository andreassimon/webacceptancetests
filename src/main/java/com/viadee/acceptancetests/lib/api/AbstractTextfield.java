package com.viadee.acceptancetests.lib.api;

public abstract class AbstractTextfield implements AbstractComponent {

  public abstract AbstractCondition isNotPresent();

  public abstract AbstractCondition isPresent();

}
