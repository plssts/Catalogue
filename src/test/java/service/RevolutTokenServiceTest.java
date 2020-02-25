/**
 * @author Paulius Staisiunas
 */

package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.TokenFetchException;
import com.j2020.model.revolut.RevolutTokenRenewalResponse;
import com.j2020.service.TokenRequestRetrievalService;
import com.j2020.service.revolut.RevolutTokenService;
import helper.TestDataHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpClientErrorException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

// FIXME move strings to @Value
public class RevolutTokenServiceTest {
    @Value("${revolutTokenRenewal.OAuthJWT}")
    private String OAuthJWT;

    private RevolutTokenService tokenService;
    private TokenRequestRetrievalService retrievalService;

    @Before
    public void setUp() {
        retrievalService = Mockito.mock(TokenRequestRetrievalService.class);
        tokenService = new RevolutTokenService(retrievalService);

        setField(tokenService, "revoTokenRenewalUri", "https://sandbox-b2b.revolut.com/api/1.0/auth/token");
        setField(tokenService, "revoRefreshToken", "oa_sand_vx6Wl1iCfh7Ou1XEPMNCDIrWiVe0ip6YtG5Nifj-TKc");
        setField(tokenService, "revoClientId", "vuIvOZuC-qQqJiOENV1Nrb8I26oqQtszWDLqSdYLSKc");
        setField(tokenService, "clAssertType", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer");
        setField(tokenService, "OAuthJWT", OAuthJWT);
    }

    @Test
    public void retrieAccessToken() throws JsonProcessingException {
        //
        // GIVEN
        //
        JavaType type = new ObjectMapper().getTypeFactory().constructType(RevolutTokenRenewalResponse.class);
        RevolutTokenRenewalResponse renewalResponse = TestDataHelper.generateExpiredRevolutTokenResponse();

        //
        // WHEN
        //
        when(retrievalService.retrieveToken(notNull(), eq("https://sandbox-b2b.revolut.com/api/1.0/auth/token"), eq(type))).thenReturn(renewalResponse);
        tokenService.refreshToken();
        String actual = tokenService.getToken();

        //
        // THEN
        //
        assertEquals("expiredAnyway", actual);
    }

    @Test
    public void requestServiceFailed() throws JsonProcessingException {
        //
        // GIVEN
        //
        JavaType type = new ObjectMapper().getTypeFactory().constructType(RevolutTokenRenewalResponse.class);

        //
        // WHEN
        //
        when(retrievalService.retrieveToken(notNull(), eq("https://sandbox-b2b.revolut.com/api/1.0/auth/token"), eq(type))).thenThrow(HttpClientErrorException.class);

        //
        // THEN
        //
        assertThrows(TokenFetchException.class, () -> tokenService.refreshToken());
    }
}
