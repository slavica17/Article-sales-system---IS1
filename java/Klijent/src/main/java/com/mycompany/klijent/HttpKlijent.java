/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.klijent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpKlijent {

    private static final String BASE_URL = "http://localhost:8080/CentralniServer/webresources";

    public static String get(String putanja) {
        try {
            URL url = new URL(BASE_URL + putanja);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Accept-Charset", "UTF-8");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String linija;
            while ((linija = br.readLine()) != null) {
                sb.append(linija).append("\n");
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            return "Greska: " + e.getMessage();
        }
    }

    public static String post(String putanja) {
        try {
            URL url = new URL(BASE_URL + putanja);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Accept-Charset", "UTF-8");

            int statusCode = conn.getResponseCode();

            if (statusCode >= 400) {
                return "Greska: Server je vratio status " + statusCode;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String linija;
            while ((linija = br.readLine()) != null) {
                sb.append(linija).append("\n");
            }
            br.close();
            return sb.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "Greska: " + e.getMessage();
        }
    }

    public static String put(String putanja) {
        try {
            URL url = new URL(BASE_URL + putanja);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);

            conn.setRequestProperty("Accept-Charset", "UTF-8");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String linija;
            while ((linija = br.readLine()) != null) {
                sb.append(linija).append("\n");
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            return "Greska: " + e.getMessage();
        }
    }
}
