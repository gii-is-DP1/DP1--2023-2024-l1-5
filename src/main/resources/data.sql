-- AUTORIDADES
INSERT INTO authorities(id, authority) VALUES 
(1,'ADMIN'),
(5,'PLAYER');


-- USUARIOS
-- Administrador
INSERT INTO appusers(id,username,password,authority) VALUES 
(1,'admin1','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',1),
-- Jugadores
(201,'player1','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(202,'player2','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(203,'player3','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(204,'player4','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(205,'player5','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(206,'player6','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(207,'player7','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(208,'player8','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(209,'player9','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(210,'player10','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(211,'player11','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5),
(212,'player12','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);


-- JUGADORES
INSERT INTO players(id, first_name, last_name, image, user_id, playerUsername) VALUES
(1, 'Guillermo', 'Gomez', 'https://s.hs-data.com/bilder/spieler/gross/246031.jpg', 201, 'guigomrom'),
(2, 'Lucas', 'Antoñanzas', 'https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg', 202, 'lucantdel'),
(3, 'Nicolas', 'Herrera', 'https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg', 203, 'nicherlob'),
(4, 'Alvaro', 'Bernal', 'https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg', 204, 'alvbercau'),
(5, 'Manuel', 'Orta', 'https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg', 205, 'manortper'),
(6, 'Ronald', 'Montoya', 'https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg', 206, 'ronmonalb'),
(7, 'Carlos', 'Muller', 'https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg', 207, 'cmuller'),
(8, 'Jose Antonio', 'Parejo', 'https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg', 208, 'japarejo'),
(9, 'Sergio', 'Ramirez', 'https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg', 209, 'seramirez'),
(10, 'Marco', 'Fernandez', 'https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg', 210, 'marfernan'),
(11, 'Jorge', 'Martinez', 'https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg', 211, 'jormartinez'),
(12, 'Eduardo', 'Lopez', 'https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg', 212, 'edulopez');


-- SOLICITUDES DE AMISTAD
-- Jugadores 11 y 12 no tienen amigos ni solicitudes de amistad
INSERT INTO friendship (id, user_dst_id, user_source_id, status) VALUES
(1, 1, 2, 'WAITING'),
(2, 1, 3, 'ACCEPTED'),
(3, 1, 4, 'WAITING'),
(4, 1, 5, 'ACCEPTED'),
(5, 2, 3, 'ACCEPTED'),
(6, 2, 4, 'WAITING'),
(7, 2, 5, 'ACCEPTED'),
(8, 2, 6, 'ACCEPTED'),
(9, 3, 4, 'ACCEPTED'),
(10, 3, 5, 'WAITING'),
(11, 3, 6, 'ACCEPTED'),
(12, 3, 7, 'WAITING'),
(13, 4, 5, 'WAITING'),
(14, 4, 6, 'ACCEPTED'),
(15, 4, 7, 'ACCEPTED'),
(16, 4, 8, 'WAITING'),
(17, 5, 6, 'WAITING'),
(18, 5, 7, 'ACCEPTED'),
(19, 5, 8, 'WAITING'),
(20, 5, 9, 'ACCEPTED'),
(21, 6, 7, 'WAITING'),
(22, 6, 8, 'WAITING'),
(23, 6, 9, 'WAITING'),
(24, 6, 10, 'ACCEPTED'),
(25, 7, 8, 'WAITING'),
(26, 7, 9, 'ACCEPTED'),
(27, 7, 10, 'WAITING'),
(28, 8, 9, 'ACCEPTED'),
(29, 8, 10, 'WAITING'),
(30, 9, 10, 'ACCEPTED');


-- LOGROS
INSERT INTO achievements(id, name, description, image_url, threshold, metric) VALUES
(1, 'Rookie Player', 'Embark on your gaming journey!', 'https://i.imgur.com/lu5S0c2.png', 1, 'GAMES_PLAYED'),
(2, 'Gaming Veteran', '5 games down, many more to go!', 'https://i.imgur.com/FKUugkt.png', 5, 'GAMES_PLAYED'),
(3, 'Gaming Enthusiast', '20 games played! A true enthusiast!', 'https://i.imgur.com/qgK74bt.png', 20, 'GAMES_PLAYED'),
(4, 'First Victory', 'Celebrate your very first win!', 'https://i.imgur.com/tBHOUtS.png', 1, 'VICTORIES'),
(5, 'Established Competitor', 'Dominating with 5 wins!', 'https://i.imgur.com/WKMLUT2.png', 5, 'VICTORIES'),
(6, 'Undisputed Champion', '10 wins! The pinnacle of victory!', 'https://i.imgur.com/LQp4Eje.png', 10, 'VICTORIES'),
(7, 'Time Well Spent', 'A quick 10 minutes of gaming mastery!', 'https://i.imgur.com/ion4RaK.png', 10, 'TOTAL_PLAY_TIME'),
(8, 'Dedicated Gamer', 'Half an hour of pure dedication!', 'https://i.imgur.com/cJoiO2z.png', 30, 'TOTAL_PLAY_TIME'),
(9, 'Master of Time', '90 minutes of epic gaming journey!', 'https://i.imgur.com/kGbAaSC.png', 90, 'TOTAL_PLAY_TIME');

-- Logros de player 1
INSERT INTO player_achievements(player_id, achievement_id) VALUES
(1, 1),
(1, 2),
(1, 4),
(1, 5),
-- Logros de player 2
(2, 1),
-- Logros de player 3 (tiene todos los logros)
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(3, 5),
(3, 6),
(3, 7),
(3, 8),
(3, 9),
-- Logros de player 4
(4, 1),
(4, 4),
(4, 7);
-- Players 5, 6, 7, 9 no tienen logros


-- CARTAS
 INSERT INTO cards(id, image) VALUES 
(1, 'https://i.imgur.com/xLo9ljN.jpg'),
(2, 'https://i.imgur.com/82KRcD2.jpg'),
(3, 'https://i.imgur.com/Cr3Rsd6.jpg'),
(4, 'https://i.imgur.com/H9vYR3Q.jpg'),
(5, 'https://i.imgur.com/JyaueG8.jpg'),
(6, 'https://i.imgur.com/5AVnmQJ.jpg'),
(7, 'https://i.imgur.com/rUClQ6k.jpg'),
(8, 'https://i.imgur.com/fDFR1Ys.jpg'),
(9, 'https://i.imgur.com/IZj6y8G.jpg'),
(10, 'https://i.imgur.com/KJKyUlM.jpg'),
(11, 'https://i.imgur.com/UUdx2ry.jpg'),
(12, 'https://i.imgur.com/ZRMCifM.jpg'),
(13, 'https://i.imgur.com/zN7pQcV.jpg'),
(14, 'https://i.imgur.com/Z4gPFid.jpg'),
(15, 'https://i.imgur.com/h8Wzpqf.jpg'),
(16, 'https://i.imgur.com/pIqoA0p.jpg');


-- SIMBOLOS
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

-- Carta 1
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(1,'DOLPHIN'),
(1,'GLASSES'),
(1,'THUNDER'),
(1,'GHOST'),
(1,'SNOWMAN'),
(1,'SHOT');

-- Carta 2
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(2,'EXCLAMATION'),
(2,'GHOST'),
(2,'HAMMER'),
(2,'ZEBRA'),
(2,'CACTUS'),
(2,'PENCIL');

-- Carta 3
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(3,'APPLE'),
(3,'GLASSES'),
(3,'BABY_BOTTLE'),
(3,'CAT'),
(3,'PENCIL'),
(3,'TURTLE');

-- Carta 4
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(4,'BABY_BOTTLE'),
(4,'LADYBUG'),
(4,'HAMMER'),
(4,'SNOWMAN'),
(4,'SPIDER'),
(4,'YIN_YAN');

-- Carta 5
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(5,'GLASSES'),
(5,'CLOVER'),
(5,'INTERROGATION'),
(5,'EXCLAMATION'),
(5,'IGLOO'),
(5,'SPIDER');

-- Carta 6
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(6,'SCISSORS'),
(6,'ZEBRA'),
(6,'APPLE'),
(6,'LADYBUG'),
(6,'SHOT'),
(6,'IGLOO');

-- Carta 7
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(7,'KEY'),
(7,'INTERROGATION'),
(7,'TURTLE'),
(7,'HAMMER'),
(7,'CHEESE'),
(7,'SHOT');

-- Carta 8
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(8,'CACTUS'),
(8,'LADYBUG'),
(8,'EYE'),
(8,'MUSIC'),
(8,'GLASSES'),
(8,'CHEESE');

-- Carta 9
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(9,'TURTLE'),
(9,'CLOVER'),
(9,'ZEBRA'),
(9,'SNOWMAN'),
(9,'MUSIC'),
(9,'DOG');

-- Carta 10
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(10,'SNOWMAN'),
(10,'CACTUS'),
(10,'APPLE'),
(10,'INTERROGATION'),
(10,'HEART'),
(10,'CLOWN');

-- Carta 11
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(11,'EXCLAMATION'),
(11,'TURTLE'),
(11,'BIRD'),
(11,'THUNDER'),
(11,'HEART'),
(11,'LADYBUG');

-- Carta 12
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(12,'WATER'),
(12,'CLOVER'),
(12,'APPLE'),
(12,'THUNDER'),
(12,'HAMMER'),
(12,'EYE');

-- Carta 13
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(13,'INTERROGATION'),
(13,'EYE'),
(13,'BIRD'),
(13,'ZEBRA'),
(13,'BABY_BOTTLE'),
(13,'DOLPHIN');

-- Carta 14
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(14,'CACTUS'),
(14,'BIRD'),
(14,'CAT'),
(14,'SHOT'),
(14,'YIN_YAN'),
(14,'CLOVER');

-- Carta 15
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(15,'KEY'),
(15,'SCISSORS'),
(15,'EYE'),
(15,'SNOWMAN'),
(15,'CAT'),
(15,'EXCLAMATION');

-- Carta 16
INSERT INTO card_symbols(card_id, symbols_name) VALUES
(16,'BIRD'),
(16,'SCISSORS'),
(16,'GLASSES'),
(16,'DOG'),
(16,'CLOWN'),
(16,'HAMMER');


-- PARTIDAS

-- Partida 1 (THE PIT en la waiting room CON mensajes SIN invitaciones)
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES 
(1,'QUICK_PLAY',2,null,1,0,'WAITING');

-- Información
INSERT INTO games_info(id, game_mode,num_players,winner_id,creator_id,game_time,game_status,game_id) VALUES 
(1,'QUICK_PLAY',2,null,1,0, 'WAITING',1);

-- Jugadores
INSERT INTO player_games(player_id, game_id) VALUES 
(1,1),
(7,1);

-- Rondas
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES 
(1,null,null,null,'PIT',1);

-- Mensajes de chat
INSERT INTO chat_messages (id, content, source_user, message_date, game_id) VALUES 
(1,'Que tal? Eres bueno?','player1','2023-02-11 11:30', 1), 
(2,'Bien, es mi primera partida','player7','2023-02-11 11:31', 1);


-- Partida 2 (INFERNAL TOWER en la waiting room SIN mensajes CON invitaciones)
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES 
(2,'QUICK_PLAY',2,null,2,0,'WAITING');

-- Información
INSERT INTO games_info(id, game_mode,num_players,winner_id,creator_id,game_time,game_status,game_id) VALUES 
(2,'QUICK_PLAY',2,null,5,0,'WAITING',2);

-- Jugadores
INSERT INTO player_games(player_id, game_id) VALUES 
(2,2),
(5,2);

-- Rondas
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES 
(2,null,null,null,'INFERNAL_TOWER',2);

-- Invitaciones
INSERT INTO invitations(id, destination_user, source_user, invitation_state, game_id) VALUES 
(1, 'manortper', 'lucantdel', 'ACCEPTED',2),
(2, 'ronmonalb', 'lucantdel', 'PENDING',2);


-- Partida 3 (THE PIT en progreso)
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES 
(3,'QUICK_PLAY',3,null,3,30,'IN_PROGRESS');

-- Información
INSERT INTO games_info(id, game_mode,num_players,winner_id,creator_id,game_time,game_status,game_id) VALUES 
(3,'QUICK_PLAY',3,null,3,30, 'IN_PROGRESS',3);

-- Jugadores
INSERT INTO player_games(player_id, game_id) VALUES 
(3,3),
(6,3),
(9,3);

-- Rondas
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES 
(3,null,null,null,'PIT',3);

-- Mazos
INSERT INTO decks(id,number_of_cards,round_id) VALUES
(1,1,3);
INSERT INTO decks_cards(cards_id, deck_id) VALUES
(1,1);

-- Manos
INSERT INTO hands(id,num_cards,player_id,round_id) VALUES
(2,1,3,3),
(3,1,6,3),
(4,1,9,3);
INSERT INTO hands_cards(cards_id, hand_id) VALUES
(2,2),
(3,3),
(4,4);

-- Partida 4 (INFERNAL TOWER en progreso)
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES 
(4,'QUICK_PLAY',2,null,4,40,'IN_PROGRESS');

-- Información
INSERT INTO games_info(id, game_mode,num_players,winner_id,creator_id,game_time,game_status,game_id) VALUES 
(4,'QUICK_PLAY',2,null,4,40, 'IN_PROGRESS',4);

-- Jugadores
INSERT INTO player_games(player_id, game_id) VALUES 
(4,4),
(8,4);

-- Rondas
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES 
(4,null,null,null,'INFERNAL_TOWER',4);

-- Mazos
INSERT INTO decks(id,number_of_cards,round_id) VALUES
(2,1,4);
INSERT INTO decks_cards(cards_id, deck_id) VALUES
(5,2);

-- Manos
INSERT INTO hands(id,num_cards,player_id,round_id) VALUES
(5,1,4,4),
(6,1,8,4);
INSERT INTO hands_cards(cards_id, hand_id) VALUES
(6,5),
(7,6);

 -- Partida 5 (THE PIT finalizada)
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES 
(5,'QUICK_PLAY',5,5,5,100,'FINALIZED');

-- Información
INSERT INTO games_info(id, game_mode,num_players,winner_id,creator_id,game_time,game_status,game_id) VALUES 
(5,'QUICK_PLAY',5,5,5,100, 'FINALIZED',5);

-- Jugadores
INSERT INTO player_games(player_id, game_id) VALUES 
(5,5),
(1,5),
(3,5),
(7,5),
(9,5);

-- Rondas
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES 
(5,5,9,100,'PIT',5);

-- Partida 6 (INFERNAL TOWER finalizada)
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES 
(6,'QUICK_PLAY',5,6,6,120,'FINALIZED');

-- Información
INSERT INTO games_info(id, game_mode,num_players,winner_id,creator_id,game_time,game_status,game_id) VALUES 
(6,'QUICK_PLAY',5,6,6,120, 'FINALIZED',6);

-- Jugadores
INSERT INTO player_games(player_id, game_id) VALUES 
(6,6),
(2,6),
(4,6),
(8,6),
(10,6);

-- Rondas
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES 
(6,6,10,null,'INFERNAL_TOWER',6);
