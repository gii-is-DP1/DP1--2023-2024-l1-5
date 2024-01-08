-- One ADMIN user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO authorities(id,authority) VALUES (1,'ADMIN');
INSERT INTO appusers(id,username,password,authority) VALUES (1,'admin1','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',1);

-- USER with authority player
INSERT INTO authorities(id,authority) VALUES(5,'PLAYER');

INSERT INTO appusers(id,username,password,authority) VALUES 
(200,'player1','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(201,'player2','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(202,'player3','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(203,'player4','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(204,'player5','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(205,'player6','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(206,'player7','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(207,'player8','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(208,'player9','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(209,'player10','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(210,'player11','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(211,'player12','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(212,'player13','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(213,'player14','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(214,'player15','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(215,'player16','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(216,'player17','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(217,'player18','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(218,'player19','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(219,'player20','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(220,'player21','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(221,'player22','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);


-- Associate PLAYERS with users
INSERT INTO players(id,first_name,last_name,image,user_id,playerUsername) VALUES
(1,'Guillermo','Gomez Romero','https://s.hs-data.com/bilder/spieler/gross/246031.jpg',200,'gilly123'),
(2,'Lucas','Antoñanzas','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',201,'luca_23'),
(3,'Nicolas','Herrera','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',202,'nickH_77'),
(4,'Alvaro','Bernal','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',203,'Alvaro2'),
(5,'Manuel','Orta','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',204,'manuOrta89'),
(6,'Ronald','Montoya','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',205,'ron_M123'),
(7,'Manuel2','Orta2','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',206,'mOrta_2'),
(8,'Ronald2','Montoya2','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',207,'ronM_88'),
(9,'Manuel3','Orta3','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',208,'manuelOrt_7'),
(10,'Ronald3','Montoya3','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',209,'rM_456'),
(11,'Manuel4','Orta4','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',210,'manuOrta_4'),
(12,'Ronald4','Montoya4','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',211,'ronaldM_11'),
(13,'Manuel5','Orta5','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',212,'m_orta_5'),
(14,'Ronald5','Montoya5','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',213,'ron_55'),
(15,'Manuel6','Orta6','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',214,'mOrt_66'),
(16,'Ronald6','Montoya6','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',215,'rM_696'),
(17,'Manuel7','Orta7','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',216,'manuel_orta'),
(18,'Ronald7','Montoya7','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',217,'ronM_7'),
(19,'prueba1','Orta8','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',218,'mOrta_88'),
(20,'prueba2','Orta8','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',219,'mOrta_8'),
(21,'prueba3','Orta8','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',220,'mOrta_'),
(22,'preba4','Orta8','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',221,'mOrta');


-- GAMES

-- PARTIDA 1
-- Creador: Player 1
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES (1,'QUICK_PLAY',4,2,1,80,'IN_PROGRESS');
-- --- INFORMACION DE LA PARTIDA 1
INSERT INTO games_info(id, game_mode, num_players, winner_id, creator_id, game_time, game_status, game_id) 
VALUES (1, 'QUICK_PLAY', 4, 2, 1, 80, 'IN_PROGRESS', 1);

-- -- Jugadores PARTIDA 1
INSERT INTO player_games(player_id, game_id) VALUES(1, 1);
INSERT INTO player_games(player_id, game_id) VALUES(13, 1);
INSERT INTO player_games(player_id, game_id) VALUES(12, 1);
INSERT INTO player_games(player_id, game_id) VALUES(10, 1);
-- -- RONDA DE LA PARTIDA 1
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(1,1,10,80,'PIT',1);

-- -- PARTIDA 2
-- -- Creador: Player 2
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES (2,'COMPETITIVE',3,3,2,93,'WAITING');
-- -- Jugadores PARTIDA 2
INSERT INTO player_games(player_id, game_id) VALUES(9, 2);
INSERT INTO player_games(player_id, game_id) VALUES(2, 2);
INSERT INTO player_games(player_id, game_id) VALUES(8, 2);

-- --PARTIDA 3
-- -- Creador: Player 6
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES (3,'QUICK_PLAY',4,3,6,135,'IN_PROGRESS');
-- -- Jugadores PARTIDA 3
INSERT INTO player_games(player_id, game_id) VALUES(7, 3);
INSERT INTO player_games(player_id, game_id) VALUES(6, 3);
INSERT INTO player_games(player_id, game_id) VALUES(4, 3);
INSERT INTO player_games(player_id, game_id) VALUES(5, 3);
-- -- RONDA DE LA PARTIDA 3
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(2,6,4,135,'INFERNAL_TOWER',3);

-- -- PARTIDA 4
-- -- Creador: Player 4
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES (4,'COMPETITIVE',2,3,4,200,'IN_PROGRESS');
-- --Jugadores PARTIDA 4
INSERT INTO player_games(player_id, game_id) VALUES(4, 4);
INSERT INTO player_games(player_id, game_id) VALUES(16, 4);
-- -- RONDA DE LA PARTIDA 4
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(3,4,1,50,'INFERNAL_TOWER',4);
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(4,4,1,100,'PIT',4);

-- -- PARTIDA 5
-- -- Creador: Player 5
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES (5,'QUICK_PLAY',3,3,5,30,'WAITING');
-- --Jugadores PARTIDA 5
INSERT INTO player_games(player_id, game_id) VALUES(5, 5);
INSERT INTO player_games(player_id, game_id) VALUES(14, 5);
INSERT INTO player_games(player_id, game_id) VALUES(15, 5);


-- -- PARTIDA 6
-- -- Creador: Player 9
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES (6,'QUICK_PLAY',3,3,9,30,'FINALIZED');
-- --Jugadores PARTIDA 6
INSERT INTO player_games(player_id, game_id) VALUES(9, 6);
INSERT INTO player_games(player_id, game_id) VALUES(10, 6);
INSERT INTO player_games(player_id, game_id) VALUES(11, 6);
-- -- RONDA DE LA PARTIDA 6
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(5,9,10,30,'INFERNAL_TOWER',6);

-- -- PARTIDA 7
-- -- Creador: Player 10
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES (7,'COMPETITIVE',3,3,10,30,'FINALIZED');
-- --Jugadores PARTIDA 7
INSERT INTO player_games(player_id, game_id) VALUES(10, 7);
INSERT INTO player_games(player_id, game_id) VALUES(11, 7);
INSERT INTO player_games(player_id, game_id) VALUES(12, 7);
-- -- RONDA DE LA PARTIDA 7
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(6,10,11,10,'INFERNAL_TOWER',7);
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(7,10,11,10,'PIT',7);
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(8,10,11,10,'INFERNAL_TOWER',7);

INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES (8,'QUICK_PLAY',3,14,13,30,'WAITING');

INSERT INTO player_games(player_id, game_id) VALUES(18, 8);
INSERT INTO player_games(player_id, game_id) VALUES(19, 8);


INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(9,10,11,10,'PIT',8);
--Inserta un registro de Deck
INSERT INTO decks(id, number_of_cards) VALUES 
(1, 16),
(2, 44),
(3, 44),
(4, 44),
(5, 44);

INSERT INTO friendship(id,user_dst_id,user_source_id,status) VALUES
(1, 18, 19, 'ACCEPTED'), (2, 18, 17, 'WAITING'), (3, 19, 17, 'ACCEPTED'), (4, 19, 10, 'WAITING'),
(5, 19, 9, 'WAITING'), (6, 1, 19, 'ACCEPTED'), (7, 2, 19, 'ACCEPTED'),
(14, 10, 1, 'ACCEPTED'), (15, 11, 1, 'WAITING'), (16, 12, 1, 'ACCEPTED'), 
(17, 13, 1, 'WAITING'), (18, 14, 1, 'ACCEPTED'), (19, 15, 1, 'WAITING'),
(20, 10, 2, 'ACCEPTED'), (21, 11, 2, 'WAITING'), (22, 12, 2, 'ACCEPTED'), 
(23, 13, 2, 'WAITING'), (24, 14, 2, 'ACCEPTED'), (25, 15, 2, 'WAITING'),
(26, 10, 3, 'ACCEPTED'), (27, 11, 3, 'ACCEPTED'), (28, 12, 3, 'WAITING'), 
(29, 13, 3, 'ACCEPTED'), (30, 14, 3, 'WAITING'), (31, 15, 3, 'ACCEPTED'),
(32, 10, 4, 'ACCEPTED'), (33, 11, 4, 'ACCEPTED'), (34, 12, 4, 'WAITING'), 
(35, 13, 4, 'WAITING'), (36, 14, 4, 'WAITING'), (37, 15, 4, 'ACCEPTED'),
(38, 10, 5, 'WAITING'), (39, 11, 5, 'WAITING'), (40, 12, 5, 'ACCEPTED'), 
(41, 13, 5, 'WAITING'), (42, 14, 5, 'ACCEPTED'), (43, 15, 5, 'WAITING'),
(44, 10, 6, 'ACCEPTED'), (45, 11, 6, 'WAITING'), (46, 12, 6, 'ACCEPTED'), 
(47, 13, 6, 'ACCEPTED'), (48, 14, 6, 'ACCEPTED'), (49, 15, 6, 'ACCEPTED');



INSERT INTO invitations(id, destination_user, source_user, invitation_state, game_id) VALUES 
(1, 'Guille8', 'Lucas24', 'ACCEPTED',1),
(2, 'Alvaro2', 'Guille12', 'PENDING',1),
(3, 'Lucas2', 'Nico1', 'REFUSED',1);

-- Inserta un registro de Achievement
INSERT INTO achievements(id, name, description, image_url, threshold, metric) VALUES
(1, 'Rookie Player', 'Play your first game!', 'https://i.imgur.com/lu5S0c2.png', 1, 'GAMES_PLAYED'),
(2, 'Gaming Enthusiast', 'You´ve played 50 games!', 'https://i.imgur.com/FKUugkt.png', 50, 'GAMES_PLAYED'),
(3, 'Gaming Veteran', 'Incredible, 250 games played!', 'https://i.imgur.com/qgK74bt.png', 250, 'GAMES_PLAYED'),
(4, 'First Victory', 'Congratulations on your first win!', 'https://i.imgur.com/tBHOUtS.png', 1, 'VICTORIES'),
(5, 'Established Competitor', '25 wins! You´re a serious competitor.', 'https://i.imgur.com/WKMLUT2.png', 25, 'VICTORIES'),
(6, 'Undisputed Champion', '100 wins! You´re a legend in the game', 'https://i.imgur.com/LQp4Eje.png', 100, 'VICTORIES'),
(7, 'Time Well Spent', 'You´ve played for 6 hours. What dedication!', 'https://i.imgur.com/ion4RaK.png', 6, 'TOTAL_PLAY_TIME'),
(8, 'Dedicated Gamer', '24 hours of gameplay. That´s commitment!', 'https://i.imgur.com/cJoiO2z.png', 24, 'TOTAL_PLAY_TIME'),
(9, 'Master of Time', 'You´ve reached 120 hours of gameplay. You´re a master of time!', 'https://i.imgur.com/kGbAaSC.png', 120, 'TOTAL_PLAY_TIME');


-- Logros de player 1
INSERT INTO player_achievements(player_id, achievement_id) 
VALUES
(1, 1),
(1, 2),
(1, 4),
(1, 5),
(1, 7);

-- Logros de player 2
INSERT INTO player_achievements(player_id, achievement_id) 
VALUES
(2, 1),
(2, 4),
(2, 7);

-- Logros de player 3
INSERT INTO player_achievements(player_id, achievement_id) 
VALUES
(3, 1),
(3, 2),
(3, 4),
(3, 5),
(3, 6),
(3, 7),
(3, 8),
(3, 9);


-- Inserta un registro de PlayerStatistic
INSERT INTO player_statistics (id, number_of_games, win_number, lose_number, competitive_points,avg_duration, max_duration, min_duration) VALUES 
(1, 10, 5, 5, 0, 250, 120, 10),
(3, 100, 80, 20, 1000, 320, 400, 180),
(2, 15, 10, 5, 50, 280, 150, 20);

-- Inserta un registro de GameStatistic
INSERT INTO game_statistics (id, result, game_duration, points) VALUES 
(1, 'alvarobernal2412', 120, 14),
(2, 'ronaldDinamita', 150, 22),
(3, 'nicherlob', 180, 12);

-- Inserta un registro de Message
INSERT INTO messages (id, content, source_user, message_date) VALUES (1, 'Hola Buenos Dias','guillecoria','2023-02-24 12:30' );
INSERT INTO messages (id, content, source_user, message_date) VALUES (2,'Que calor hace!!!' ,'lucas69','2023-04-11 11:30' );

 INSERT INTO cards(id, image) VALUES 
(1, 'https://i.imgur.com/pbHhrvm.jpeg'),
(2, 'https://i.imgur.com/I6Id6Ta.jpeg'),
(3, 'https://i.imgur.com/4kI9vYZ.jpeg'),
(4, 'https://i.imgur.com/3fugva3.jpeg'),
(5, 'https://i.imgur.com/Kxl8nZD.jpeg'),
(6, 'https://i.imgur.com/MWUOfw1.jpeg'),
(7, 'https://i.imgur.com/y3hnnjZ.jpeg'),
(8, 'https://i.imgur.com/kx7n6FM.jpeg'),
(9, 'https://i.imgur.com/CasQYx8.jpeg'),
(10, 'https://i.imgur.com/1mC2EW8.jpeg'),
(11, 'https://i.imgur.com/VswRlaY.jpeg'),
(12, 'https://i.imgur.com/AfC2ihF.jpeg'),
(13, 'https://i.imgur.com/ARussRh.jpeg'),
(14, 'https://i.imgur.com/owic8Ou.jpeg'),
(15, 'https://i.imgur.com/Rl8yaMu.jpeg'),
(16, 'https://i.imgur.com/VaXKfLq.jpeg');
--Se han declarado todos los simbolos
INSERT INTO symbols(name) VALUES
('DOLPHIN'),
('GLASSES'),
('THUNDER'),
('GHOST'),
('SNOWMAN'),
('SHOT'),
('EXCLAMATION'),
('ZEBRA'),
('PENCIL'),
('HAMMER'),
('CACTUS'),
('CAT'),
('TURTLE'),
('APPLE'),
('BABY_BOTTLE'),
('SPIDER'),
('YIN_YAN'),
('LADYBUG'),
('IGLOO'),
('INTERROGATION'),
('CLOVER'),
('SCISSORS'),
('KEY'),
('CHEESE'),
('EYE'),
('MUSIC'),
('DOG'),
('HEART'),
('CLOWN'),
('BIRD'),
('WATER');

--Carta ID: 1
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(1,'DOLPHIN'),
(1,'GLASSES'),
(1,'THUNDER'),
(1,'GHOST'),
(1,'SNOWMAN'),
(1,'SHOT');

-- Carta ID: 2
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(2,'EXCLAMATION'),
(2,'GHOST'),
(2,'HAMMER'),
(2,'ZEBRA'),
(2,'CACTUS'),
(2,'PENCIL');

-- Carta ID: 3
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(3,'APPLE'),
(3,'GLASSES'),
(3,'BABY_BOTTLE'),
(3,'CAT'),
(3,'PENCIL'),
(3,'TURTLE');

-- Carta ID: 4
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(4,'BABY_BOTTLE'),
(4,'LADYBUG'),
(4,'HAMMER'),
(4,'SNOWMAN'),
(4,'SPIDER'),
(4,'YIN_YAN');

-- Carta ID: 5
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(5,'GLASSES'),
(5,'CLOVER'),
(5,'INTERROGATION'),
(5,'EXCLAMATION'),
(5,'IGLOO'),
(5,'SPIDER');

-- Carta ID: 6
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(6,'SCISSORS'),
(6,'ZEBRA'),
(6,'APPLE'),
(6,'LADYBUG'),
(6,'SHOT'),
(6,'IGLOO');

-- Carta ID: 7
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(7,'KEY'),
(7,'INTERROGATION'),
(7,'TURTLE'),
(7,'HAMMER'),
(7,'CHEESE'),
(7,'SHOT');

-- Carta ID: 8
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(8,'CACTUS'),
(8,'LADYBUG'),
(8,'EYE'),
(8,'MUSIC'),
(8,'GLASSES'),
(8,'CHEESE');

-- Carta ID: 9
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(9,'TURTLE'),
(9,'CLOVER'),
(9,'ZEBRA'),
(9,'SNOWMAN'),
(9,'MUSIC'),
(9,'DOG');

-- Carta ID: 10
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(10,'SNOWMAN'),
(10,'CACTUS'),
(10,'APPLE'),
(10,'INTERROGATION'),
(10,'HEART'),
(10,'CLOWN');

-- Carta ID: 11
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(11,'EXCLAMATION'),
(11,'TURTLE'),
(11,'BIRD'),
(11,'THUNDER'),
(11,'HEART'),
(11,'LADYBUG');

-- Carta ID: 12
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(12,'WATER'),
(12,'CLOVER'),
(12,'APPLE'),
(12,'THUNDER'),
(12,'HAMMER'),
(12,'EYE');

-- Carta ID: 13
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(13,'INTERROGATION'),
(13,'EYE'),
(13,'BIRD'),
(13,'ZEBRA'),
(13,'BABY_BOTTLE'),
(13,'DOLPHIN');

-- Carta ID: 14
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(14,'CACTUS'),
(14,'BIRD'),
(14,'CAT'),
(14,'SHOT'),
(14,'YIN_YAN'),
(14,'CLOVER');

-- Carta ID: 15
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(15,'KEY'),
(15,'SCISSORS'),
(15,'EYE'),
(15,'SNOWMAN'),
(15,'CAT'),
(15,'EXCLAMATION');

-- Carta ID: 16
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(16,'BIRD'),
(16,'SCISSORS'),
(16,'GLASSES'),
(16,'DOG'),
(16,'CLOWN'),
(16,'HAMMER');

--Se declaran los registros de chats
INSERT INTO chats (id) VALUES (1);
INSERT INTO chats (id) VALUES (2);
INSERT INTO chats (id) VALUES (3);



