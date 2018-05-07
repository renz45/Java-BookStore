package com.pluralsight;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;

import static org.junit.Assert.*;

import java.io.IOException;

public class Module2_Task3_IT {
	private String BOOK_FORM_NAME = "book_form";
	private String indexUrl;
	private WebClient webClient;
  HtmlPage firstPage;
	HtmlPage nextPage;

	  @Before
	  public void setUp() throws IOException {
	    indexUrl = "http://localhost:8080"; //System.getProperty("integration.base.url");
	    webClient = new WebClient();
			// Open the admin page
	    firstPage = webClient.getPage(indexUrl + "/books/admin");
	  }
	  @After
	  public void tearDown() {
	    webClient.closeAllWindows();
	  }

		// Verify they adapted the BookForm.jsp page for editing existing books
		// and adding new book
		// In this test check the form action is conditional, and the form h2
    @Test
	  public void module2_task3() {
      clickLink("Edit");
      clickLink("New");
    }

		private void clickLink(String urlStr) {
      String foundURL = "";
			String desiredUrlText = urlStr.toLowerCase();
      try {
        for (  HtmlAnchor a : firstPage.getAnchors()) {
          String href = a.getHrefAttribute();
          if (href.contains(desiredUrlText)) {
            nextPage = a.click();
            break;
          }
        }
      }
      catch (  Exception e) {}

			assertNotNull("Link " + urlStr + " did not work.", nextPage);

			h2_correct(urlStr);
    }

	  public void h2_correct(String urlStr) {
      // First check if an H2 exists with text "New Book Form"
      boolean h2Text_correct = false;
      DomNodeList< DomElement > list = nextPage.getElementsByTagName( "h2" );
			String h2Text = "";
			String desiredText = urlStr + " Book Form";
			desiredText = desiredText.replaceAll("\\s+","");
			for( DomElement domElement : list )
      {
					h2Text = domElement.getTextContent();
					h2Text = h2Text.replaceAll("\\s+","");
          if (h2Text.equals(desiredText))
            h2Text_correct = true;
      }

      assertTrue("h2 text = " + h2Text + " , desiredText = " +desiredText, h2Text_correct);

			// Get form and check action
			HtmlForm form = nextPage.getFormByName(BOOK_FORM_NAME);
			String action = form.getActionAttribute();

			if (urlStr.equals("Edit")) {
				assertEquals("Form, book_form, action not \"update\".",
										 "update", action);
			}
			else if (urlStr.equals("New")) {
				assertEquals("Form, book_form, action not \"insert\".",
										 "insert", action);
			}
	  }
}
