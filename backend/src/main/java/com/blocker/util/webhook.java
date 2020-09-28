package com.blocker.util;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import net.bis5.mattermost.client4.hook.IncomingWebhookClient;
import net.bis5.mattermost.model.IncomingWebhookRequest;
import net.bis5.mattermost.model.SlackAttachment;
import net.minidev.json.JSONObject;

public class webhook {
		
		private final static String _WEBHOOK_URL = "https://meeting.ssafy.com/hooks/gnj1aqbr6b8j3qqotky3zb1hiw";
		
		public void send(String text) {
			IncomingWebhookClient client = new IncomingWebhookClient(_WEBHOOK_URL);
			IncomingWebhookRequest payload = new IncomingWebhookRequest();
			payload.setText("@wnw1102 \n");
			payload.setUsername("Override Username");
			SlackAttachment sa = new SlackAttachment();
			sa.setColor("#36a64f");
			sa.setText(""+text);
			sa.setAuthorName("Backend Exception!");
			sa.setAuthorIcon("https://www.epicentrofestival.com/wp-content/uploads/2019/12/Front-Facing-Baby-Chick-Icon-Icon.jpg");
			List<SlackAttachment> li = new ArrayList<>();
			li.add(sa);
			payload.setAttachments(li);
			client.postByIncomingWebhook(payload);
		}
//		public void postHttpsRequest(String cause, String s) {
//			try {
//				URL url = new URL("https://meeting.ssafy.com/hooks/gnj1aqbr6b8j3qqotky3zb1hiw");
//				HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
//				con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
//				con.setRequestProperty("Content-Type", "application/json");
//				con.setRequestProperty("charset", "UTF-8");
//				con.setDoOutput(true);
//				con.setConnectTimeout(5000);
//				con.setReadTimeout(5000);
//				con.setRequestMethod("POST");
//				
//				JSONObject obj = new JSONObject(), 
//				props = new JSONObject();
////				obj.put("username","Backend Exception!");
////				obj.put("icon_url","https://www.epicentrofestival.com/wp-content/uploads/2019/12/Front-Facing-Baby-Chick-Icon-Icon.jpg");
//				obj.put("text", s + "\n(click me) ");
//				props.put("card", cause);
//				obj.put("props", props);
//				System.out.println(obj);
//				OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
//				os.write(obj.toString());
//				os.flush();
//				os.close();
//				con.getInputStream();
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
}
