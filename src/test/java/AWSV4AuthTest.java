
import java.io.IOException;

import java.security.KeyManagementException;

import java.security.KeyStoreException;

import java.security.NoSuchAlgorithmException;

import java.util.Map;

import java.util.Scanner;

import java.util.TreeMap;


import org.apache.http.HttpResponse;

import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.client.methods.HttpPost;

import org.apache.http.conn.ssl.NoopHostnameVerifier;

import org.apache.http.conn.ssl.TrustAllStrategy;

import org.apache.http.entity.ContentType;

import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClients;

import org.apache.http.ssl.SSLContextBuilder;


public class AWSV4AuthTest {

    public static void main(String[] args) throws ClientProtocolException, IOException, NoSuchAlgorithmException,

            KeyManagementException, KeyStoreException {

        String url = "<<enter your URL here>>";

        String p1 = "<<add your body part here or read from external file and pass here>>";


        TreeMap<String, String> awsHeaders = new TreeMap<String, String>();

        awsHeaders.put("host", "<<add your host entry here>>");


        AWSV4Auth aWSV4Auth = new AWSV4Auth.Builder("<<add your accessKeyID>>", "<<add your secretAccessKey here>>")

                .regionName("us-east-1").serviceName("execute-api") // es -

                // elastic

                // search.

                // use your

                // service

                // name

                .httpMethodName("GET") // GET, PUT, POST, DELETE, etc...

                .canonicalURI("/dev/place") // end point

                .queryParametes(null) // query parameters if any

                .awsHeaders(awsHeaders) // aws header parameters

                .payload(null) // payload if any

                .debug() // turn on the debug mode

                .build();


        HttpClient httpClient = HttpClients.custom()

                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())

                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();


        CloseableHttpClient httpclient = HttpClients.createDefault();


        HttpPost httppost = new HttpPost();


        System.out.println("Request Type: " + httppost.getMethod());


        HttpGet httpGet = new HttpGet(url);


        Map<String, String> header = aWSV4Auth.getHeaders();

        for (Map.Entry<String, String> entrySet : header.entrySet()) {

            String key = entrySet.getKey();

            String value = entrySet.getValue();


            httpGet.addHeader(key, value);


        }

        System.out.println("Request Type: " + httpGet.getMethod());

        httpGet.addHeader("Accept", "application/json");

        httpGet.addHeader("x-api-key", "<<add your apikey here>>");


        StringEntity requestEntity = new StringEntity(p1, ContentType.APPLICATION_JSON);


        httppost.setEntity(requestEntity);


        HttpResponse httpresponse = httpClient.execute(httpGet);


        @SuppressWarnings("resource")

        Scanner scan = new Scanner(httpresponse.getEntity().getContent());


        System.out.println(httpresponse.getStatusLine());

        while (scan.hasNext()) {

            System.out.println(scan.nextLine());

        }


    }

}

