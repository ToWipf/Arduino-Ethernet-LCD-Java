package org.wipf.elcd.model;

import org.wipf.elcd.model.task.TaskTelegram;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MTelegram {

	/**
	 * @param sMsg
	 * @param sChatId
	 * @return
	 */
	public static String sendeTele(String sMsg, String sChatId) {
		try {
			HttpResponse<String> res;
			res = Unirest.post("https://api.telegram.org/" + TaskTelegram.BOTKEY + "sendMessage?chat_id=" + sChatId
					+ "&text=" + sMsg).asString();
			return res.getBody();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return
	 */
	public static String leseTele() {
		try {
			return Unirest.post("https://api.telegram.org/" + TaskTelegram.BOTKEY + "getUpdates").asString().getBody();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
