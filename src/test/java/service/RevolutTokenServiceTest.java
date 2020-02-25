/**
 * @author Paulius Staisiunas
 */

package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.Constants;
import com.j2020.model.TokenFetchException;
import com.j2020.model.revolut.RevolutTokenRenewalResponse;
import com.j2020.service.TokenRequestRetrievalService;
import com.j2020.service.revolut.RevolutTokenService;
import helper.TestDataHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.client.HttpClientErrorException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

public class RevolutTokenServiceTest {
    private RevolutTokenService tokenService;
    private TokenRequestRetrievalService retrievalService;

    @Before
    public void setUp() {
        retrievalService = Mockito.mock(TokenRequestRetrievalService.class);
        tokenService = new RevolutTokenService(retrievalService);

        setField(tokenService, "revoTokenRenewalUri", Constants.REVOLUT_TOKEN_RENEWAL_URL);
        setField(tokenService, "revoRefreshToken", Constants.REVOLUT_REFRESH_TOKEN);
        setField(tokenService, "revoClientId", Constants.REVOLUT_CLIENT_ID);
        setField(tokenService, "clAssertType", Constants.REVOLUT_ASSERTION_TYPE);
        setField(tokenService, "OAuthJWT", Constants.REVOLUT_OAUTHJWT);
    }

    @Test
    public void retrieveAccessToken() throws JsonProcessingException {
        // GIVEN
        JavaType type = new ObjectMapper().getTypeFactory().constructType(RevolutTokenRenewalResponse.class);
        RevolutTokenRenewalResponse renewalResponse = TestDataHelper.generateExpiredRevolutTokenResponse();

        // WHEN
        when(retrievalService.retrieveToken(notNull(), eq(Constants.REVOLUT_TOKEN_RENEWAL_URL), eq(type))).thenReturn(renewalResponse);
        tokenService.refreshToken();
        String actual = tokenService.getToken();

        // THEN
        assertEquals("expiredAnyway", actual);
    }

    @Test
    public void requestServiceFailed() throws JsonProcessingException {
        // GIVEN
        JavaType type = new ObjectMapper().getTypeFactory().constructType(RevolutTokenRenewalResponse.class);

        // WHEN
        when(retrievalService.retrieveToken(notNull(), eq(Constants.REVOLUT_TOKEN_RENEWAL_URL), eq(type))).thenThrow(HttpClientErrorException.class);

        // THEN
        assertThrows(TokenFetchException.class, () -> tokenService.refreshToken());
    }
}
