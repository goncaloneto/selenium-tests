/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2016 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/
package com.pentaho.ctools.issues.cde;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.gui.web.ctools.cde.CDEditor;
import com.pentaho.gui.web.ctools.cde.utils.Widgets;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-453
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1024
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDE453 extends BaseTest {
  // The widget name that we what to create
  private final String widgetName = "CDE453";
  // Indicator to check if any assert fails in the test case
  private boolean noAssertFails = false;
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE453.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert that when a new widget is created it is readily available in the components panel of CDE
   *
   * Description:
   *    The test pretends validate the CDE-453 issue, so when user creates a widget it's available in the
   *    components panel of CDE.
   *
   * Steps:
   *    1. Open New Dashboard and save as Widget
   *    2. Open New Dashboard and assert new Widget is present in components panel
   *    3. Delete newly created Widget
   *
   */
  @Test
  public void tc01_NewCDEDashboard_NewWidgetPresent() {
    this.log.info( "tc01_NewCDEDashboard_NewWidgetPresent" );

    /*
     * ## Step 1
     */
    Widgets widgets = new Widgets();
    widgets.CreateWidget( driver, this.widgetName );

    /*
     * ## Step 2
     */
    //New CDE dashboard
    CDEditor cdeEditor = new CDEditor( driver );
    cdeEditor.GoToNewCDE();
    cdeEditor.GoToComponentPanel();
    //Expand Widgets option
    this.elemHelper.ClickJS( driver, By.cssSelector( "#ui-accordion-cdfdd-components-palletePallete-header-8 > span" ) );
    //Check the widget created is visible in the list of Widgets
    WebElement widgetCDE453 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.linkText( this.widgetName ) );
    assertNotNull( widgetCDE453 );
    this.elemHelper.Click( driver, By.linkText( this.widgetName ) );
    //Check the widget was added to the list of components
    String groupName = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//tr[@id='WIDGETS']/td[2]" ) );
    assertEquals( "Widgets", groupName );
    // Check the group added is Widgets
    String displayWidgetName = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//tr[2]/td" ) );
    assertEquals( displayWidgetName, this.widgetName );

    /*
     * ## Step 3
     */
    widgets.RemoveWidgetByName( driver, this.widgetName );
    this.noAssertFails = true;
  }

  @AfterClass( alwaysRun = true )
  public void tearDownClass() {
    this.log.info( "tearDownClass" );

    if ( !this.noAssertFails ) {
      Widgets widgets = new Widgets();
      widgets.RemoveWidgetByName( driver, this.widgetName );
    }
  }
}
