package com.viadee.acceptancetests.lib.api;


public abstract class AbstractTable implements AbstractComponent  {

  public abstract AbstractCondition isNotPresent();

  public abstract AbstractCondition isPresent();

  public abstract AbstractCondition hasExactlyOneRowWhere(AbstractCriterion... criteria);

  public abstract AbstractCondition isEmpty();

  public abstract AbstractCondition isNotEmpty();

}