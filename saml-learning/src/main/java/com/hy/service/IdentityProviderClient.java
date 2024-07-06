package com.hy.service;

import com.hy.model.AuthnRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.impl.ResponseMarshaller;
import org.opensaml.xml.util.XMLHelper;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.Signer;
import org.w3c.dom.Element;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public final class IdentityProviderClient {
    public static void authenticate(AuthnRequest authRequest) throws Exception {
        Response response = ResponseFactory.buildResponse(authRequest);
        IdentityProviderClient.authenticate(response,authRequest);
    }

    private static void authenticate(Response response,AuthnRequest authRequest) throws MarshallingException, IOException, SignatureException {



        AssertionSigner signingFactory = AssertionSigner.createWithCredential(authRequest.signingCredential);
        Signature signature = signingFactory.signResponse(response);
        ResponseMarshaller responseMarshaller = new ResponseMarshaller();
        Element el = responseMarshaller.marshall(response);
        Signer.signObject(signature);
        String originalAssertionString = XMLHelper.nodeToString(el);
        System.out.format("%n***** Assertion XML ******%n%n");
        System.out.println(originalAssertionString);
        originalAssertionString = originalAssertionString.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>","");
        String samlResponse = Base64.getEncoder().encodeToString(originalAssertionString.getBytes(StandardCharsets.UTF_8));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(samlResponse);

        HttpPost httpPost = new HttpPost(response.getDestination());

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("SAMLResponse", samlResponse));
        params.add(new BasicNameValuePair("RelayState", authRequest.relayState));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        System.out.format("%n***** Sending request to Okta ******%n%n");
        try (CloseableHttpClient httpClient = HttpClients.createSystem();
             CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
            System.out.println(httpPost);
            System.out.println(httpResponse.getStatusLine());
            Arrays.stream(httpResponse.getAllHeaders()).forEach(System.out::println);
            System.out.println(EntityUtils.toString(httpResponse.getEntity()));
        }
    }
}
