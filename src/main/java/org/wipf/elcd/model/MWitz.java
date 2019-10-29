package org.wipf.elcd.model;

import java.net.URLEncoder;

import com.mashape.unirest.http.Unirest;

/**
 * @author wipf
 *
 */
public class MWitz {

	/**
	 * @return
	 */
	public static String getWitz() {
		try {
			String xml = Unirest.get("http://witze.net/witze.rss?cfg=000000410").asString().getBody();
			// return URLEncoder.encode(parse(xml), "UTF-8");
			return URLEncoder.encode(xml, "UTF-8");

		} catch (Exception e) {
			MLogger.warn("Witzfehler " + e);
		}
		return null;
	}

//	private static String parse(String xml) {
//
//		XmlMapper xmlMapper = new XmlMapper();
//		Witz w = xmlMapper.readValue(xml, Witz.class);
//		assertTrue(value.getX() == 1 && value.getY() == 2);
//
//	}

//	/**
//	 * @param sWitz
//	 * @return
//	 */
//	public static String parse(String sXMLWitz) {
//		try {
////			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
//
//			Document doc = convertStringToXMLDocument(sXMLWitz);
//
//			XMLReader stax = doc;
//			StringBuffer sb = new StringBuffer();
//			int state = 0;
//			while (stax.hasNext()) {
//				stax.next();
//				String name = (stax.hasName()) ? stax.getName().getLocalPart().trim() : null;
//				String text = (stax.hasText()) ? stax.getText().trim() : null;
//				boolean b1 = stax.hasName() && name.equals("rss"); // <ParentElem>
//				boolean b2 = stax.hasName() && name.equals("channel"); // <ChildElem>
//				boolean b3 = stax.hasText() && text.equals("item"); // <FindText>
//				boolean b4 = stax.hasName() && name.equals("description"); // <DataElem>
//				if (b1 && stax.isStartElement())
//					state = 1; // <ParentElem>
//				if (b1 && stax.isEndElement())
//					state = 0;
//				if (state == 1 && b2 && stax.isStartElement())
//					state = 2; // <ChildElem>
//				if (state == 2 && b2 && stax.isEndElement())
//					state = 1;
//				if (state == 2 && b3)
//					state = 3; // <FindText>
//				if (state == 3 && b4 && stax.isStartElement())
//					state = 4; // <DataElem>
//				if (state == 4 && b4 && stax.isEndElement())
//					break;
//				if (state == 4 && stax.hasText())
//					sb.append(stax.getText()); // gesuchtes Ergebnis
//			}
//			System.out.println(sb);
//			return sb.toString();
//		} catch (Exception e) {
//			MLogger.warn("witzParse " + e);
//			return "FAIL";
//		}
//	}
//
//	private static Document convertStringToXMLDocument(String xmlString) {
//		// Parser that produces DOM object trees from XML content
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//
//		// API to obtain DOM Document instance
//		DocumentBuilder builder = null;
//		try {
//			// Create DocumentBuilder with default configuration
//			builder = factory.newDocumentBuilder();
//
//			// Parse the content to Document object
//			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
//			return doc;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}
