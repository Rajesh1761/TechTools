package com.exploreworld.tech.techtool.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class IpChangerTor {

    public static void main(String[] args) throws IOException, InterruptedException {
        IpChangerTor ipChangerTor = new IpChangerTor();
        ipChangerTor.processRequest();
    }

    private void processRequest() throws InterruptedException, IOException {
        // Check if tor is installed
        Process checkTor = Runtime.getRuntime().exec("which tor");
        checkTor.waitFor();
        if (checkTor.exitValue() != 0) {
            System.out.println("[+] tor is not installed !");
            Runtime.getRuntime().exec("sudo apt update").waitFor();
            Runtime.getRuntime().exec("sudo apt install tor -y").waitFor();
            System.out.println("[!] tor is installed successfully");
        }

        Runtime.getRuntime().exec("clear");
        System.out.println("\033[1;32;40m \n");
        System.out.println("                _          _______");
        System.out.println("     /\\        | |        |__   __|");
        System.out.println("    /  \\  _   _| |_ ___      | | ___  _ __");
        System.out.println("   / /\\ \\| | | | __/ _ \\     | |/ _ \\| '__|");
        System.out.println("  / ____ \\ |_| | || (_) |    | | (_) | |");
        System.out.println(" /_/    \\_\\__,_|\\__\\___/     |_|\\___/|_|");
        System.out.println("                V 2.1");
        System.out.println("from TechTools");

        Runtime.getRuntime().exec("service tor start");
        Thread.sleep(3000);
        System.out.println("\033[1;32;40m change your  SOCKES to 127.0.0.1:9050 \n");
        Runtime.getRuntime().exec("service tor start");

        Scanner scanner = new Scanner(System.in);
        System.out.print("[+] time to change IP in Sec [type=60] >> ");
        int changeInterval = Integer.parseInt(scanner.nextLine());
        System.out.print("[+] how many times do you want to change your IP [type=1000] for infinite IP change type [0] >> ");
        int changeCount = Integer.parseInt(scanner.nextLine());
        if (changeCount == 0) {
            while (true) {
                try {
                    Thread.sleep(changeInterval * 1000L);
                    changeIP();
                } catch (InterruptedException e) {
                    System.out.println("\nauto tor is closed");
                    System.exit(0);
                }
            }
        } else {
            for (int i = 0; i < changeCount; i++) {
                Thread.sleep(changeInterval * 1000L);
                changeIP();
            }
        }
    }

    private void changeIP() {
        try {
            Runtime.getRuntime().exec("service tor reload").waitFor();
            System.out.println("[+] Your IP has been Changed to : " + getExternalIP());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getExternalIP() {
        String ip = "";
        try {
            URL url = new URL("https://www.myexternalip.com/raw");
            HttpURLConnection conn = getHttpURLConnection(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            ip = in.readLine();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ip;
    }

    private HttpURLConnection getHttpURLConnection(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Proxy-Connection", "keep-alive");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:91.0) Gecko/20100101 Firefox/91.0");
        conn.setRequestProperty("Accept", "text/plain");
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Host", "www.myexternalip.com");
        conn.setRequestProperty("Proxy-Host", "127.0.0.1");
        conn.setRequestProperty("Proxy-Port", "9050");
        conn.setRequestProperty("Proxy-Type", "socks5");
        return conn;
    }
}

