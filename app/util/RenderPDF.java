package util;

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
import play.mvc.Security;


/**
 * Provides functionality for rendering an .pdf
 *
 */
@Security.Authenticated(Secured.class)
public class RenderPDF {
	
	/**
	 * 
	 * @param content the content to add to pdf
	 * @param name name of pdf to render
	 * @return File with specified content and name
	 */
	public static File renderPDFfile(Content content, String name) {
		try {
			File pdfFile = File.createTempFile(name, "pdf", new File(File.separator + "tmp"));
			FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
			DocumentBuilder documentBuilder = DocumentBuilderFactory
					.newInstance().newDocumentBuilder();
			Document document = documentBuilder.parse(new StringBufferInputStream(content.body()));

			ITextRenderer iTextRenderer = new ITextRenderer();
			iTextRenderer.setDocument(document, null);
			iTextRenderer.layout();
			iTextRenderer.createPDF(fileOutputStream);

			fileOutputStream.close();
						
			return pdfFile;

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
		
		return null;
	}
}


