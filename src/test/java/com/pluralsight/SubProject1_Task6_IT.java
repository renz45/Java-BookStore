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

public class SubProject1_Task6_IT {
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

    @Test
	  public void task1() {
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

			// If it's Edit Form, there's more to check
			if (urlStr.equals("Edit")) {
				//Get id input field
				try {
					HtmlInput inputId = form.getInputByName("id");

					// Check if hidden
					String typeAttribute = inputId.getTypeAttribute();
					assertEquals("The id input needs type=\"hidden\".", "hidden", typeAttribute);

					// Check value is an int
					try {
						Integer.parseInt(inputId.getValueAttribute());
					} catch (NumberFormatException e) {
						assertTrue("The id input does not have an int for value.", false);
					}
				} catch (ElementNotFoundException e) {
					assertTrue("The input field with name \"id\" does not exist.", false);
				}

				// Get title input field, check value
				try {
					HtmlInput inputTitle = form.getInputByName("booktitle");
					String titleValue = inputTitle.getValueAttribute();
					assertTrue("Title field value is empty, value is \"" + titleValue + "\".",
										 titleValue.length() > 0);
				}catch (ElementNotFoundException e) {
					assertTrue("The input field with name \"booktitle\" does not exist.", false);
				}

				// Get author input field, check value
				try {
					HtmlInput inputAuthor = form.getInputByName("bookauthor");
					String authorValue = inputAuthor.getValueAttribute();
					assertTrue("Author field value is empty, value is \"" + authorValue + "\".",
										 authorValue.length() > 0);
				}catch (ElementNotFoundException e) {
					assertTrue("The input field with name \"bookauthor\" does not exist.", false);
				}

				// Get price input field, check value
				try {
					HtmlInput inputPrice = form.getInputByName("bookprice");
					String priceValue = inputPrice.getValueAttribute();
					assertTrue("Price field value is empty, value is \"" + priceValue + "\".",
										 priceValue.length() > 0);
				}catch (ElementNotFoundException e) {
					assertTrue("The input field with name \"bookprice\" does not exist.", false);
				}
			}
	  }
}
