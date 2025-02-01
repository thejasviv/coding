//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package com.tester;

import com.force.commons.collection.Pair;
import com.force.commons.provider.ProviderFactory;
import com.force.commons.text.TextUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.URL;
import java.security.KeyPair;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIMatcher;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509ExtendedKeyManager;
import klein.Klein;

public class DynamicCertificateX509KeyManager extends X509ExtendedKeyManager {
    private static final DynamicCertificateX509KeyManager INSTANCE = new DynamicCertificateX509KeyManager();
    private static final String OCSP_RESPONDER_PATH = "/qa/testcertrevocation/ocspResponse.jsp?revoked=%s&orgId=%s&hostname=%s&expiredocspresponse=%s";
    private static final String CRL_DISTRIBUTION_POINT_PATH = "/qa/testcertrevocation/crlResponse.jsp?revoked=%s&orgId=%s&hostname=%s";
    public static final Object CERT_FOR_REVOCATION_CHECK;
    public static final Object IS_CERT_REVOKED;
    public static final Object IS_OCSP_RESPONSE_EXPIRED;
    static final Logger logger;
    private final LoadingCache<String, Pair<X509Certificate[], PrivateKey>> certificateAndKeyLoader;
    private static final Pair<X509Certificate[], PrivateKey> NO_CERT = Pair.of((Object)null, (Object)null);

    public DynamicCertificateX509KeyManager() {
        this.certificateAndKeyLoader = CacheBuilder.newBuilder().maximumSize(32768L).expireAfterAccess(10L, TimeUnit.MINUTES).build(CacheLoader.from(this::loadCertPrivateKey));
    }

    public static DynamicCertificateX509KeyManager getInstance() {
        return INSTANCE;
    }

