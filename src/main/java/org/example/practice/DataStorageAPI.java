package org.example.practice;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/*
* This solution uses Java's built-in 'HttpServer' for handling HTTP requests
* */
public class DataStorageAPI {
    // Repositories
    private static final Map<String, Map<String, byte[]>> repositories = new HashMap<>();

    public static void main(String[] args) throws Exception {
        // Create an HTTP server that listens on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        // Define the '/data' endpoint and associate it with the DataHandler
        server.createContext("/data", new DataHandler1());
        // Use the default executor(single-threaded)
        server.setExecutor(null);

        // Start the server
        server.start();
        System.out.println("Server started on port 8080");
    }

    // This class handles HTTP requests related to data storage(PUT, GET, DELETE)
    static class DataHandler1 implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Get the HTTP method(PUT, GET, DELETE) and split the URI path
            String method = exchange.getRequestMethod();
            String[] path = exchange.getRequestURI().getPath().split("/");

            // path.length -> /data/{repository} || Check if the path is valid and start with '/data'
            if(path.length < 3 || !"data".equals(path[1])) {
                exchange.sendResponseHeaders(404, -1); // 404 Not Found
                return;
            }

            // Extract the repository name & objectID from the list
            // Path : /data/{repository}/{objectID} -> Split("/") ==> [,data,{repository},{objectID}]
            String repository = path[2];
            String objectID = path.length == 4 ? path[3] : "";

            switch (method) {
                case "PUT": // Handle PUT requests (to store data)
                    handlePut(exchange, repository);
                    break;
                case "GET": // Handle GET requests (to retrieve data)
                    if(path.length == 4) {
                        handleGet(exchange, repository, objectID);
                    } else {
                        exchange.sendResponseHeaders(404, -1); // 404 Not Found
                    }
                    break;
                case "DELETE": // Handle DELETE requests (to delete data)
                    if(path.length == 4) {
                        handleDelete(exchange, repository, objectID);
                    } else {
                        exchange.sendResponseHeaders(404, -1); // 404 Not Found
                    }
                    break;
                default:
                    // Respond with 405 if method is not allowed
                    exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                    break;
            }
        }

        // Handle PUT requests to store data in the repository
        private void handlePut(HttpExchange exchange, String repository) throws IOException {
            // Read the request body (data to be stored)
            byte[] data = exchange.getRequestBody().readAllBytes();

            // Generate an objectID (SHA-1 hash of the data)
            String objectID = generateObjectID(data);
            // Ensure the repository exists -> if not, create
            repositories.putIfAbsent(repository, new HashMap<>());
            //Store the object if it doesn't already exist
            repositories.get(repository).putIfAbsent(objectID, data);

            // Respond with the objectID and data size in JSON format
            String response = String.format("{\"oid\": \"%s\", \"size\": %d}", objectID, data.length);
            exchange.sendResponseHeaders(201, response.getBytes().length); // 201 Created
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        // Handle GET requests to retrieve data from the repository
        private void handleGet(HttpExchange exchange, String repository, String objectID) throws IOException {
            // Check if the repository and object exist(repository, objcetID)
            if (repositories.containsKey(repository) && repositories.get(repository).containsKey(objectID)) {
                byte[] data = repositories.get(repository).get(objectID);

                // Respond with the data
                exchange.sendResponseHeaders(200, data.length); // 200
                OutputStream os = exchange.getResponseBody();
                os.write(data);
                os.close();
            } else {
                exchange.sendResponseHeaders(404, -1); // 200
            }
        }

        // Handle DELETE requests to delete data from the repository
        private void handleDelete(HttpExchange exchange, String repository, String objectID) throws IOException {
            // Check if the repository and object exist(repository, objcetID)
            if (repositories.containsKey(repository) && repositories.get(repository).containsKey(objectID)) {
                repositories.get(repository).remove(objectID);
                exchange.sendResponseHeaders(200, -1); // 200
            } else {
                exchange.sendResponseHeaders(404, -1); // 404
            }
        }

        // Method to generate a unique object ID using SHA-1 hash of the object data
        private String generateObjectID(byte[] data) {
            try {
                // Use SHA-1 algorithm to hash the data
                // Create a SHA-1 message digest instance
                MessageDigest digest = MessageDigest.getInstance("SHA-1");
                // Compute the SHA-1 hash of the object data
                byte[] hash = digest.digest(data);

                // Convert the hash bytes to a hexadecimal string(Hash byte -> 16)
                StringBuilder sb = new StringBuilder();
                for(byte b : hash) {
                    sb.append(String.format("%02x", b)); // Append each byte as a two-digit hex value
                }
                return sb.toString(); // Return the final hash string(Object ID)
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("SHA-1 algorithm not found");
            }
        }
    }
}
