package com.viadee.acceptancetests.lib.api;


public abstract class AbstractApplicationDriver {
  public abstract void setUp();

  public abstract void tearDown();

  public abstract AbstractButton buttonWithCaption(String caption);

  public abstract AbstractButton buttonWithAltText(String altText);

  public abstract void typeTextIntoTextfield(String text,
      AbstractTextfield textfield);

  public abstract void clickClickableAndWaitForNextPage(Clickable clickable);

  public abstract AbstractLink linkWithCaption(String caption);

  public abstract void open(String url);

  public abstract boolean isTextPresent(String text);

  public abstract AbstractCriterion entryInColumnEqualsValue(String columnName,
      String columnEntry);

  public abstract AbstractTable tableWithColumns(String... columnHeaders);

  public abstract AbstractTextfield textfieldWithLabel(String label);

}