    private Pair<X509Certificate[], PrivateKey> loadCertPrivateKey(String hostname) {
        try {
            if (isRunningTests()) {
                String updatedHostname = (String)getTestValue(hostname, (Object)DynamicCertificateX509KeyManager.TestContextHolder.DYNAMIC_CERTIFICATE_MAP_HOSTNAME, (String)null);
                if (!TextUtil.isNullEmptyOrWhitespace(updatedHostname)) {
                    hostname = updatedHostname;
                }
            }

            KeyPair keyPair = (KeyPair)DynamicCertificateX509KeyManager.PkiUtilHolder.GENERATE_KEY_PAIR_METHOD.invoke((Object)null, DynamicCertificateX509KeyManager.PkiUtilHolder.DEFAULT_KEYSIZE);
            int indexOfOrgId = hostname.indexOf(".testrevocation");
            String orgId18Char = indexOfOrgId != -1 ? hostname.substring(0, indexOfOrgId) : null;
            String orgId = null;

            try {
                orgId = (String)DynamicCertificateX509KeyManager.CaseSafeIdsHolder.GET_CASE_SENSITIVE_ID_FROM_LOWER_METHOD.invoke((Object)null, orgId18Char);
            } catch (Exception var18) {
            }

            String certRevocationGateName = (String)DynamicCertificateX509KeyManager.GatewayGatesHolder.GENERATE_FULL_GATE_NAME_METHOD.invoke((Object)null, DynamicCertificateX509KeyManager.CertificateRevocationUtilHolder.ENABLE_CERT_REVOCATION);
            boolean isCertificateRevocationEnabled = this.getCertificateRevocationGateValue(certRevocationGateName, orgId);
            String ocspGateName = (String)DynamicCertificateX509KeyManager.GatewayGatesHolder.GENERATE_FULL_GATE_NAME_METHOD.invoke((Object)null, DynamicCertificateX509KeyManager.CertificateRevocationUtilHolder.ENABLE_OCSP_CHECK);
            boolean isOcspEnabled = this.getCertificateRevocationGateValue(ocspGateName, orgId);
            String crlGateName = (String)DynamicCertificateX509KeyManager.GatewayGatesHolder.GENERATE_FULL_GATE_NAME_METHOD.invoke((Object)null, DynamicCertificateX509KeyManager.CertificateRevocationUtilHolder.ENABLE_CRL_CHECK);
            boolean isCrlEnabled = this.getCertificateRevocationGateValue(crlGateName, orgId);
            Boolean isCertificateRevoked = isRunningTests() && containsTestValue(orgId, (Object)IS_CERT_REVOKED, (String)null) ? (Boolean)getTestValue(orgId, (Object)IS_CERT_REVOKED, (String)null) : false;
            Boolean isOCSPResponseExpired = isRunningTests() && containsTestValue(orgId, (Object)IS_OCSP_RESPONSE_EXPIRED, (String)null) ? (Boolean)getTestValue(orgId, (Object)IS_OCSP_RESPONSE_EXPIRED, (String)null) : false;
            String caHostnameAndPort = (new URL((String)DynamicCertificateX509KeyManager.SoapHolder.GET_BASE_URL_METHOD.invoke((Object)null, true))).toString();
            String ocspUrl = isOcspEnabled ? caHostnameAndPort + String.format("/qa/testcertrevocation/ocspResponse.jsp?revoked=%s&orgId=%s&hostname=%s&expiredocspresponse=%s", isCertificateRevoked, orgId18Char, hostname, isOCSPResponseExpired) : null;
            String crlUrl = isCrlEnabled ? caHostnameAndPort + String.format("/qa/testcertrevocation/crlResponse.jsp?revoked=%s&orgId=%s&hostname=%s", isCertificateRevoked, orgId18Char, hostname) : null;
            X509Certificate signedCertificate = (X509Certificate)DynamicCertificateX509KeyManager.PkiTestingUtilHolder.SIGN_CERTIFICATE_USING_SFDC_TESTING_ROOT_METHOD.invoke(ProviderFactory.get().get(DynamicCertificateX509KeyManager.PkiTestingUtilHolder.PKI_TESTING_UTIL_CLAZZ), keyPair.getPublic(), hostname, Collections.singletonList(hostname), new Date(System.currentTimeMillis() - 172800000L), new Date(System.currentTimeMillis() + 172800000L), ocspUrl, crlUrl);
            if (isCertificateRevocationEnabled && isRunningTests()) {
                pushTestValue(orgId, CERT_FOR_REVOCATION_CHECK, new X509Certificate[]{signedCertificate}, (String)null);
            }

            return Pair.of(new X509Certificate[]{signedCertificate}, keyPair.getPrivate());
        } catch (Exception var19) {
            Exception e = var19;
            Klein.log_gwgwy(logger, Level.SEVERE, "Could not successfully generate a certificate to serve HTTPS for " + hostname, e);
            return NO_CERT;
        }
    }

    private boolean getCertificateRevocationGateValue(String gateName, String orgId) throws Exception {
        if (isRunningTests()) {
            return containsTestValue(orgId, (String)gateName, (String)null) ? (Boolean)getTestValue(orgId, (String)gateName, (String)null) : (containsTestValue((String)null, (String)gateName, (String)null) ? (Boolean)getTestValue((String)null, (String)gateName, (String)null) : false);
        } else {
            return false;
        }
    }

    private String chooseServerAlias(SSLParameters params) {
        if (params == null) {
            return null;
        } else {
            Collection<SNIMatcher> matchers = params.getSNIMatchers();
            if (matchers == null) {
                return null;
            } else {
                Iterator var3 = matchers.iterator();

                SNIMatcher matcher;
                do {
                    if (!var3.hasNext()) {
                        return null;
                    }

                    matcher = (SNIMatcher)var3.next();
                } while(!(matcher instanceof DynamicCertificateSNIMatcher));

                return ((DynamicCertificateSNIMatcher)matcher).getServerName();
            }
        }
    }

    private Pair<X509Certificate[], PrivateKey> getCertAndPrivateKey(String alias) {
        try {
            return (Pair)this.certificateAndKeyLoader.get(alias);
        } catch (ExecutionException var3) {
            ExecutionException e = var3;
            Klein.log_gwgwy(logger, Level.SEVERE, "Could not successfully get a certificate to serve HTTPS for " + alias, e);
            return NO_CERT;
        }
    }

