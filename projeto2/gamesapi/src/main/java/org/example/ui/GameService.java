package org.example.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Game;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class GameService {

    private static final String API_URL = "http://localhost:8080/games";
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Game> getGames() {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(API_URL).openConnection();
            conn.setRequestMethod("GET");

            InputStream response = conn.getInputStream();
            Game[] games = mapper.readValue(response, Game[].class);

            return Arrays.asList(games);

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public void addGame(Game game) {
        sendWithBody("POST", API_URL, game);
    }

    public void updateGame(Long id, Game game) {
        sendWithBody("PUT", API_URL + "/" + id, game);
    }

    public void deleteGame(Long id) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(API_URL + "/" + id).openConnection();
            conn.setRequestMethod("DELETE");
            conn.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendWithBody(String method, String urlStr, Game game) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();

            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String json = mapper.writeValueAsString(game);

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();

            conn.getInputStream();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}