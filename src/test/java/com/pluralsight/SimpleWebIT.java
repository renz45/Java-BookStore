package com.pluralsight;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import static org.junit.Assert.*;

import java.io.IOException;

public class SimpleWebIT {

	private String indexUrl;
	private WebClient webClient;

	@Before
	  public void setUp() {
	    indexUrl = "http://localhost:8080"; //System.getProperty("integration.base.url");
	    webClient = new WebClient();
	  }
	  @After
	  public void tearDown() {
	    webClient.closeAllWindows();
	  }

	  @Test
	  public void your_name_can_be_submitted() throws IOException {

	    // Open the home page
	    HtmlPage page = webClient.getPage(indexUrl);

	    String responseUrl = "";
		try {
			// Find the relevant form by name
			HtmlForm form = page.getFormByName("SubmitYourName");

			// Fill in your name
			HtmlTextInput textField = form.getInputByName("name");
			textField.setValueAttribute("Test Name");

			// Submit the form and assert the response
			HtmlSubmitInput button = form.getInputByValue("OK");

			WebResponse response = button.click().getWebResponse();
			responseUrl = response.getWebRequest().getUrl().toString();
		} catch (ElementNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    assertEquals("The form field name was not set.",
	    		(indexUrl + "/response.jsp?name=Test+Name"), responseUrl);
	  }

	  @Test
	  public void the_submitted_name_is_displayed() throws IOException {
	    final String testName = "MyName";
	    HtmlPage page = webClient.getPage(indexUrl + "/response.jsp?name=" + testName);
	    assertTrue("The submitted name was not displayed.", page.asXml().contains("Hello, " + testName + "!"));
	  }
}
