/**
 * @author Paulius Staisiunas
 */

package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.Constants;
import com.j2020.model.TokenRenewalResponse;
import com.j2020.model.revolut.RevolutTokenRenewalResponse;
import com.j2020.service.TokenRequestRetrievalService;
import helper.TestDataHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenRequestServiceTest {
    private TokenRequestRetrievalService tokenService;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        tokenService = new TokenRequestRetrievalService(restTemplate);
    }

    @Test
    public void tokenRetrievalUnderNormalConditions() throws JsonProcessingException {
        // GIVEN
        JavaType type = new ObjectMapper().getTypeFactory().constructType(RevolutTokenRenewalResponse.class);
        TokenRenewalResponse expected = TestDataHelper.generateExpiredRevolutTokenResponse();
        ResponseEntity<String> response = new ResponseEntity<>(new ObjectMapper().writeValueAsString(expected), HttpStatus.OK);

        // WHEN
        when(restTemplate.postForEntity(anyString(), notNull(), eq(String.class))).thenReturn(response);
        TokenRenewalResponse actual = tokenService.retrieveToken(new LinkedMultiValueMap<>(), Constants.REVOLUT_TOKEN_RENEWAL_URL, type);

        // THEN
        assertEquals(expected, actual);
    }
}
