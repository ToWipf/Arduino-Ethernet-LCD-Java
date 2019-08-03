package wipf.elcd;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class dlcd {

	public static void testRest() {
		HttpResponse<String> response;
		try {
			response = Unirest.get("http://192.168.2.242/testText").asString();
			System.out.println(response.getBody());
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void goToLine(Integer n) {
		try {
			Unirest.get("http://192.168.2.242/gL" + n).asString();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void clear() {
		try {
			Unirest.get("http://192.168.2.242/cls").asString();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void text(String s) {
		try {
			Unirest.get("http://192.168.2.242/" + s).asString();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
