package com.example.demo;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.github.scribejava.apis.FlickrApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

@Configuration
public class FlickrConfig {

    @Value("${flickr.apiKey}")
    String apiKey;

    @Value("${flickr.secretKey}")
    String secretKey;

    @Bean
    public Flickr getFlickr() throws InterruptedException, ExecutionException, IOException, FlickrException {
        Flickr flickr = new Flickr(apiKey, secretKey, new REST());

        // Now we need to auth flickr.
        //1. Add an Oauth
        // sorry, is oathService.
        // in this case, I just use read permission. You can choose another perm
        OAuth10aService service = new ServiceBuilder(apiKey)
                .apiSecret(secretKey).build(FlickrApi.instance(FlickrApi.FlickrPerm.READ));

        //2. Because, Flick need you confirm permission, So, We need an confirm in command.
        final Scanner in = new Scanner(System.in);

        //3. Create oauth request token.
        final OAuth1RequestToken requestToken = service.getRequestToken();

        //4. Get auth URL to confirm with flick. and get your token
        final String authUrl = service.getAuthorizationUrl(requestToken);
        System.out.println(authUrl);
        System.out.println("Paste the verifier code here:");
        System.out.print(">>");
        final String oauthVerifier = in.nextLine();

        //5. Trade the request token and verifier for access token.
        final OAuth1AccessToken accessToken = service.getAccessToken(requestToken, oauthVerifier);

        //6. Final you need check for the flickr object
        flickr.getAuthInterface().checkToken(accessToken);
        return flickr;
    }
}