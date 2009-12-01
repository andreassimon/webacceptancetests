package com.viadee.acceptancetests.lib.api;

public abstract class AbstractLink implements AbstractComponent, Clickable {

  public abstract AbstractCondition isPresent();

  public abstract AbstractCondition isNotPresent();

}
