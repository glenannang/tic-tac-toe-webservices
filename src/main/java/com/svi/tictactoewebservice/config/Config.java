package com.svi.tictactoewebservice.config;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public final class Config {

    private static final Properties properties = new Properties();

    private Config() {}

    public static void load(InputStream input) throws IOException {
        properties.load(input);
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public enum Keys {
        RECORDS_DIR("RECORDS_DIR"),
        PLAYER_DIR("PLAYER_DIR"),
        GAME_DIR("GAME_DIR"),
        ROOM_DIR("ROOM_DIR"),
        FRONTEND_URL("FRONTEND_URL");

        private final String value;

        Keys(String value) {this.value = value;}

        public String value() {return value;}
    }
}