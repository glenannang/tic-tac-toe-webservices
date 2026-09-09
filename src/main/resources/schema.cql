USE cassandra_activity;

CREATE TABLE IF NOT EXISTS annang_user_table (
                                                 game_id uuid,
                                                 date_save timestamp,
                                                 player_id uuid,
                                                 symbol text,
                                                 location int,
                                                 PRIMARY KEY (game_id, date_save)
) WITH CLUSTERING ORDER BY (date_save ASC);

CREATE TABLE IF NOT EXISTS annang_player_games (
                                                   player_id uuid,
                                                   date_saved timestamp,
                                                   game_id uuid,
                                                   PRIMARY KEY (player_id, date_saved, game_id)
) WITH CLUSTERING ORDER BY (date_saved ASC);