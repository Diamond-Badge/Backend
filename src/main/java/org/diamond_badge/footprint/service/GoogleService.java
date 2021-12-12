package org.diamond_badge.footprint.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.naming.CommunicationException;

import org.diamond_badge.footprint.model.social.GoogleProfile;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleService {

	public GoogleProfile getGoogleUserInfo(String accessToken) throws Exception {

		String reqURL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=" + accessToken;
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			//요청에 필요한 Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);

			int responseCode = conn.getResponseCode();
			log.info("## ResponseCode : {}", responseCode);

			if (responseCode == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				String line = "";
				String result = "";
				while ((line = br.readLine()) != null) {
					result += line;
				}

				JsonParser parser = new JsonParser();
				log.info("## Result = {}", result);

				JsonElement element = parser.parse(result);
				String name = element.getAsJsonObject().get("name").getAsString();
				String email = element.getAsJsonObject().get("email").getAsString();
				String id = "GOOGLE_" + element.getAsJsonObject().get("id").getAsString();

				GoogleProfile googleProfile = GoogleProfile.builder()
					.email(email)
					.name(name)
					.id(id)
					.build();

				log.info("## Login Controller : {}", googleProfile);
				return googleProfile;
			}
		} catch (Exception e) {
			log.error(String.valueOf(e));
			throw e;
		}
		throw new CommunicationException();
	}
}
