package com.hy.service;

import com.hy.model.AuthnRequest;
import net.shibboleth.utilities.java.support.security.RandomIdentifierGenerationStrategy;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml2.core.*;
import org.opensaml.saml.saml2.core.impl.IssuerBuilder;
import org.opensaml.saml.saml2.core.impl.ResponseBuilder;
import org.opensaml.saml.saml2.core.impl.StatusBuilder;
import org.opensaml.saml.saml2.core.impl.StatusCodeBuilder;

import java.util.concurrent.TimeUnit;

final class ResponseFactory {
    static Response buildResponse(AuthnRequest input) throws Exception {
        long instant = TimeUnit.SECONDS.toMillis(input.authenticationInstant.getEpochSecond());
        DateTime authenticationTime = LocalDateTime.now().toDateTime().plusHours(8);

        Response response = new ResponseBuilder().buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "Response", "samlp");
        response.setID(new RandomIdentifierGenerationStrategy().generateIdentifier());
        response.setIssueInstant(authenticationTime);
        response.setVersion(SAMLVersion.VERSION_20);
        response.setIssuer(buildIssuer(input));
        response.setDestination(input.destinationUrl);
        response.setStatus(buildStatus());
        response.getAssertions().add(AssertionFactory.buildAssertion(input, authenticationTime));

        return response;
    }

    private static Issuer buildIssuer(AuthnRequest input) {
        Issuer issuer = new IssuerBuilder().buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Issuer", "saml");
        issuer.setValue(input.issuer);
        return issuer;
    }

    private static Status buildStatus() {
        StatusCode statusCode = new StatusCodeBuilder().buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "StatusCode", "samlp");
        statusCode.setValue(StatusCode.SUCCESS);
        Status status = new StatusBuilder().buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "Status", "samlp");
        status.setStatusCode(statusCode);
        return status;
    }
}
