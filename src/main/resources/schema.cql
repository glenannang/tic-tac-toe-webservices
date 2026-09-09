USE cassandra_activity;

CREATE TABLE IF NOT EXISTS annang_user_table (
                                                 game_id uuid,
                                                 date_save timestamp,
                                                 player_id uuid,
                                                 symbol text,
                                                 location int,
                                                 PRIMARY KEY (game_id, date_save)
) WITH CLUSTERING ORDER BY (date_save ASC);