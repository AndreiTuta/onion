package com.andreituta.onion.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.andreituta.onion.model.response.PasswordResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordClient {
	@Autowired
	private RestTemplate template;
	final String DINOPASS_ENDPOINT = "https://www.dinopass.com/password/";

	private PasswordResponse buildPasswordResponse(final String password) {
		return PasswordResponse.builder().id(java.util.UUID.randomUUID()).password(password).build();
	}

	public PasswordResponse fetchNewPassword(final boolean strongPass) {
		PasswordResponse resp = null;
		String passType = strongPass ? "strong" : "simple";
		log.info("Making a call to Dinopass for a {} password", passType);
		ResponseEntity<String> ex = template.getForEntity(DINOPASS_ENDPOINT + passType, String.class);
		if (ex.getStatusCode() == HttpStatus.OK) {
			resp = buildPasswordResponse(ex.getBody());
			log.info("Fetched new {} password from Dinopass: {}", passType, resp.getPassword());
		}
		return resp;
	}

}
