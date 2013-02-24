package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringBufferInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.SAXException;

import com.lowagie.text.DocumentException;

import controllers.Secured;
import play.mvc.Content;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


@Security.Authenticated(Secured.class)
public class RenderPDF extends Controller {
	public static Result renderPDFfile(Content content, String filename) {
		try {
			File pdfFile = new File(filename);
			FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
			DocumentBuilder documentBuilder = DocumentBuilderFactory
					.newInstance().newDocumentBuilder();
			Document document = documentBuilder.parse(new StringBufferInputStream(content.body()));

			ITextRenderer iTextRenderer = new ITextRenderer();
			iTextRenderer.setDocument(document, null);
			iTextRenderer.layout();
			iTextRenderer.createPDF(fileOutputStream);

			fileOutputStream.close();

			response().setContentType("application/pdf ");
			
			return ok(new FileInputStream(pdfFile));

		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			System.out.println("ParserConfigurationException");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("DocumentException");
			e.printStackTrace();
		} catch (SAXException e) {
			System.out.println("SAXException");
			e.printStackTrace();
		}
		
		return internalServerError();
	}
}


