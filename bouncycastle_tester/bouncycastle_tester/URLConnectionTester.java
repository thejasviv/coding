package bouncycastle_tester;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.Security;

import org.spongycastle.jce.provider.BouncyCastleProvider;

public class URLConnectionTester {

    public static void main(String[] args) throws Throwable {
        Security.insertProviderAt(new BouncyCastleProvider(), 0);
        testUrlConnection();
    }
    
    public static void testUrlConnection() throws Exception {
        URL url = new URI("https://hitax.hawaii.gov/WebServices/HICAuth/TaxClearanceStatus").toURL();
        //URL url = new URI("https://www.salesforce.com").toURL();
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        //connection.getInputStream().read();
        connection.getOutputStream().write(10);
    }

}
