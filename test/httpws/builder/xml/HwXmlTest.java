package httpws.builder.xml;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HwXmlTest extends HwXml {
	
	@Test
	public void testSimple() {
		$("html", () -> {
			$("head", () -> {
			});
			$("body", () -> {
			});
		});
		assertEquals("<html><head></head><body></body></html>", $eof().toString());
	}
	
	@Test
	public void testBlockAttrs() {
		$("html", () -> {
		} , $attr("id", 1), $attr("class", "my"));
		assertEquals("<html id='1' class='my'></html>", $eof().toString());
	}
	
	@Test
	public void testSimpleAttrs() {
		$("html", $attr("id", 1), $attr("class", "my"));
		assertEquals("<html id='1' class='my'></html>", $eof().toString());
	}
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		InputStream in = HwXmlTest.class.getClassLoader().getResource("httpws/resource/xml/mime.xml").openStream();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setIgnoringComments(true);
		dbf.setIgnoringElementContentWhitespace(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(in);
		NodeList childNodes = ((Element) doc.getFirstChild()).getChildNodes();
		for (int n = 0; n < childNodes.getLength(); n++) {
			Node node = childNodes.item(n);
			if (node.getNodeName().toLowerCase().equals("mime-mapping")) {
				String extension = null, value = null;
				NodeList childNodeNodes = node.getChildNodes();
				for (int m = 0; m < childNodeNodes.getLength(); m++) {
					Node child = childNodeNodes.item(m);
					if (child.getNodeName().toLowerCase().equals("extension")) {
						extension = child.getFirstChild().getNodeValue().toLowerCase().trim();
					} else if (child.getNodeName().toLowerCase().equals("mime-type")) {
						value = child.getFirstChild().getNodeValue().toLowerCase().trim();
					}
				}
				if (extension != null && value != null) {
					System.out.println(String.format("$(\"mime\", $attr(\"extension\", \"%s\"), $attr(\"type\", \"%s\"));", extension, value));
				}
			}
		}
	}
	
}
