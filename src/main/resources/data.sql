-- One ADMIN user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO authorities(id,authority) VALUES (1,'ADMIN');
INSERT INTO appusers(id,username,password,authority) VALUES (1,'admin1','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',1);

-- USER with authority player
INSERT INTO authorities(id,authority) VALUES(5,'PLAYER');

INSERT INTO appusers(id,username,password,authority) VALUES (200,'player1','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (201,'player2','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (202,'player3','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (203,'player4','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (204,'player5','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (205,'player6','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (206,'player7','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (207,'player8','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (208,'player9','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (209,'player10','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (210,'player11','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (211,'player12','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (212,'player13','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (213,'player14','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (214,'player15','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (215,'player16','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (216,'player17','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (217,'player18','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (218,'player19','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);

-- Associate PLAYERS with users
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(1,'Guillermo','Gomez Romero','https://s.hs-data.com/bilder/spieler/gross/246031.jpg',200);
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(2,'Lucas','Antoñanzas','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',201);
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(3,'Nicolas','Herrera','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',202);
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(4,'Alvaro','Bernal','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',203);
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(5,'Manuel','Orta','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',204);
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(6,'Ronald','Montoya','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',205);
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(7,'Manuel2','Orta2','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',206);
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(8,'Ronald2','Montoya2','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',207);
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(9,'Manuel3','Orta3','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',208);
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(10,'Ronald3','Montoya3','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',209);
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(11,'Manuel4','Orta4','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',210);
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(12,'Ronald4','Montoya4','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',211);
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(13,'Manuel5','Orta5','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',212);
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(14,'Ronald5','Montoya5','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',213);
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(15,'Manuel6','Orta6','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',214);
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(16,'Ronald6','Montoya6','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',215);
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(17,'Manuel7','Orta7','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',216);
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(18,'Ronald7','Montoya7','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',217);
INSERT INTO players(id,first_name,last_name,image,user_id) VALUES(19,'Manuel8','Orta8','https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg',218);


-- GAMES

-- PARTIDA 1
-- Creador: Player 1
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES (1,'QUICK_PLAY',4,2,1,80,'IN_PROGRESS');
-- Jugadores PARTIDA 1
INSERT INTO player_games(player_id, game_id) VALUES(1, 1);
INSERT INTO player_games(player_id, game_id) VALUES(13, 1);
INSERT INTO player_games(player_id, game_id) VALUES(12, 1);
INSERT INTO player_games(player_id, game_id) VALUES(10, 1);
-- RONDA DE LA PARTIDA 1
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(1,1,10,80,'PIT',1);

-- PARTIDA 2
-- Creador: Player 2
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES (2,'COMPETITIVE',3,3,2,93,'WAITING');
-- Jugadores PARTIDA 2
INSERT INTO player_games(player_id, game_id) VALUES(9, 2);
INSERT INTO player_games(player_id, game_id) VALUES(2, 2);
INSERT INTO player_games(player_id, game_id) VALUES(8, 2);

--PARTIDA 3
-- Creador: Player 6
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES (3,'QUICK_PLAY',4,3,6,135,'IN_PROGRESS');
-- Jugadores PARTIDA 3
INSERT INTO player_games(player_id, game_id) VALUES(7, 3);
INSERT INTO player_games(player_id, game_id) VALUES(6, 3);
INSERT INTO player_games(player_id, game_id) VALUES(4, 3);
INSERT INTO player_games(player_id, game_id) VALUES(5, 3);
-- RONDA DE LA PARTIDA 3
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(2,6,4,135,'INFERNAL_TOWER',3);

-- PARTIDA 4
-- Creador: Player 4
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES (4,'COMPETITIVE',2,3,4,200,'IN_PROGRESS');
--Jugadores PARTIDA 4
INSERT INTO player_games(player_id, game_id) VALUES(4, 4);
INSERT INTO player_games(player_id, game_id) VALUES(1, 4);
-- RONDA DE LA PARTIDA 4
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(3,4,1,50,'INFERNAL_TOWER',4);
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(4,4,1,100,'PIT',4);

-- PARTIDA 5
-- Creador: Player 5
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES (5,'QUICK_PLAY',3,3,5,30,'WAITING');
--Jugadores PARTIDA 5
INSERT INTO player_games(player_id, game_id) VALUES(5, 5);
INSERT INTO player_games(player_id, game_id) VALUES(14, 5);
INSERT INTO player_games(player_id, game_id) VALUES(15, 5);


-- PARTIDA 6
-- Creador: Player 9
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES (6,'QUICK_PLAY',3,3,9,30,'FINALIZED');
--Jugadores PARTIDA 6
INSERT INTO player_games(player_id, game_id) VALUES(9, 6);
INSERT INTO player_games(player_id, game_id) VALUES(10, 6);
INSERT INTO player_games(player_id, game_id) VALUES(11, 6);
-- RONDA DE LA PARTIDA 6
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(5,9,10,30,'INFERNAL_TOWER',6);

-- PARTIDA 7
-- Creador: Player 10
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES (7,'COMPETITIVE',3,3,10,30,'FINALIZED');
--Jugadores PARTIDA 7
INSERT INTO player_games(player_id, game_id) VALUES(10, 7);
INSERT INTO player_games(player_id, game_id) VALUES(11, 7);
INSERT INTO player_games(player_id, game_id) VALUES(12, 7);
-- RONDA DE LA PARTIDA 7
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(6,10,11,10,'INFERNAL_TOWER',7);
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(7,10,11,10,'PIT',7);
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(8,10,11,10,'INFERNAL_TOWER',7);

-- Inserta una registro de Hand
INSERT INTO hands(id,num_cards, round_id, player_id) VALUES
(1,6,1,1),
(2,4,2,2),
(3,4,4,4);

-- Inserta un registro de Deck
INSERT INTO decks(id, number_of_cards, round_id) VALUES 
(1, 16, 1),
(2, 44, 2),
(3, 44, 3),
(4, 44, 4);

INSERT INTO friendship(id,user_dst_id,user_source_id,status) VALUES
(1,18,19,'ACCEPTED'),
(2,18,17,'WAITING'),
(3,19,17,'ACCEPTED'),
(4,19,10,'WAITING'),
(5,19,9,'WAITING');


INSERT INTO invitations(id, destination_user, source_user, invitation_state) VALUES 
(1, 'Guille8', 'Lucas24', 'ACCEPTED'),
(2, 'Alvaro2', 'Guille12', 'PENDING'),
(3, 'Lucas2', 'Nico1', 'REFUSED');

-- Inserta un registro de Achievement
INSERT INTO achievements(id,name,description,image_url,threshold,metric) VALUES (1, 'Rookie Player', 'Play your first game!', 'https://i.imgur.com/lu5S0c2.png', 1, 'GAMES_PLAYED');
INSERT INTO achievements(id,name,description,image_url,threshold,metric) VALUES (2, 'Gaming Enthusiast', 'You´ve played 50 games!', 'https://i.imgur.com/FKUugkt.png', 50, 'GAMES_PLAYED');
INSERT INTO achievements(id,name,description,image_url,threshold,metric) VALUES (3, 'Gaming Veteran', 'Incredible, 250 games played!', 'https://i.imgur.com/qgK74bt.png', 250, 'GAMES_PLAYED');
INSERT INTO achievements(id,name,description,image_url,threshold,metric) VALUES (4, 'First Victory', 'Congratulations on your first win!', 'https://i.imgur.com/tBHOUtS.png', 1, 'VICTORIES');
INSERT INTO achievements(id,name,description,image_url,threshold,metric) VALUES (5, 'Established Competitor', '25 wins! You´re a serious competitor.', 'https://i.imgur.com/WKMLUT2.png', 25, 'VICTORIES');
INSERT INTO achievements(id,name,description,image_url,threshold,metric) VALUES (6, 'Undisputed Champion', '100 wins! You´re a legend in the game', 'https://i.imgur.com/LQp4Eje.png', 100, 'VICTORIES');
INSERT INTO achievements(id,name,description,image_url,threshold,metric) VALUES (7, 'Time Well Spent', 'You´ve played for 6 hours. What dedication!', 'https://i.imgur.com/ion4RaK.png', 6, 'TOTAL_PLAY_TIME');
INSERT INTO achievements(id,name,description,image_url,threshold,metric) VALUES (8, 'Dedicated Gamer', '24 hours of gameplay. That´s commitment!', 'https://i.imgur.com/cJoiO2z.png', 24, 'TOTAL_PLAY_TIME');
INSERT INTO achievements(id,name,description,image_url,threshold,metric) VALUES (9, 'Master of Time', 'You´ve reached 120 hours of gameplay. You´re a master of time!', 'https://i.imgur.com/kGbAaSC.png', 120, 'TOTAL_PLAY_TIME');

-- Logros de player 1
INSERT INTO player_achievements(player_id, achievement_id) VALUES (1, 1);
INSERT INTO player_achievements(player_id, achievement_id) VALUES (1, 2);
INSERT INTO player_achievements(player_id, achievement_id) VALUES (1, 4);
INSERT INTO player_achievements(player_id, achievement_id) VALUES (1, 5);
INSERT INTO player_achievements(player_id, achievement_id) VALUES (1, 7);

-- Logros de player 2
INSERT INTO player_achievements(player_id, achievement_id) VALUES (2, 1);
INSERT INTO player_achievements(player_id, achievement_id) VALUES (2, 4);
INSERT INTO player_achievements(player_id, achievement_id) VALUES (2, 7);

-- Logros de player 3
INSERT INTO player_achievements(player_id, achievement_id) VALUES (3, 1);
INSERT INTO player_achievements(player_id, achievement_id) VALUES (3, 2);
INSERT INTO player_achievements(player_id, achievement_id) VALUES (3, 4);
INSERT INTO player_achievements(player_id, achievement_id) VALUES (3, 5);
INSERT INTO player_achievements(player_id, achievement_id) VALUES (3, 6);
INSERT INTO player_achievements(player_id, achievement_id) VALUES (3, 7);
INSERT INTO player_achievements(player_id, achievement_id) VALUES (3, 8);
INSERT INTO player_achievements(player_id, achievement_id) VALUES (3, 9);

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

INSERT INTO cards(id, image, deck_id, hand_id) VALUES 
(1, 'https://i.imgur.com/pbHhrvm.jpeg',1,null),
(2, 'https://i.imgur.com/I6Id6Ta.jpeg',1,null),
(3, 'https://i.imgur.com/4kI9vYZ.jpeg',1,null),
(4, 'https://i.imgur.com/3fugva3.jpeg',null,1),
(5, 'https://i.imgur.com/Kxl8nZD.jpeg',null,1),
(6, 'https://i.imgur.com/MWUOfw1.jpeg',null,2),
(7, 'https://i.imgur.com/y3hnnjZ.jpeg',2,null),
(8, 'https://i.imgur.com/kx7n6FM.jpeg',2,null),
(9, 'https://i.imgur.com/CasQYx8.jpeg',3,null),
(10, 'https://i.imgur.com/1mC2EW8.jpeg',null,2),
(11, 'https://i.imgur.com/VswRlaY.jpeg',null,3),
(12, 'https://i.imgur.com/AfC2ihF.jpeg',null,3),
(13, 'https://i.imgur.com/ARussRh.jpeg',4,null),
(14, 'https://i.imgur.com/owic8Ou.jpeg',4,null),
(15, 'https://i.imgur.com/Rl8yaMu.jpeg',4,null),
(16, 'https://i.imgur.com/VaXKfLq.jpeg',null,3);

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

--Se declaran las relaciones de card y symbol
INSERT INTO card_symbols(card_id, symbol) VALUES(1,'DOLPHIN');
INSERT INTO card_symbols(card_id, symbol) VALUES(1,'GLASSES');
INSERT INTO card_symbols(card_id, symbol) VALUES(1,'THUNDER');
INSERT INTO card_symbols(card_id, symbol) VALUES(1,'GHOST');
INSERT INTO card_symbols(card_id, symbol) VALUES(1,'SNOWMAN');
INSERT INTO card_symbols(card_id, symbol) VALUES(1,'SHOT');

INSERT INTO card_symbols(card_id, symbol) VALUES(2,'EXCLAMATION');
INSERT INTO card_symbols(card_id, symbol) VALUES(2,'GHOST');
INSERT INTO card_symbols(card_id, symbol) VALUES(2,'HAMMER');
INSERT INTO card_symbols(card_id, symbol) VALUES(2,'ZEBRA');
INSERT INTO card_symbols(card_id, symbol) VALUES(2,'CACTUS');
INSERT INTO card_symbols(card_id, symbol) VALUES(2,'PENCIL');

INSERT INTO card_symbols(card_id, symbol) VALUES(3,'APPLE');
INSERT INTO card_symbols(card_id, symbol) VALUES(3,'GLASSES');
INSERT INTO card_symbols(card_id, symbol) VALUES(3,'BABY_BOTTLE');
INSERT INTO card_symbols(card_id, symbol) VALUES(3,'CAT');
INSERT INTO card_symbols(card_id, symbol) VALUES(3,'PENCIL');
INSERT INTO card_symbols(card_id, symbol) VALUES(3,'TURTLE');

INSERT INTO card_symbols(card_id, symbol) VALUES(4,'BABY_BOTTLE');
INSERT INTO card_symbols(card_id, symbol) VALUES(4,'LADYBUG');
INSERT INTO card_symbols(card_id, symbol) VALUES(4,'HAMMER');
INSERT INTO card_symbols(card_id, symbol) VALUES(4,'SNOWMAN');
INSERT INTO card_symbols(card_id, symbol) VALUES(4,'SPIDER');
INSERT INTO card_symbols(card_id, symbol) VALUES(4,'YIN_YAN');

INSERT INTO card_symbols(card_id, symbol) VALUES(5,'GLASSES');
INSERT INTO card_symbols(card_id, symbol) VALUES(5,'CLOVER');
INSERT INTO card_symbols(card_id, symbol) VALUES(5,'INTERROGATION');
INSERT INTO card_symbols(card_id, symbol) VALUES(5,'EXCLAMATION');
INSERT INTO card_symbols(card_id, symbol) VALUES(5,'IGLOO');
INSERT INTO card_symbols(card_id, symbol) VALUES(5,'SPIDER');

INSERT INTO card_symbols(card_id, symbol) VALUES(6,'SCISSORS');
INSERT INTO card_symbols(card_id, symbol) VALUES(6,'ZEBRA');
INSERT INTO card_symbols(card_id, symbol) VALUES(6,'APPLE');
INSERT INTO card_symbols(card_id, symbol) VALUES(6,'LADYBUG');
INSERT INTO card_symbols(card_id, symbol) VALUES(6,'SHOT');
INSERT INTO card_symbols(card_id, symbol) VALUES(6,'IGLOO');

INSERT INTO card_symbols(card_id, symbol) VALUES(7,'KEY');
INSERT INTO card_symbols(card_id, symbol) VALUES(7,'INTERROGATION');
INSERT INTO card_symbols(card_id, symbol) VALUES(7,'TURTLE');
INSERT INTO card_symbols(card_id, symbol) VALUES(7,'HAMMER');
INSERT INTO card_symbols(card_id, symbol) VALUES(7,'CHEESE');
INSERT INTO card_symbols(card_id, symbol) VALUES(7,'SHOT');

INSERT INTO card_symbols(card_id, symbol) VALUES(8,'CACTUS');
INSERT INTO card_symbols(card_id, symbol) VALUES(8,'LADYBUG');
INSERT INTO card_symbols(card_id, symbol) VALUES(8,'EYE');
INSERT INTO card_symbols(card_id, symbol) VALUES(8,'MUSIC');
INSERT INTO card_symbols(card_id, symbol) VALUES(8,'GLASSES');
INSERT INTO card_symbols(card_id, symbol) VALUES(8,'CHEESE');

INSERT INTO card_symbols(card_id, symbol) VALUES(9,'TURTLE');
INSERT INTO card_symbols(card_id, symbol) VALUES(9,'CLOVER');
INSERT INTO card_symbols(card_id, symbol) VALUES(9,'ZEBRA');
INSERT INTO card_symbols(card_id, symbol) VALUES(9,'SNOWMAN');
INSERT INTO card_symbols(card_id, symbol) VALUES(9,'MUSIC');
INSERT INTO card_symbols(card_id, symbol) VALUES(9,'DOG');

INSERT INTO card_symbols(card_id, symbol) VALUES(10,'SNOWMAN');
INSERT INTO card_symbols(card_id, symbol) VALUES(10,'CACTUS');
INSERT INTO card_symbols(card_id, symbol) VALUES(10,'APPLE');
INSERT INTO card_symbols(card_id, symbol) VALUES(10,'INTERROGATION');
INSERT INTO card_symbols(card_id, symbol) VALUES(10,'HEART');
INSERT INTO card_symbols(card_id, symbol) VALUES(10,'CLOWN');

INSERT INTO card_symbols(card_id, symbol) VALUES(11,'EXCLAMATION');
INSERT INTO card_symbols(card_id, symbol) VALUES(11,'TURTLE');
INSERT INTO card_symbols(card_id, symbol) VALUES(11,'BIRD');
INSERT INTO card_symbols(card_id, symbol) VALUES(11,'THUNDER');
INSERT INTO card_symbols(card_id, symbol) VALUES(11,'HEART');
INSERT INTO card_symbols(card_id, symbol) VALUES(11,'LADYBUG');

INSERT INTO card_symbols(card_id, symbol) VALUES(12,'WATER');
INSERT INTO card_symbols(card_id, symbol) VALUES(12,'CLOVER');
INSERT INTO card_symbols(card_id, symbol) VALUES(12,'APPLE');
INSERT INTO card_symbols(card_id, symbol) VALUES(12,'THUNDER');
INSERT INTO card_symbols(card_id, symbol) VALUES(12,'HAMMER');
INSERT INTO card_symbols(card_id, symbol) VALUES(12,'EYE');

INSERT INTO card_symbols(card_id, symbol) VALUES(13,'INTERROGATION');
INSERT INTO card_symbols(card_id, symbol) VALUES(13,'EYE');
INSERT INTO card_symbols(card_id, symbol) VALUES(13,'BIRD');
INSERT INTO card_symbols(card_id, symbol) VALUES(13,'ZEBRA');
INSERT INTO card_symbols(card_id, symbol) VALUES(13,'BABY_BOTTLE');
INSERT INTO card_symbols(card_id, symbol) VALUES(13,'DOLPHIN');

INSERT INTO card_symbols(card_id, symbol) VALUES(14,'CACTUS');
INSERT INTO card_symbols(card_id, symbol) VALUES(14,'BIRD');
INSERT INTO card_symbols(card_id, symbol) VALUES(14,'CAT');
INSERT INTO card_symbols(card_id, symbol) VALUES(14,'SHOT');
INSERT INTO card_symbols(card_id, symbol) VALUES(14,'YIN_YAN');
INSERT INTO card_symbols(card_id, symbol) VALUES(14,'CLOVER');

INSERT INTO card_symbols(card_id, symbol) VALUES(15,'KEY');
INSERT INTO card_symbols(card_id, symbol) VALUES(15,'SCISSORS');
INSERT INTO card_symbols(card_id, symbol) VALUES(15,'EYE');
INSERT INTO card_symbols(card_id, symbol) VALUES(15,'SNOWMAN');
INSERT INTO card_symbols(card_id, symbol) VALUES(15,'CAT');
INSERT INTO card_symbols(card_id, symbol) VALUES(15,'EXCLAMATION');

INSERT INTO card_symbols(card_id, symbol) VALUES(16,'BIRD');
INSERT INTO card_symbols(card_id, symbol) VALUES(16,'SCISSORS');
INSERT INTO card_symbols(card_id, symbol) VALUES(16,'GLASSES');
INSERT INTO card_symbols(card_id, symbol) VALUES(16,'DOG');
INSERT INTO card_symbols(card_id, symbol) VALUES(16,'CLOWN');
INSERT INTO card_symbols(card_id, symbol) VALUES(16,'HAMMER');

--Se declaran los registros de chats
INSERT INTO chats (id) VALUES (1);
INSERT INTO chats (id) VALUES (2);
INSERT INTO chats (id) VALUES (3);



