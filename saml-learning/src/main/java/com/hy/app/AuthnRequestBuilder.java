package com.hy.app;

import com.hy.attributes.NamedAttribute;
import com.hy.model.AuthnRequest;
import net.shibboleth.utilities.java.support.security.RandomIdentifierGenerationStrategy;
import org.apache.commons.codec.binary.Base64;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.BasicX509Credential;

import java.io.*;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public final class AuthnRequestBuilder {
    static AuthnRequest buildAuthRequest(String userName, List<NamedAttribute> attributes) throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableEntryException {
        Properties properties = getProperties("idp.properties");
        AuthnRequest input = new AuthnRequest();
        input.authenticationInstant = Instant.now();
        input.issuer = properties.getProperty("issuer");
        input.audienceRestriction = properties.getProperty("audienceRestriction");
        input.destinationUrl = properties.getProperty("destinationUrl");
        input.nameId = userName;
        input.sessionId = new RandomIdentifierGenerationStrategy().generateIdentifier();
        input.attributes = attributes;
        input.relayState = properties.getProperty("relayState");
        input.signingCredential = loadSigningCredential(
                properties.getProperty("signingKeystorePassword"),
                properties.getProperty("signingKeystore")
        );
        return input;
    }

    public static Properties getProperties(String propertiesPath) throws IOException {
        ClassLoader classLoader = AuthnRequestBuilder.class.getClassLoader();
        // 当有依赖多个jar包，且jar包中包含相同路径文件 则会读取到多个资源
        Enumeration<URL> resources = classLoader.getResources(propertiesPath);
        while (resources.hasMoreElements()){
            URL url = resources.nextElement();
            InputStream inputStream = url.openStream();
            BufferedReader aa= new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            Properties properties = new Properties();
            properties.load(aa);
            return properties;
        }
        return null;
    }

    private static Credential loadSigningCredential(String signingKeystorePassword, String signingKeystore) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException {
        char[] password = signingKeystorePassword.toCharArray();

        KeyStore store = KeyStore.getInstance("PKCS12");

        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(signingKeystore)) {
            store.load(inputStream, password);
        }

        KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection(password);
        KeyStore.PrivateKeyEntry pkEntry =
                (KeyStore.PrivateKeyEntry) store.getEntry("1", protectionParameter);
        PrivateKey pk = pkEntry.getPrivateKey();
        String originalKeyStr = new String(Base64.encodeBase64(pk.getEncoded()));

        X509Certificate certificate = (X509Certificate) pkEntry.getCertificate();
        PublicKey publicKey = certificate.getPublicKey();
        String publicKeyStr = new String(Base64.encodeBase64(publicKey.getEncoded()));
        return new BasicX509Credential(certificate, pk);
    }


}
