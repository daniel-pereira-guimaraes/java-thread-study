package com.example.example15;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class Main {

    private static final String INPUT_FILE = "c:\\tmp\\file.txt";
    private static final int NUMBER_OF_THREADS = 3;
    private static final int OK = 200;
    private static final int BAD_REQUEST = 400;
    private static final String WORD_PARAM = "word";
    private static final String WORD_PARAM_NOT_FOUND = "Required 'word' param not found!";
    private static final int PORT = 8000;

    public static void main(String[] args) throws IOException {
        var text = new String(Files.readAllBytes(Paths.get(INPUT_FILE)));
        startServer(text);
    }

    private static void startServer(String text) throws IOException {
        var executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        var server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/search", new WordCountHandler(text));
        server.setExecutor(executor);
        server.start();
    }

    private static class WordCountHandler implements HttpHandler {

        private final String text;

        private WordCountHandler(String text) {
            this.text = text;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            var queryParams = getQueryParams(exchange);
            var word = queryParams.get(WORD_PARAM);
            if (word == null || word.isEmpty()) {
                writeResponse(exchange, BAD_REQUEST, WORD_PARAM_NOT_FOUND.getBytes());
                return;
            }
            var count = countWord(word);
            writeResponse(exchange, OK, Long.toString(count).getBytes());
        }

        private Map<String, String> getQueryParams(HttpExchange exchange) {
            var result = new HashMap<String, String>();
            var queryParams = exchange.getRequestURI().getQuery().split("&");
            for (var queryParam : queryParams) {
                var nameValue = queryParam.split("=");
                if (nameValue.length > 1) {
                    result.put(nameValue[0], nameValue[1]);
                }
            }
            return result;
        }

        private void writeResponse(HttpExchange exchange, int status, byte[] body)
                throws IOException {
            exchange.sendResponseHeaders(status, body.length);
            writeResponseBody(exchange, body);
        }

        private void writeResponseBody(HttpExchange exchange, byte[] body)
                throws IOException {
            try (var outputStream = exchange.getResponseBody()) {
                outputStream.write(body);
            }
        }

        private long countWord(String word) {
            long count = 0;
            int index = 0;
            while (index >= 0) {
                index = text.indexOf(word, index);
                if (index >= 0) {
                    count++;
                    index++;
                }
            }
            return count;
        }
    }

}