    public X509Certificate[] getCertificateChain(String alias) {
        return (X509Certificate[])this.getCertAndPrivateKey(alias).getFirst();
    }

    public PrivateKey getPrivateKey(String alias) {
        return (PrivateKey)this.getCertAndPrivateKey(alias).getSecond();
    }

    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
        SSLParameters params = socket instanceof SSLSocket ? ((SSLSocket)socket).getSSLParameters() : null;
        return this.chooseServerAlias(params);
    }

    public String chooseEngineClientAlias(String[] keyType, Principal[] issuers, SSLEngine engine) {
        SSLParameters params = engine != null ? engine.getSSLParameters() : null;
        return this.chooseServerAlias(params);
    }

    public String chooseEngineServerAlias(String keyType, Principal[] issuers, SSLEngine engine) {
        SSLParameters params = engine != null ? engine.getSSLParameters() : null;
        return this.chooseServerAlias(params);
    }

    public String[] getClientAliases(String keyType, Principal[] issuers) {
        return null;
    }

    public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
        return null;
    }

    public String[] getServerAliases(String keyType, Principal[] issuers) {
        return null;
    }

    private static boolean isRunningTests() throws IllegalAccessException, InvocationTargetException {
        return (Boolean)DynamicCertificateX509KeyManager.TestContextHolder.IS_RUNNING_TESTS_METHOD.invoke((Object)null);
    }

    private static boolean containsTestValue(String id, String key, String orgId) throws IllegalAccessException, InvocationTargetException {
        return (Boolean)DynamicCertificateX509KeyManager.TestContextHolder.CONTAINS_TEST_VALUE_STRING_ARGUMENT_METHOD.invoke((Object)null, id, key, orgId);
    }

    private static boolean containsTestValue(String id, Object key, String orgId) throws IllegalAccessException, InvocationTargetException {
        return (Boolean)DynamicCertificateX509KeyManager.TestContextHolder.CONTAINS_TEST_VALUE_KEY_ARGUMENT_METHOD.invoke((Object)null, id, key, orgId);
    }

    private static Object getTestValue(String id, String key, String orgId) throws IllegalAccessException, InvocationTargetException {
        return DynamicCertificateX509KeyManager.TestContextHolder.GET_TEST_VALUE_STRING_ARGUMENT_METHOD.invoke((Object)null, id, key, orgId);
    }

    private static Object getTestValue(String id, Object key, String orgId) throws IllegalAccessException, InvocationTargetException {
        return DynamicCertificateX509KeyManager.TestContextHolder.GET_TEST_VALUE_KEY_ARGUMENT_METHOD.invoke((Object)null, id, key, orgId);
    }

    private static void pushTestValue(String id, Object key, Object value, String orgId) throws IllegalAccessException, InvocationTargetException {
        DynamicCertificateX509KeyManager.TestContextHolder.PUSH_TEST_VALUE_KEY_ARGUMENT_METHOD.invoke((Object)null, id, key, value, orgId);
    }

    static {
        try {
            Class<?> sfdcLogFactoryClazz = Class.forName("sfdc.log.SfdcLogFactory");
            Method getLoggerMethod = sfdcLogFactoryClazz.getMethod("getLogger", Class.class, String.class, String.class, Boolean.class);
            Class<?> gatewayGusProductTagInfoClazz = Class.forName("gateway.GatewayGusProductTagInfo");
            String gatewayTeamName = (String)gatewayGusProductTagInfoClazz.getDeclaredField("TEAM_NAME").get((Object)null);
            String gatewayProductTagGeneric = (String)gatewayGusProductTagInfoClazz.getDeclaredField("PRODUCT_TAG_GENERIC").get((Object)null);
            logger = (Logger)getLoggerMethod.invoke((Object)null, DynamicCertificateX509KeyManager.class, gatewayTeamName, gatewayProductTagGeneric, true);
            Class<?> testContextKeyClazz = Class.forName("system.context.TestContext$Key");
            Method newOrgLevelKeyMethod = testContextKeyClazz.getMethod("newOrgLevelKey", String.class);
            CERT_FOR_REVOCATION_CHECK = newOrgLevelKeyMethod.invoke((Object)null, "CertForRevocationCheck");
            IS_CERT_REVOKED = newOrgLevelKeyMethod.invoke((Object)null, "IsCertRevoked");
            IS_OCSP_RESPONSE_EXPIRED = newOrgLevelKeyMethod.invoke((Object)null, "IsOCSPResponseExpired");
        } catch (IllegalAccessException | InvocationTargetException | NoSuchFieldException | NoSuchMethodException | ClassNotFoundException var7) {
            throw new RuntimeException("Exception initializing " + DynamicCertificateX509KeyManager.class.getName());
        }
    }

    public static class DynamicCertificateSNIMatcher extends SNIMatcher {
        private volatile String serverName = "localhost";

        public DynamicCertificateSNIMatcher() {
            super(0);
        }

        public boolean matches(SNIServerName serverName) {
            if (serverName instanceof SNIHostName && ((SNIHostName)serverName).getType() == 0) {
                String hostname = TextUtil.toAsciiLowerCase(((SNIHostName)serverName).getAsciiName());
                if (TextUtil.isNullEmptyOrWhitespace(hostname)) {
                    return false;
                } else {
                    this.serverName = TextUtil.toAsciiLowerCase(hostname);
                    return true;
                }
            } else {
                return false;
            }
        }

        public String getServerName() {
            return this.serverName;
        }
    }

    private static class CertificateRevocationUtilHolder {
        static final String ENABLE_CERT_REVOCATION;
        static final String ENABLE_OCSP_CHECK;
        static final String ENABLE_CRL_CHECK;

        private CertificateRevocationUtilHolder() {
        }

        static {
            try {
                Class<?> certificateRevocationUtilClazz = Class.forName("core.revocationcheck.CertificateRevocationUtil");
                ENABLE_CERT_REVOCATION = (String)certificateRevocationUtilClazz.getDeclaredField("ENABLE_CERT_REVOCATION").get((Object)null);
                ENABLE_OCSP_CHECK = (String)certificateRevocationUtilClazz.getDeclaredField("ENABLE_OCSP_CHECK").get((Object)null);
                ENABLE_CRL_CHECK = (String)certificateRevocationUtilClazz.getDeclaredField("ENABLE_CRL_CHECK").get((Object)null);
            } catch (IllegalAccessException | NoSuchFieldException | ClassNotFoundException var1) {
                throw new RuntimeException("Exception initializing " + CertificateRevocationUtilHolder.class.getName());
            }
        }
    }

    private static class CaseSafeIdsHolder {
        static final Method GET_CASE_SENSITIVE_ID_FROM_LOWER_METHOD;

        private CaseSafeIdsHolder() {
        }

        static {
            try {
                Class<?> caseSafeIdsClazz = Class.forName("common.api.CaseSafeIds");
                GET_CASE_SENSITIVE_ID_FROM_LOWER_METHOD = caseSafeIdsClazz.getMethod("getCaseSensitiveIDFromLower", String.class);
            } catch (NoSuchMethodException | ClassNotFoundException var1) {
                throw new RuntimeException("Exception initializing " + CaseSafeIdsHolder.class.getName());
            }
        }
    }

    private static class GatewayGatesHolder {
        static final Method GENERATE_FULL_GATE_NAME_METHOD;

        private GatewayGatesHolder() {
        }

        static {
            try {
                Class<?> gatewayGatesClazz = Class.forName("gateway.util.GatewayGates");
                GENERATE_FULL_GATE_NAME_METHOD = gatewayGatesClazz.getMethod("generateFullGateName", String.class);
            } catch (NoSuchMethodException | ClassNotFoundException var1) {
                throw new RuntimeException("Exception initializing " + GatewayGatesHolder.class.getName());
            }
        }
    }

    private static class SoapHolder {
        static final Method GET_BASE_URL_METHOD;

        private SoapHolder() {
        }

        static {
            try {
                Class<?> soapClazz = Class.forName("common.api.soap.Soap");
                GET_BASE_URL_METHOD = soapClazz.getMethod("getBaseURL", Boolean.TYPE);
            } catch (NoSuchMethodException | ClassNotFoundException var1) {
                throw new RuntimeException("Exception initializing " + SoapHolder.class.getName());
            }
        }
    }

    private static class PkiTestingUtilHolder {
        static final Class<?> PKI_TESTING_UTIL_CLAZZ;
        static final Method SIGN_CERTIFICATE_USING_SFDC_TESTING_ROOT_METHOD;

        private PkiTestingUtilHolder() {
        }

        static {
            try {
                PKI_TESTING_UTIL_CLAZZ = Class.forName("system.security.PkiTestingUtil");
                SIGN_CERTIFICATE_USING_SFDC_TESTING_ROOT_METHOD = PKI_TESTING_UTIL_CLAZZ.getMethod("signCertificateUsingSfdcTestingRoot", PublicKey.class, String.class, List.class, Date.class, Date.class, String.class, String.class);
            } catch (NoSuchMethodException | ClassNotFoundException var1) {
                throw new RuntimeException("Exception initializing " + PkiTestingUtilHolder.class.getName());
            }
        }
    }

    private static class PkiUtilHolder {
        static final Method GENERATE_KEY_PAIR_METHOD;
        static final int DEFAULT_KEYSIZE;

        private PkiUtilHolder() {
        }

        static {
            try {
                Class<?> pkiUtilClazz = Class.forName("encryption.util.PkiUtil");
                GENERATE_KEY_PAIR_METHOD = pkiUtilClazz.getMethod("generateKeyPair", Integer.TYPE);
                DEFAULT_KEYSIZE = (Integer)pkiUtilClazz.getDeclaredField("DEFAULT_KEYSIZE").get((Object)null);
            } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException | ClassNotFoundException var1) {
                throw new RuntimeException("Exception initializing " + PkiUtilHolder.class.getName());
            }
        }
    }

    private static class TestContextHolder {
        static final Method IS_RUNNING_TESTS_METHOD;
        static final Method CONTAINS_TEST_VALUE_STRING_ARGUMENT_METHOD;
        static final Method CONTAINS_TEST_VALUE_KEY_ARGUMENT_METHOD;
        static final Method GET_TEST_VALUE_STRING_ARGUMENT_METHOD;
        static final Method GET_TEST_VALUE_KEY_ARGUMENT_METHOD;
        static final Method PUSH_TEST_VALUE_KEY_ARGUMENT_METHOD;
        static final Object DYNAMIC_CERTIFICATE_MAP_HOSTNAME;

        private TestContextHolder() {
        }

        static {
            try {
                Class<?> testContextClazz = Class.forName("system.context.TestContext");
                Class<?> testContextKeyClazz = Class.forName("system.context.TestContext$Key");
                IS_RUNNING_TESTS_METHOD = testContextClazz.getMethod("isRunningTests");
                CONTAINS_TEST_VALUE_STRING_ARGUMENT_METHOD = testContextClazz.getMethod("containsTestValue", String.class, String.class, String.class);
                CONTAINS_TEST_VALUE_KEY_ARGUMENT_METHOD = testContextClazz.getMethod("containsTestValue", String.class, testContextKeyClazz, String.class);
                GET_TEST_VALUE_STRING_ARGUMENT_METHOD = testContextClazz.getMethod("getTestValue", String.class, String.class, String.class);
                GET_TEST_VALUE_KEY_ARGUMENT_METHOD = testContextClazz.getMethod("getTestValue", String.class, testContextKeyClazz, String.class);
                PUSH_TEST_VALUE_KEY_ARGUMENT_METHOD = testContextClazz.getMethod("pushTestValue", String.class, testContextKeyClazz, Object.class, String.class);
                DYNAMIC_CERTIFICATE_MAP_HOSTNAME = testContextClazz.getDeclaredField("DYNAMIC_CERTIFICATE_MAP_HOSTNAME").get((Object)null);
            } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException | ClassNotFoundException var2) {
                throw new RuntimeException("Exception initializing " + TestContextHolder.class.getName());
            }
        }
    }
}
