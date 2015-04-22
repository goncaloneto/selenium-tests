/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
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
package org.pentaho.ctools.cde.require;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Popup Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class PopupComponent {
  // Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // The base url to be append the relative url in test
  private static String BASE_URL;
  //Log instance
  private static Logger LOG = LogManager.getLogger( PopupComponent.class );

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + PopupComponent.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

  @Before
  public void setUpTestCase() {
    //Go to AddinReference
    DRIVER.get( BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3APopupComponent%3Apopup.wcdf/generatedContent" );

    //NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementPresence( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    PageContent
   * Description:
   *    The test case pretends validate the contents of the sample is presented
   *    in the page.
   * Steps:
   *    1. Check page content
   */
  @Test( timeout = 60000 )
  public void tc01_PageContent_DisplayContent() {
    LOG.info( "tc01_PageContent_DisplayContent" );

    /*
     * ## Step 1
     */
    String strTitle = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='Title']" ) );
    String strDescription = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='DescriptionTitle']" ) );
    String strUsage = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='Usage']" ) );
    String strUsageDesc = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='UsageDesc']/p" ) );
    String strUsageDescNote = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='UsageDesc']/i" ) );

    assertEquals( "Popup Component Reference", strTitle );
    assertEquals( "Description", strDescription );
    assertEquals( "Usage", strUsage );
    assertEquals( "There are 3 steps for defining the popup component", strUsageDesc );
    assertEquals( "Note that when you create a component with name 'popup1' CDE will generate a js variable 'named render_popup1'", strUsageDescNote );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    PopupExample1
   * Description:
   *    The test case pretends validate if the popup works as expect.
   * Steps:
   *    1. Check page contents
   *    2. Check popup display
   */
  @Test( timeout = 60000 )
  public void tc02_PopupExample1_PopupDisplay() {
    LOG.info( "tc02_PopupExample1_PopupDisplay" );

    /*
     * ## Step 1
     */
    String strExampleTitle = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='Example1Title']" ) );
    String strExampleDesc = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='Example1Desc']/p" ) );
    String strButtonName = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='example1Obj']/button" ) );

    assertEquals( "Example 1", strExampleTitle );
    assertEquals( "Button popup with the default settings. You can drag it around and close on the button", strExampleDesc );
    assertEquals( "Popup", strButtonName );

    /*
     * ## Step 2
     */
    ElementHelper.ClickJS( DRIVER, By.xpath( "//div[@id='example1Obj']/button" ) );
    ElementHelper.WaitForElementPresence( DRIVER, By.xpath( "//div[@id='popupContent1']" ) );
    //Wait for the charts load
    ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//*[3][@width>13]" ) );
    //Check text
    String textInPopup = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='text']" ) );
    assertEquals( "This will appear in the popup", textInPopup );
    //Check rect in chart
    WebElement elemRect1 = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='popupContent1']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][1]" ) );
    WebElement elemRect2 = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='popupContent1']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][2]" ) );
    WebElement elemRect3 = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='popupContent1']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][3]" ) );
    WebElement elemRect4 = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='popupContent1']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][4]" ) );
    assertNotNull( elemRect1 );
    assertNotNull( elemRect2 );
    assertNotNull( elemRect3 );
    assertNotNull( elemRect4 );
    assertEquals( "35.521816666666666", elemRect1.getAttribute( "width" ) ); //36.27305471479055
    assertEquals( "136.75265000000002", elemRect2.getAttribute( "width" ) ); //139.64478231479157
    assertEquals( "13.579274999999999", elemRect3.getAttribute( "width" ) ); //13.866458173700408
    assertEquals( "104.68426666666666", elemRect4.getAttribute( "width" ) ); //106.89819634537434
    //Check subtitles
    Actions acts = new Actions( DRIVER );
    acts.moveToElement( elemRect2 ).perform();
    String tooltipValue = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[3]/td[3]/span" ) );
    assertEquals( "49,578", tooltipValue );
    //Close popup
    ElementHelper.ClickJS( DRIVER, By.cssSelector( "a.close" ) );
    //wait for popup disappear
    ElementHelper.WaitForElementInvisibility( DRIVER, By.cssSelector( "a.close" ) );
    WebElement element = DRIVER.findElement( By.cssSelector( "a.close" ) );
    assertFalse( element.isDisplayed() );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    PopupExample2
   * Description:
   *    The test case pretends validate if the popup works as expect.
   * Steps:
   *    1. Check page contents
   *    2. Check popup display
   */
  @Test( timeout = 60000 )
  public void tc03_PopupExample2_PopupDisplay() {
    LOG.info( "tc03_PopupExample2_PopupDisplay" );

    /*
     * ## Step 1
     */
    String strExampleTitle = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='Example2Title']" ) );
    String strExampleDesc = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='Example2Desc']/p" ) );
    String strButtonName = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='example2Obj']/button" ) );

    assertEquals( "Example 2", strExampleTitle );
    assertEquals( "Button popup appearing in the west, can't be dragged and clicking anywhere outside the element will popup the button", strExampleDesc );
    assertEquals( "Popup", strButtonName );

    /*
     * ## Step 2
     */
    ElementHelper.ClickJS( DRIVER, By.xpath( "//div[@id='example2Obj']/button" ) );
    ElementHelper.WaitForElementPresence( DRIVER, By.xpath( "//div[@id='popupContent2']" ) );
    //Check text
    String textInPopup = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='popupContent2']" ) );
    assertEquals( "A simple text that can be used as a tooltip", textInPopup );
    //Close popup
    ElementHelper.ClickJS( DRIVER, By.xpath( "//div[8]/a" ) );
    //wait for popup disappear
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[8]/a" ) );

  }

  @AfterClass
  public static void tearDownClass() {
    LOG.info( "tearDown##" + PopupComponent.class.getSimpleName() );
  }
}
