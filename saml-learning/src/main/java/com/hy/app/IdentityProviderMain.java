package com.hy.app;

import com.hy.attributes.NamedAttribute;
import com.hy.examples.AttributeExamples;
import com.hy.model.AuthnRequest;
import com.hy.service.IdentityProviderClient;

import java.util.Arrays;
import java.util.List;

public final class IdentityProviderMain {
    public static void main(String[] args) throws Exception {
        String exampleName = args.length > 0 ? args[0] : AttributeExamples.WORKS_FOR_OKTA;
        System.out.println(exampleName);
        OpenSamlInitializer.initialize();
        String userName = "test@qq.com";
        List<NamedAttribute> attributes = AttributeExamples.attributesByExampleName(exampleName, userName);
        AuthnRequest authRequest = AuthnRequestBuilder.buildAuthRequest(
                userName, attributes);
        authRequest.roles = Arrays.asList("impersonation",
                "query-users",
                "view-events",
                "maximo",
                "manage-clients",
                "manage-account",
                "manage-events",
                "view-clients",
                "manage-authorization",
                "create-client",
                "uma_authorization",
                "default-roles-master",
                "view-realm",
                "view-identity-providers",
                "manage-realm",
                "view-authorization",
                "view-profile",
                "manage-identity-providers",
                "manage-account-links",
                "query-groups",
                "create-realm",
                "admin",
                "manage-users",
                "offline_access",
                "view-users",
                "query-clients",
                "query-realms"
        );
        IdentityProviderClient.authenticate(authRequest);
    }
}
