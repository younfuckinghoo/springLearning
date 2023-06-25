package com.hy.service;

import com.hy.attributes.AttributeConverter;
import com.hy.attributes.AttributeUtils;
import com.hy.model.AuthnRequest;
import net.shibboleth.utilities.java.support.security.RandomIdentifierGenerationStrategy;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.core.xml.schema.impl.XSStringBuilder;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.SAMLVersion;


import org.opensaml.saml.saml2.core.*;
import org.opensaml.saml.saml2.core.impl.*;
import org.opensaml.xmlsec.signature.support.SignatureException;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.stream.Collectors;

final class AssertionFactory {
    static Assertion buildAssertion(AuthnRequest input, DateTime authenticationTime) throws MarshallingException, SignatureException {
        Assertion assertion = new AssertionBuilder().buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion", "saml");
        assertion.setID(new RandomIdentifierGenerationStrategy().generateIdentifier());
        assertion.setIssuer(buildIssuer(input));
        assertion.setIssueInstant(authenticationTime);
        assertion.setVersion(SAMLVersion.VERSION_20);
        assertion.getAuthnStatements().add(buildAuthnStatement(input, authenticationTime));
        assertion.getAttributeStatements().add(buildAttributeStatement(input));
        assertion.setConditions(buildConditions(input));
        assertion.setSubject(buildSubject(input, authenticationTime));
        return assertion;
    }

    private static Issuer buildIssuer(AuthnRequest input) {
        Issuer issuer = new IssuerBuilder().buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Issuer", "saml");
        issuer.setValue(input.issuer);
        return issuer;
    }

    private static AttributeStatement buildAttributeStatement(AuthnRequest input) {
        AttributeStatementBuilder attributeStatementBuilder =
                (AttributeStatementBuilder) XMLObjectSupport.getBuilder(new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AttributeStatement", "saml"));
        AttributeStatement attrStatement = attributeStatementBuilder.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AttributeStatement", "saml");
      /*  Attribute attribute = new AttributeBuilder().buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Attribute", "saml");
        attribute.setNameFormat(Attribute.BASIC);
        input.attributes.stream().map(AttributeConverter::convertAttribute).forEach(attrStatement.getAttributes()::add);*/
        List<Attribute> attributeList = input.roles.stream().map(role -> {
            Attribute attribute = new AttributeBuilder().buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Attribute", "saml");
            attribute.setNameFormat(Attribute.BASIC);
            List<XMLObject> attributeValues = attribute.getAttributeValues();
            XSString xsString = AttributeUtils.buildStringAttributeValue(role);
            attributeValues.add(xsString);
            return attribute;
        }).collect(Collectors.toList());
        attrStatement.getAttributes().addAll(attributeList);
        return attrStatement;
    }

    private static AuthnStatement buildAuthnStatement(AuthnRequest input, DateTime authenticationTime) {
        AuthnStatement authnStatement = new AuthnStatementBuilder().buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnStatement", "saml");

        authnStatement.setAuthnInstant(authenticationTime);
        authnStatement.setSessionIndex(input.sessionId);
        authnStatement.setSessionNotOnOrAfter(authenticationTime.plus(input.maxSessionTimeoutInMinutes));

        AuthnContext authnContext = new AuthnContextBuilder().buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContext", "saml");

        AuthnContextClassRef authnContextClassRef = new AuthnContextClassRefBuilder().buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContextClassRef", "saml");
        authnContextClassRef.setAuthnContextClassRef(AuthnContext.UNSPECIFIED_AUTHN_CTX);

        authnContext.setAuthnContextClassRef(authnContextClassRef);
        authnStatement.setAuthnContext(authnContext);
        return authnStatement;
    }

    private static Conditions buildConditions(AuthnRequest input) {
        Conditions conditions = new ConditionsBuilder().buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Conditions", "saml");
//        Condition condition = new OneTimeUseBuilder().buildObject();
//        conditions.getConditions().add(condition);

        AudienceRestriction audienceRestriction = new AudienceRestrictionBuilder().buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AudienceRestriction", "saml");

        Audience audience = new AudienceBuilder().buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Audience", "saml");
        audience.setAudienceURI(input.audienceRestriction);

        audienceRestriction.getAudiences().add(audience);

        conditions.getAudienceRestrictions().add(audienceRestriction);
        return conditions;
    }

    private static Subject buildSubject(AuthnRequest input, DateTime authenticationTime) {
        SubjectConfirmationData confirmationData = new SubjectConfirmationDataBuilder().buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectConfirmationData", "saml");
        confirmationData.setNotBefore(authenticationTime);
        confirmationData.setNotOnOrAfter(authenticationTime.plusMinutes(5));
        confirmationData.setRecipient(input.destinationUrl);

        SubjectConfirmation subjectConfirmation = new SubjectConfirmationBuilder().buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectConfirmation", "saml");
        subjectConfirmation.setSubjectConfirmationData(confirmationData);
        subjectConfirmation.setMethod(SubjectConfirmation.METHOD_BEARER);

        Subject subject = new SubjectBuilder().buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Subject", "saml");;
        subject.setNameID(buildNameId(input));
        subject.getSubjectConfirmations().add(subjectConfirmation);
        return subject;
    }

    private static NameID buildNameId(AuthnRequest input) {
        NameID nameId = new NameIDBuilder().buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "NameID", "saml");
        nameId.setValue(input.nameId);
        nameId.setFormat(NameIDType.UNSPECIFIED);
        return nameId;
    }
}
