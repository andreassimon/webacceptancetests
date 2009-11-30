package com.viadee.acceptancetests.lib;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;

import com.viadee.acceptancetests.lib.api.AbstractApplicationDriver;
import com.viadee.acceptancetests.lib.api.AbstractButton;
import com.viadee.acceptancetests.lib.api.AbstractCondition;
import com.viadee.acceptancetests.lib.api.AbstractCriterion;
import com.viadee.acceptancetests.lib.api.AbstractLink;
import com.viadee.acceptancetests.lib.api.AbstractTable;
import com.viadee.acceptancetests.lib.api.AbstractTextfield;
import com.viadee.acceptancetests.lib.api.Clickable;


public abstract class AbstractAcceptanceTestGroup {

  public static final String MAXIMUM_WAIT_TIME = "1000";

  public AbstractAcceptanceTestGroup() {
    super();
  }

  protected abstract AbstractApplicationDriver applicationDriver();

  @Before
  public final void setUpApplicationDriver() throws Exception {
	  applicationDriver().setUp();
  }

  @After
  public final void tearDownApplicationDriver() throws Exception {
	  applicationDriver().tearDown();
  }

  protected abstract String applicationBaseUrl();

  protected final static void assertThat(AbstractCondition abstractCondition) {
    assertTrue(abstractCondition.failureMessage(), abstractCondition.value());
  }

  public final AbstractButton buttonWithCaption(String caption) {
    return applicationDriver().buttonWithCaption(caption);
  }

  protected final AbstractButton buttonWithAltText(String altText) {
    return applicationDriver().buttonWithAltText(altText);
  }

  protected final void typeTextIntoTextfield(String text,
      AbstractTextfield textfield) {
    applicationDriver().typeTextIntoTextfield(text, textfield);
  }

  protected AbstractTextfield textfieldWithLabel(String label) {
    return applicationDriver().textfieldWithLabel(label);
  }

  protected final void clickClickableAndWaitForNextPage(Clickable clickable) {
    applicationDriver().clickClickableAndWaitForNextPage(clickable);
  }

  protected final AbstractLink linkWithCaption(String caption) {
    return applicationDriver().linkWithCaption(caption);
  }

  protected final void open(String url) {
    applicationDriver().open(url);
  }

  protected final void assertTextIsPresent(String text) {
    assertTrue(String.format("The text \"%s\" could not be found!", text),
        applicationDriver().isTextPresent(text));
  }

  protected final void waitForHalfASecond() {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      // Do nothing
    }
  }

  protected final AbstractCriterion entryInColumnEqualsValue(String columnName,
      String columnEntry) {
    return applicationDriver().entryInColumnEqualsValue(columnName, columnEntry);
  }

  protected final AbstractTable tableWithColumns(String... columnNames) {
    return applicationDriver().tableWithColumns(columnNames);
  }

}
