package com.viadee.acceptancetests.lib.api;

public interface AbstractComponent {

  public abstract AbstractCondition isPresent();

  public abstract AbstractCondition isNotPresent();

}
