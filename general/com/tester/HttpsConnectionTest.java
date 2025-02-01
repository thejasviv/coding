package com.tester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import javax.net.ServerSocketFactory;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class HttpsConnectionTest {
    
    String[] certs = new String[] {
            "-----BEGIN CERTIFICATE-----\n"
            + "MIID3zCCAsegAwIBAgIJAKzhQSmFl5O2MA0GCSqGSIb3DQEBCwUAMHUxCzAJBgNV\n"
            + "BAYTAlVTMRMwEQYDVQQIDApDYWxpZm9ybmlhMRcwFQYDVQQKDA5TYWxlc2ZvcmNl\n"
            + "LmNvbTEMMAoGA1UECwwDUiZEMSowKAYDVQQDDCFTRkRDIERldiBSb290IGZvciB0\n"
            + "dm9uaWFkLWx0bTJkOTQwHhcNMjQwNTE0MDg1NjQyWhcNMjQwODEyMDg1NjQyWjB1\n"
            + "MQswCQYDVQQGEwJVUzETMBEGA1UECAwKQ2FsaWZvcm5pYTEXMBUGA1UECgwOU2Fs\n"
            + "ZXNmb3JjZS5jb20xDDAKBgNVBAsMA1ImRDEqMCgGA1UEAwwhU0ZEQyBEZXYgUm9v\n"
            + "dCBmb3IgdHZvbmlhZC1sdG0yZDk0MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIB\n"
            + "CgKCAQEA3elm0n+iTPihxs+K6gOGzS9wUS8YPKOXD42K1fgPJE0e3cw8+f9VKfEQ\n"
            + "u3kgg08KFK6RxyM4yf7LzGwdI5yh1txJN7XbzNBdsIavg3tuOLiT318075G/mrNc\n"
            + "xtMAjtTW7QFMqdF9J1XqdA7csPAuO+jQWga9LYYFA0rzb/jmKI+i3goHRbalsvbD\n"
            + "n+h7ktd0QRBLRnXKeUhfHM3YcQF3eEmXVbaEXqcHZdgodfZs/QzhCFarBz3jTva/\n"
            + "SHMTdp5gOUtxBvtqhnaCodCrYO2XFqav68Ln1AO2hwGWXIBtOh6ugLEPQW3AL8sJ\n"
            + "KXliKcsLgNlxMP0Tk/n/vQJid+JIkQIDAQABo3IwcDAdBgNVHQ4EFgQUf/u8Czhd\n"
            + "rL0Nrwqiz+eEjBDcsG0wDwYDVR0TAQH/BAUwAwEB/zALBgNVHQ8EBAMCAYYwEQYJ\n"
            + "YIZIAYb4QgEBBAQDAgI0MB4GCWCGSAGG+EIBDQQRFg94Y2EgY2VydGlmaWNhdGUw\n"
            + "DQYJKoZIhvcNAQELBQADggEBAF7E6wHxfW6fPM9KCJiMUIjF+nb/JvuAj0MurPlI\n"
            + "c1KpcIF8XTaUUy7EBWGsihZZ3IYUx3DY6wFWf8YsBZVLa/7ygSv5GR/6PQIIbDuH\n"
            + "aywij4JX1C/VOgnk7vdrUoYtmSg2ttWVnxX3OUNp2Bos8AIfRFLPOAG6anhOVEAU\n"
            + "fGnu32j28MlXO6tEBk4JBnoXTl/YItxJD247LqIhi4xIfoYwYGKU/jV5tXX7k9Xl\n"
            + "JBcFcPL5kYUrD/7L8CFo78au6m7xuQkuH6bvo1LW1qMHST1H/VE9XOprU/riGcKp\n"
            + "9SIAwVtxbWuM0550zVCB0AkQWulDBUevpsqzsoQOdD3RD7Q=\n"
            + "-----END CERTIFICATE-----",
            "-----BEGIN CERTIFICATE-----\n"
            + "MIIF7jCCA9agAwIBAgIIVSIKZQxZRA0wDQYJKoZIhvcNAQENBQAwfTELMAkGA1UE\n"
            + "BhMCVVMxEzARBgNVBAgTCkNhbGlmb3JuaWExFjAUBgNVBAcTDVNhbiBGcmFuY2lz\n"
            + "Y28xFzAVBgNVBAoTDlNhbGVzZm9yY2UuY29tMQwwCgYDVQQLDANSJkQxGjAYBgNV\n"
            + "BAMTEVNGREMgVGVzdGluZyBSb290MB4XDTE5MTIxMzAwMDAwMFoXDTMwMTIzMTIz\n"
            + "NTk1OVowfTELMAkGA1UEBhMCVVMxEzARBgNVBAgTCkNhbGlmb3JuaWExFjAUBgNV\n"
            + "BAcTDVNhbiBGcmFuY2lzY28xFzAVBgNVBAoTDlNhbGVzZm9yY2UuY29tMQwwCgYD\n"
            + "VQQLDANSJkQxGjAYBgNVBAMTEVNGREMgVGVzdGluZyBSb290MIICIjANBgkqhkiG\n"
            + "9w0BAQEFAAOCAg8AMIICCgKCAgEAxa/5JsvmLIZeNa78HHGBnvdrajB1zJTAlIIG\n"
            + "7Ogh5TBFSP7r4w7sNFpNqf4xMW5Wycnm3n31QfxsM7yl6iYv4yBNm3DCsyePhctd\n"
            + "zKBRKcE/6Z8ODoO85WAsctbgkfsBCUYoO+FpiFgJymp6QdPuWVouIZS6zgmpBE22\n"
            + "AK3lIQWO85d1CuheoeasOUqxHbHOaWwR90TwjLkUKr9xLsyXuSGed5cWXmojE9W+\n"
            + "QwMDi/oUoo4TMZ+7Nu4+FAgOd6s1rI6KzLofPQxeKxP5/7hV6T8oQQcQyG6ok3Pb\n"
            + "3tl1rd5BoEPSeXoluqxn1QE3D6YAgvdgwmkeHLL0u5HpxaSe6uARx8y1VfI2w9u9\n"
            + "7LTY5qM3qmQR+GpflNIh6uswF1EXX1ITwR6A5IyxqLm+HVsCbzKOzrYHhwdbMWnB\n"
            + "lYKDgopnU2/dywBNxlZj0jNsDMI+vr+11GmUE2kLRFWJ6XQTm4WlczXWqU9rhI9R\n"
            + "yv1bRkjWy7OR9IBzolJ2gGUlSbQjcLPrMiLKvfRN/P5Pk7oRtQ+/Xwe12OXt5eVs\n"
            + "m1Xb2uPDeUsNZG4YnDrvHBRbnYdPws6T8I9ef89ZrVUiFwH9jBK8NMFUJgtlmCA7\n"
            + "D8OGRqaNtQAlGvjNb6YLBlYoA7130X/FRvKNXCfaEPerVDcNuv3WH2HV9fGQNCzA\n"
            + "feaboaMCAwEAAaNyMHAwDwYDVR0TAQH/BAUwAwEB/zAdBgNVHQ4EFgQUdraZ0f0g\n"
            + "JlK4tmJi91uCkKa/PTwwCwYDVR0PBAQDAgEGMBEGCWCGSAGG+EIBAQQEAwIABzAe\n"
            + "BglghkgBhvhCAQ0EERYPeGNhIGNlcnRpZmljYXRlMA0GCSqGSIb3DQEBDQUAA4IC\n"
            + "AQBh1q0PiWhzOm9VJUwxRgLDt6P3bMMYU436HmNbK4Aha9omj5P4ZrxqCcMPdt7s\n"
            + "T/B/Lj6aRI2E5MPbxU8oKcPwOqstigTuCxHdd9A+sKuNh1t1ekv8biyaqoriO2cP\n"
            + "JiXP9KaPJfKOOnH/Dwzp/HPdvWjoqYSI+b6pK0ji+RCX21exan2ywxTiyO8KqZ/F\n"
            + "XzWyLOlwn/HfylaNmnCFNHevd22I8U43HMOpVuGsYQ/5tUJ4MH/lMd8PMPrgHesy\n"
            + "WdtYWDu+QaTDyqJ2UVwRjTlrTv9m+wj4qrnDM5X4QDXD3t9V3yM6hKAgEHYfYI6l\n"
            + "ms3Dvmj3WP8h36VH8/XqO+g3YiQ1VrMZ1JEI8wSqFAwa/eMYiCN9L15lpfy029pZ\n"
            + "HF1JCkVkuNPNE2sJ2BA8T4/YMS9uzi8kHRnLE6Ud/PUN3c7sR7GsuJYkc1usc4Kc\n"
            + "Tmp4UPGXWxiAG/cmcGq1BtandpfLWZ8NrjFM0vC+KlIZnNDDKJHXaSpni2m0z7Kk\n"
            + "pvnmcM92JS1Irwjp+nb3jg4j7SbfM8vtXh5ZHAnNfNHy0BLFtJnzoksQVeWO/eIf\n"
            + "e8d2WPFlQzjgCPsQM2cpg6bXXemd+6u4A+JivAdmDaPNtWMvcnQUFkh625qUKXqH\n"
            + "4boRpe6NWSciLFRSnqDzleyeF7R5tfpEuHY1q+W3u/FZzg==\n"
            + "-----END CERTIFICATE-----"};

    public static void main(String[] args) throws IOException {
        try (final ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket()) {
            serverSocket.bind(new InetSocketAddress("localhost", 28073));
            serverSocket.setSoTimeout(1000);
            final InetSocketAddress serverSocketAddress = (InetSocketAddress)serverSocket.getLocalSocketAddress();
            final URL calloutTarget = new URL("https://localhost:28073");
            final AtomicReference<Throwable> thrownException = new AtomicReference<>();
            final AtomicBoolean testPassed = new AtomicBoolean(false);
            final Thread ntThread = new Thread(() -> {});
            final Thread serverThread = new Thread(() -> {
                try {
                    final Socket socket = serverSocket.accept();
                    socket.setSoTimeout(5000);
                    try {
                        final DataInputStream in = new DataInputStream(socket.getInputStream());
                        assertEquals("Expected a handshake message type", 0x16, in.readUnsignedByte());
                        final int tlsVersion = in.readUnsignedShort();
                        if (tlsVersion != 0x0303 && tlsVersion != 0x0304 &&
                                tlsVersion != 0x0301) {
                            fail("Expected to see TLS 1.2 or 1.3 as our version (this can change once we have TLS 1.4 and/or higher enabled), but saw " + tlsVersion);
                        }
                        final int recordLength = in.readUnsignedShort();
                        if (recordLength <= 0) {
                            fail("TLS record length has an unexpectedly high value: " + (recordLength & 0xffff));
                        }
                        assertEquals("Expected to see the message type set to ClientHello", 0x01, in.readUnsignedByte());
                        assertEquals("Expected the first byte of the 3-byte ClientHello to be 0x00", 0x00, in.readUnsignedByte());
                        final int clientHelloLength = in.readUnsignedShort();
                        if (clientHelloLength <= 0) {
                            fail("ClientHello length has an unexpectedly high value: " + (clientHelloLength & 0xffff));
                        }
                        assertEquals("Expected TLS 1.2", 0x0303, in.readUnsignedShort());
                        in.skipBytes(32); // 32 bytes of random bytes
                        final int sessionIdLength = in.readUnsignedByte();
                        in.skipBytes(sessionIdLength);

                        // Get and assert the ciphersuite byte count
                        int cipherSuiteBytes = in.readUnsignedShort();
                        assertTrue("Expected the cipher suite length to be divisible by 4. Unfortunately, while divisibility by 2 is sufficient (each cipher suite item is two bytes), some servers go further an require the cipher suite count to be divisible by two, too. Actual byte size was " + cipherSuiteBytes,
                                cipherSuiteBytes % 4 == 0);
                        testPassed.set(true);
                    } finally {
                        socket.shutdownOutput();
                    }
                } catch (Throwable t) {
                    thrownException.set(t);
                }
            }, "ServerThread");
            serverThread.start();

            // Make an https callout. We expect it to fail, which is fine.
            try {
                final URLConnection conn = calloutTarget.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                ((HttpsURLConnection) conn).setSSLSocketFactory(initSSLContext().getSocketFactory());
                conn.connect();
                fail("We expected the callout to our test server to fail");
            } catch (SSLHandshakeException e) {
                assertEquals("Expected a particular exception message", getExpectedSSLHandshakeExceptionMessage(), e.getMessage());
            }
        }
    }
    
    private SSLContext initSSLContext() throws Exception {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        return initSSLContext(sslContext, null, null);
    }
    
    private SSLContext initSSLContext(SSLContext sslContext, X509Certificate[] clientCert, PrivateKey clientCertPrivateKey) throws Exception {
        final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        final KeyStore trustKeystore = KeyStore.getInstance("JKS");
        final char[] keystorePassword = "123456".toCharArray();
        trustKeystore.load(null, keystorePassword);
        for (String certString : certs) {
            trustKeystore.setCertificateEntry(String.valueOf(certString.substring(0, 5)), getCertificate(certString));
        }
        trustManagerFactory.init(trustKeystore);
        final KeyManager[] keyManagers = new KeyManager[] {DynamicCertificateX509KeyManager.getInstance()};
        sslContext.init(keyManagers, trustManagerFactory.getTrustManagers(), null);
        return sslContext;
    }
    
    private Certificate getCertificate(String certString) throws Exception {
        CertificateFactory factory = CertificateFactory.getInstance("X509");
        try (ByteArrayInputStream in = new ByteArrayInputStream(certString.getBytes(StandardCharsets.UTF_8))) {
            return factory.generateCertificate(in);
        }
    }

}
