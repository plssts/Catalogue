package com.catalogue;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RevolutService implements AccountService {
    private String OAuthJWT = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJp" +
            "c3MiOiJyZXZvbHV0LWp3dC1zYW5kYm94LmdsaXRjaC5tZSIsInN1YiI6InZ" +
            "1SXZPWnVDLXFRcUppT0VOVjFOcmI4STI2b3FRdHN6V0RMcVNkWUxTS2MiLC" +
            "JhdWQiOiJodHRwczovL3Jldm9sdXQuY29tIn0.zTjgOs0bXnaeke8hTDSwl" +
            "xoXh-c1i2aZux9N0D2CCTMKptIC0SB4xExpN-wK6dJnORMvJvGLcHeaceya" +
            "f62Kwa9SQ3tBRtruVzdyBiGOUhBo_dcGfX50L2OGn-BLSf3LmX-4zWpQ1_L" +
            "vO8wHp9wawEpg59PXMdJXF7TDKVDkCx0";

    @Override
    public String retrieveAccountData() {
        String url = "https://sandbox-b2b.revolut.com/api/1.0/accounts";

        // So far manually renewed and hardcoded
        // TODO make it automatic and move to other service
        String OAuthToken = "oa_sand_klp0O8n3lEf_JTIHk51ClL8qsPJ-GesphYs2mLVHSOM";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + OAuthToken);

        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> response;
        try {
            response = template.exchange(url, HttpMethod.GET, new HttpEntity(headers), String.class);
        } catch(HttpClientErrorException ex){
            return "OAuthToken has to be renewed (manually)";
        }

        return response.getBody();
    }
}
