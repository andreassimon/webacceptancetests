package com.viadee.acceptancetests.lib.api;

public abstract class AbstractButton implements AbstractComponent, Clickable {

  public abstract AbstractCondition isPresent();

  public abstract AbstractCondition isNotPresent();

}
