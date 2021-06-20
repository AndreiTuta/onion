package com.andreituta.onion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andreituta.onion.client.PasswordClient;
import com.andreituta.onion.model.response.PasswordResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/v1/password")
@Slf4j
@RequiredArgsConstructor
public class PasswordController {

	private final PasswordClient passClient;

	@GetMapping(value = "/{strong}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PasswordResponse> getPassword(@PathVariable String strong) {
		log.info("Fetching new password...");
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(passClient.fetchNewPassword(StringUtils.pathEquals(strong, "strong")));
	}
}
