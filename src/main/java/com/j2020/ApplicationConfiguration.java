/**
 * @author Paulius Staisiunas
 */

package com.j2020;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public GoogleAuthenticator googleAuthenticator() {
        return new GoogleAuthenticator();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }

    @Bean
    public RestTemplate mfaRestTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }
}
