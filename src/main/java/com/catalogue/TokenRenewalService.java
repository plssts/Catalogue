package com.catalogue;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
// should bee an interface?
public class TokenRenewalService {
    public String getNewToken(@RequestBody Map<String, String> req){
        //RestTemplate template = new RestTemplateBuilder().build();

        //RequestEntity<String> request = new RequestEntity<String>(HttpMethod.POST).BodyBuilder;
        req.get("grant_type"); // refresh_token
        req.get("refresh_token"); // oa_sand_vx6Wl1iCfh7Ou1XEPMNCDIrWiVe0ip6YtG5Nifj-TKc
        req.get("client_id"); // vuIvOZuC-qQqJiOENV1Nrb8I26oqQtszWDLqSdYLSKc
        req.get("client_assertion_type"); // urn:ietf:params:oauth:client-assertion-type:jwt-bearer
        req.get("client_assertion"); // eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJyZXZvbHV0LWp3dC1zYW5kYm94LmdsaXRjaC5tZSIsInN1YiI6InZ1SXZPWnVDLXFRcUppT0VOVjFOcmI4STI2b3FRdHN6V0RMcVNkWUxTS2MiLCJhdWQiOiJodHRwczovL3Jldm9sdXQuY29tIn0.zTjgOs0bXnaeke8hTDSwlxoXh-c1i2aZux9N0D2CCTMKptIC0SB4xExpN-wK6dJnORMvJvGLcHeaceyaf62Kwa9SQ3tBRtruVzdyBiGOUhBo_dcGfX50L2OGn-BLSf3LmX-4zWpQ1_LvO8wHp9wawEpg59PXMdJXF7TDKVDkCx0
        // ^^ the OAuthJWT, located in RevolutService

        // form a post request and send it to:
        // https://sandbox-b2b.revolut.com/api/1.0/auth/token
        return "";
    }
}
