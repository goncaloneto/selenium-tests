/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2014 by Pentaho : http://www.pentaho.com
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
package org.pentaho.ctools.issues.cde;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-403
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-991
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE403 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE403.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE403.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Focus goes to opened input field
   *
   * Description:
   *    The test pretends validate the CDE-403 issue, so when user opens an
   *    input field the focus goes automatically to it.
   *
   * Steps:
   *    testtest
   */
  @Test(timeout = 120000)
  public void tc01_CdeDashboard_InputFieldFocus() {
    log.info("tc01_CdeDashboard_InputFieldFocus");

    //Go to New CDE Dashboard
    driver.get(baseUrl + "api/repos/wcdf/new");

    /*
     * ## Step 1
     */
    //Assert elements on page and go to Datasources Panel
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='datasourcesPanelButton']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("previewButton"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='layoutPanelButton']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='componentsPanelButton']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@class='datasourcesPanelButton']"));

    /*
     * ## Step 2
     */
    //Add MDX query element and click Parameters
    ElementHelper.ClickElementInvisible(driver, By.xpath("//a[@title='denormalizedMdx over mondrianJdbc']"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//td[@title='Query to be executed in the selected datasource']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//table[@id='table-cdfdd-datasources-properties']/tbody/tr[8]/td[2]/div/button"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//table[@id='table-cdfdd-datasources-properties']/tbody/tr[8]/td[2]/div/button"));

    /*
     * ## Step 3
     */
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("wizardDialogBody"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("wizardEditor"));
    assertNotNull(element);
    Robot robot;
    try {
      robot = new Robot();
      robot.keyPress(KeyEvent.VK_T);
      robot.keyRelease(KeyEvent.VK_T);
      robot.keyPress(KeyEvent.VK_E);
      robot.keyRelease(KeyEvent.VK_E);
      robot.keyPress(KeyEvent.VK_S);
      robot.keyRelease(KeyEvent.VK_S);
      robot.keyPress(KeyEvent.VK_T);
      robot.keyRelease(KeyEvent.VK_T);
    } catch (AWTException e) {
      e.printStackTrace();
    }

    /*
     * ## Step 4
     */
    String text = ElementHelper.WaitForTextPresence(driver, By.xpath("//div[@id='wizardDialogCenterSection']/div/div/pre/div[2]/div/div[3]/div"), "testselect {} ON COLUMNS,");
    assertEquals("testselect {} ON COLUMNS,", text);
  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE403.class.getSimpleName());
  }
}
