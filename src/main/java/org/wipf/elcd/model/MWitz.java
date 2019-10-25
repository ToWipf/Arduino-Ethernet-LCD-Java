package org.wipf.elcd.model;

//import javax.xml.stream.XMLStreamException;
//import javax.xml.stream.XMLStreamReader;
//
//import com.mashape.unirest.http.Unirest;
//import com.mashape.unirest.http.exceptions.UnirestException;
import javax.xml.stream.XMLStreamException;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MWitz {
//

	public static void main(String[] args) {
		try {
			System.out.println(getWitz());
		} catch (XMLStreamException e) {
			MLogger.err(e);
		}
	}

	public static String getWitz() throws XMLStreamException {
		try {
			String xml = Unirest.get("http://witze.net/witze.rss?cfg=000000410").asString().getBody();
			return parse(xml);

		} catch (UnirestException e) {
			MLogger.err("Witzfehler " + e);
		}
		return null;
	}

	private static String parse(String xml) {
		// TODO Auto-generated method stub
		return null;
	}
}
//	public static String parse(String sWitz) throws XMLStreamException {
//
//		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
//		XMLStreamReader stax = (XMLStreamReader) sWitz;
//		StringBuffer sb = new StringBuffer();
//		int state = 0;
//		while (stax.hasNext()) {
//			stax.next();
//			String name = (stax.hasName()) ? stax.getName().getLocalPart().trim() : null;
//			String text = (stax.hasText()) ? stax.getText().trim() : null;
//			boolean b1 = stax.hasName() && name.equals("rss"); // <ParentElem>
//			boolean b2 = stax.hasName() && name.equals("channel"); // <ChildElem>
//			boolean b3 = stax.hasText() && text.equals("item"); // <FindText>
//			boolean b4 = stax.hasName() && name.equals("description"); // <DataElem>
//			if (b1 && stax.isStartElement())
//				state = 1; // <ParentElem>
//			if (b1 && stax.isEndElement())
//				state = 0;
//			if (state == 1 && b2 && stax.isStartElement())
//				state = 2; // <ChildElem>
//			if (state == 2 && b2 && stax.isEndElement())
//				state = 1;
//			if (state == 2 && b3)
//				state = 3; // <FindText>
//			if (state == 3 && b4 && stax.isStartElement())
//				state = 4; // <DataElem>
//			if (state == 4 && b4 && stax.isEndElement())
//				break;
//			if (state == 4 && stax.hasText())
//				sb.append(stax.getText()); // gesuchtes Ergebnis
//		}
//		System.out.println(sb);
//		return sWitz;
//	}
//}
