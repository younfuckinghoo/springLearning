package com.hy.controller;

import com.hy.app.AuthnRequestBuilder;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/realms/master")
    public Map<String, Object> realmsMaster() {
        Map<String, Object> map = new HashMap<>();
        try {
            Properties properties = AuthnRequestBuilder.getProperties("idp.properties");
            String password = properties.getProperty("signingKeystorePassword");
            String signingKeystore = properties.getProperty("signingKeystore");
            char[] passwordCharArr = password.toCharArray();

            KeyStore store = KeyStore.getInstance("PKCS12");
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(signingKeystore);
            store.load(inputStream, passwordCharArr);
            KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection(passwordCharArr);
            KeyStore.PrivateKeyEntry pkEntry =
                    (KeyStore.PrivateKeyEntry) store.getEntry("1", protectionParameter);
            X509Certificate certificate = (X509Certificate) pkEntry.getCertificate();
            PublicKey publicKey = certificate.getPublicKey();
            String publicKeyStr = new String(Base64.encodeBase64(publicKey.getEncoded()));

            map.put("realm", "master");
            map.put("public_key", publicKeyStr);

            return map;
        } catch (IOException | KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return map;
    }

}
