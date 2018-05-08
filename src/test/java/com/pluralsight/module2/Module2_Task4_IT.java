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

public class Module2_Task4_IT {
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
		// In this test check the form input fields have values filled in
    @Test
	  public void module2_task4() {
      clickLink("Edit");
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

			inputFields_correct(urlStr);
    }

	  public void inputFields_correct(String urlStr) {
			// Get form and check Input Fields
			HtmlForm form = nextPage.getFormByName(BOOK_FORM_NAME);

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
