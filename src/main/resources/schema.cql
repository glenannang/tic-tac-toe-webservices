USE batch1_2026_trainees;

CREATE TABLE IF NOT EXISTS annang_moves_table (
                                                 game_id uuid,
                                                 date_save timestamp,
                                                 player_id uuid,
                                                 symbol text,
                                                 location int,
                                                 PRIMARY KEY (game_id, date_save)
) WITH CLUSTERING ORDER BY (date_save ASC);

CREATE TABLE IF NOT EXISTS annang_player_games_table (
                                                   player_id uuid,
                                                   date_saved timestamp,
                                                   game_id uuid,
                                                   PRIMARY KEY (player_id, date_saved)
) WITH CLUSTERING ORDER BY (date_saved ASC);


CREATE TABLE IF NOT EXISTS annang_room_games_table (
                                                 room_code text,
                                                 date_saved timestamp,
                                                 game_id uuid,
                                                 PRIMARY KEY (room_code, date_saved)
) WITH CLUSTERING ORDER BY (date_saved ASC);

