/**
 * @author Paulius Staisiunas
 */

package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.Constants;
import com.j2020.model.TokenFetchException;
import com.j2020.model.deutsche.DeutscheTokenRenewalResponse;
import com.j2020.service.TokenRequestRetrievalService;
import com.j2020.service.deutsche.DeutscheTokenService;
import helper.TestDataHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class DeutscheTokenServiceTest {
    private DeutscheTokenService tokenService;
    private TokenRequestRetrievalService retrievalService;

    @Before
    public void setUp() {
        retrievalService = Mockito.mock(TokenRequestRetrievalService.class);
        tokenService = new DeutscheTokenService(retrievalService);

        setField(tokenService, "deutTokenRenewalUri", Constants.DEUTSCHE_TOKEN_RENEWAL_URL);
        setField(tokenService, "deutClientId", Constants.DEUTSCHE_CLIENT_ID);
        setField(tokenService, "deutClientSecret", Constants.DEUTSCHE_CLIENT_SECRET);
        setField(tokenService, "oAuthToken", Constants.DEUTSCHE_OAUTHTOKEN);
    }

    @Test
    public void retrieveAccessToken() throws JsonProcessingException {
        // GIVEN
        JavaType type = new ObjectMapper().getTypeFactory().constructType(DeutscheTokenRenewalResponse.class);
        DeutscheTokenRenewalResponse renewalResponse = TestDataHelper.generateExpiredDeutscheTokenResponse();

        // WHEN
        when(retrievalService.retrieveToken(notNull(), eq(Constants.DEUTSCHE_TOKEN_RENEWAL_URL), eq(type))).thenReturn(renewalResponse);
        tokenService.refreshToken();
        String actual = tokenService.getToken();

        // THEN
        assertEquals("expiredAnyway", actual);
    }

    @Test
    public void requestServiceFailed() throws JsonProcessingException {
        // GIVEN
        JavaType type = new ObjectMapper().getTypeFactory().constructType(DeutscheTokenRenewalResponse.class);

        // WHEN
        when(retrievalService.retrieveToken(notNull(), eq(Constants.DEUTSCHE_TOKEN_RENEWAL_URL), eq(type))).thenThrow(HttpClientErrorException.class);

        // THEN
        assertThrows(TokenFetchException.class, () -> tokenService.refreshToken());
    }
}
