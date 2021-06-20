/**
 * 
 */
package com.andreituta.onion.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author andrei.tuta
 *
 */
@ExtendWith(MockitoExtension.class)
class PasswordClientTest {
	@Mock
	private RestTemplate templateMock;
	@Mock
	private ResponseEntity<Object> mockResponse;
	@InjectMocks
	private PasswordClient client;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		when(templateMock.getForEntity(anyString(), any())).thenReturn(null).thenReturn(mockResponse);
		when(mockResponse.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND).thenReturn(HttpStatus.OK, HttpStatus.OK);
		when(mockResponse.getBody()).thenReturn("simple-pass", "strong-pass");
	}

	/**
	 * Test method for
	 * {@link com.andreituta.onion.client.PasswordClient#fetchNewPassword(boolean)}.
	 */
	@Test
	void testFetchNewPassword() {
		// null resp
		assertNull(client.fetchNewPassword(true));
		// status code 404
		assertNull(client.fetchNewPassword(true));
		// 200 with simple and strong pass
		assertNotNull(client.fetchNewPassword(false));
		assertNotNull(client.fetchNewPassword(true));
	}
}
