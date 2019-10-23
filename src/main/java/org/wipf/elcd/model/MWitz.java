package org.wipf.elcd.model;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MWitz {
//

	public static void main(String[] args) {
		System.out.println(getWitz());
	}

	public static String getWitz() {
		try {
			String xml = Unirest.get("http://witze.net/witze.rss?cfg=000000410").asString().getBody();
			return parse(xml);

		} catch (UnirestException e) {
			System.out.println("Witzfehler");
			return null;
		}
	}

	public static String parse(String sWitzXml) {
		System.out.println(sWitzXml);

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(sWitzXml));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("channel");

			// String sWitz = doc.getElementsByTagName("channel").toString();

			// return sWitz;

			// iterate the employees
			for (int i = 0; i < nodes.getLength(); i++) {
				System.out.println("1");
				Element element = (Element) nodes.item(i);

				NodeList name = element.getElementsByTagName("channel");
				Element line = (Element) name.item(0);
				System.out.println("Witz: " + getCharacterDataFromElement(line));
				// return ("x");

//				NodeList title = element.getElementsByTagName("title");
//				line = (Element) title.item(0);
//				System.out.println("Title: " + getCharacterDataFromElement(line));
			}
			return "e";
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * output : Name: John Title: Manager Name: Sara Title: Clerk
		 */
		return null;

	}

	public static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "?";
	}

}
