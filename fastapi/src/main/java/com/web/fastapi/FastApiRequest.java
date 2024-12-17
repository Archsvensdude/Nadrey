package com.web.fastapi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class FastApiRequest
{
    public static void main(String[] args)
    {
        //simpleRequest();
        //sendJSON();
        chatgptAsk();
    }

    static void simpleRequest() {
        // HttpClient 생성
        HttpClient client = HttpClient.newHttpClient();

        // 요청 URL 구성
        String url = "http://localhost:8000/items/1?q=example";

        // HttpRequest 생성 (GET 요청)
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // 요청 전송 및 응답 받기
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 응답 상태 코드 출력
            System.out.println("Status Code: " + response.statusCode());

            // 응답 본문 출력
            System.out.println("Response Body: " + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void sendJSON() {
        // HttpClient 생성
        HttpClient client = HttpClient.newHttpClient();

        // JSON 문자열 생성
        String json = """
        {
            "name": "Laptop",
            "description": "A high-performance laptop",
            "price": 999.99
        }
        """;

        // HttpRequest 생성 (POST 요청)
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/items/json"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // 요청 전송 및 응답 받기
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 응답 상태 코드 출력
            System.out.println("Status Code: " + response.statusCode());

            // 응답 본문 출력
            System.out.println("Response Body: " + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void chatgptAsk() {
        // HttpClient 생성
        HttpClient client = HttpClient.newHttpClient();

        // 전송할 JSON 문자열 생성
        String json = """
        {
            "question": "What is the capital of France?"
        }
        """;

        // HttpRequest 생성 (POST 요청)
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/ask"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        // 요청 전송 및 응답 받기
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 응답 상태 코드 출력
            System.out.println("Status Code: " + response.statusCode());

            // 응답 본문 출력
            System.out.println("Response Body: " + response.body());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

