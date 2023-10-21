    -- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO authorities(id,authority) VALUES (1,'ADMIN');
INSERT INTO appusers(id,username,password,authority) VALUES (1,'admin1','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',1);

-- Three clinic owners, with password "clinic_owner"
INSERT INTO authorities(id,authority) VALUES (2,'CLINIC_OWNER');
INSERT INTO appusers(id,username,password,authority) VALUES (2,'clinicOwner1','$2a$10$t.I/C4cjUdUWzqlFlSddLeh9SbZ6d8wR7mdbeIRghT355/KRKZPAi',2);
INSERT INTO appusers(id,username,password,authority) VALUES (3,'clinicOwner2','$2a$10$t.I/C4cjUdUWzqlFlSddLeh9SbZ6d8wR7mdbeIRghT355/KRKZPAi',2);

INSERT INTO clinic_owners(id,first_name,last_name,user_id) VALUES (1, 'John', 'Doe', 2);
INSERT INTO clinic_owners(id,first_name,last_name,user_id) VALUES (2, 'Jane', 'Doe', 3);

INSERT INTO clinics(id, name, address, telephone, plan, clinic_owner) VALUES (1, 'Clinic 1', 'Av. Palmera, 26', '955684230', 'PLATINUM', 1);
INSERT INTO clinics(id, name, address, telephone, plan, clinic_owner) VALUES (2, 'Clinic 2', 'Av. Torneo, 52', '955634232', 'GOLD', 2);
INSERT INTO clinics(id, name, address, telephone, plan, clinic_owner) VALUES (3, 'Clinic 3', 'Av. Reina Mercedes, 70', '955382238', 'BASIC', 2);

-- Ten owner user, named owner1 with passwor 0wn3r
INSERT INTO authorities(id,authority) VALUES (3,'OWNER');
INSERT INTO appusers(id,username,password,authority) VALUES (4,'owner1','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (5,'owner2','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (6,'owner3','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (7,'owner4','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (8,'owner5','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (9,'owner6','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (10,'owner7','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (11,'owner8','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (12,'owner9','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (13,'owner10','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (20,'alvbercau','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (21,'nicherlob','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (22,'ronmonal','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (23,'manortper1','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (24,'lucantdel','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (25,'guigonron','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);

-- One vet user, named vet1 with passwor v3t
/*INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (12,'vet1','veterinarian');*/
INSERT INTO authorities(id,authority) VALUES (4,'VET');
INSERT INTO appusers(id,username,password,authority) VALUES (14,'vet1','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',4);
INSERT INTO appusers(id,username,password,authority) VALUES (15,'vet2','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',4);
INSERT INTO appusers(id,username,password,authority) VALUES (16,'vet3','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',4);
INSERT INTO appusers(id,username,password,authority) VALUES (17,'vet4','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',4);
INSERT INTO appusers(id,username,password,authority) VALUES (18,'vet5','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',4);
INSERT INTO appusers(id,username,password,authority) VALUES (19,'vet6','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',4);

INSERT INTO authorities(id,authority) VALUES(5,'PLAYER');
INSERT INTO appusers(id,username,password,authority) VALUES (200,'player0','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (201,'player1','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);
INSERT INTO appusers(id,username,password,authority) VALUES (202,'player2','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',5);

INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES (1,'QUICK_PLAY',4,2,3,80,'IN_PROGRESS');
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES (2,'COMPETITIVE',3,3,4,93,'WAITING');
INSERT INTO games(id,game_mode,num_players,winner_id,creator_id,game_time,game_status) VALUES (3,'QUICK_PLAY',4,3,2,135,'FINALIZED');

INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(1,2,3,'00:02:30','PIT',1);
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(2,3,4,'00:01:48','INFERNAL_TOWER',2);
INSERT INTO rounds(id,winner_id,loser_id,round_time,round_mode,game_id) VALUES(3,4,5,'00:01:15','PIT',1);

INSERT INTO players(id,user_id) VALUES(1,200);
INSERT INTO players(id,user_id) VALUES(2,201);
INSERT INTO players(id,user_id,round_id) VALUES(3,202,2);

INSERT INTO players_friends_list(friends_list_id,player_id) VALUES(1,2);
INSERT INTO players_friends_list(friends_list_id,player_id) VALUES(1,3);

INSERT INTO card(id,image) VALUES(1,'https://i.imgur.com/1Q2Qj1M.png');

INSERT INTO symbol(name) VALUES('DOG');
INSERT INTO symbol(name) VALUES('IGLOO');
INSERT INTO symbol(name) VALUES('CLOWN');

INSERT INTO hand(id,num_cartas) VALUES(1,6);




INSERT INTO friend_requests(id, user_source, user_dst, friend_request_status) VALUES(1, 'Lucas69', 'xleunam', 'WAITING');
INSERT INTO friend_requests(id, user_source, user_dst, friend_request_status) VALUES(2, 'Lucas70', 'xleunam2', 'ACCEPTED');
INSERT INTO friend_requests(id, user_source, user_dst, friend_request_status) VALUES(3, 'Lucas71', 'xleunam1', 'DENIED');


INSERT INTO vets(id, first_name,last_name,city, clinic, user_id) VALUES (1, 'James', 'Carter','Sevilla', 1, 14);
INSERT INTO vets(id, first_name,last_name,city, clinic, user_id) VALUES (2, 'Helen', 'Leary','Sevilla', 1, 15);
INSERT INTO vets(id, first_name,last_name,city, clinic, user_id) VALUES (3, 'Linda', 'Douglas','Sevilla', 2, 16);
INSERT INTO vets(id, first_name,last_name,city, clinic, user_id) VALUES (4, 'Rafael', 'Ortega','Badajoz', 2, 17);
INSERT INTO vets(id, first_name,last_name,city, clinic, user_id) VALUES (5, 'Henry', 'Stevens','Badajoz', 3, 18);
INSERT INTO vets(id, first_name,last_name,city, clinic, user_id) VALUES (6, 'Sharon', 'Jenkins','Cádiz', 3, 19);

INSERT INTO specialties(id,name) VALUES (1, 'radiology');
INSERT INTO specialties(id,name) VALUES (2, 'surgery');
INSERT INTO specialties(id,name) VALUES (3, 'dentistry');

INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (2, 1);
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (3, 2);
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (3, 3);
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (4, 2);
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (5, 1);

INSERT INTO types(id,name)  VALUES (1, 'cat');
INSERT INTO types(id,name)  VALUES (2, 'dog');
INSERT INTO types(id,name)  VALUES (3, 'lizard');
INSERT INTO types(id,name)  VALUES (4, 'snake');
INSERT INTO types(id,name)  VALUES (5, 'bird');
INSERT INTO types(id,name)  VALUES (6, 'hamster');
INSERT INTO types(id,name)  VALUES (7, 'turtle');

INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Sevilla', '608555103', 4, 1);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sevilla', '608555174', 5, 1);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'Sevilla', '608558763', 6, 1);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Sevilla', '608555319', 7, 2);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Sevilla', '608555765', 8, 2);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Badajoz', '608555264', 9, 2);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Badajoz', '608555538', 10, 3);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Badajoz', '608557683', 11, 3);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail','Cádiz', '685559435', 12, 3);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Cádiz', '685555487', 13, 1);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (11, 'Álvaro', 'Bernal', '2335 Independence La.', 'Cádiz', '685555487', 20, 1);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (12, 'Nicolas', 'Herrera', '2335 Independence La.', 'Cádiz', '685555487', 21, 1);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (13, 'Ronald', 'Montoya', '2335 Independence La.', 'Cádiz', '685555487', 22, 1);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (14, 'Manuel', 'Orta', '2335 Independence La.', 'Cádiz', '685555487', 23, 1);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (15, 'Lucas', 'Antoñanzas', '2335 Independence La.', 'Cádiz', '685555487', 24, 1);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (16, 'Guillermo', 'Gonzalez', '2335 Independence La.', 'Cádiz', '685555487', 25, 1);

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Carl', '2012-06-08', 1, 11);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (14, 'Pul', '2012-06-08', 1, 12);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (15, 'Pel', '2012-06-08', 1, 13);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (16, 'Pil', '2012-06-08', 1, 14);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (17, 'Pol', '2012-06-08', 1, 15);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (18, 'Pal', '2012-06-08', 1, 16);

INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (1, 7, '2013-01-01 13:00', 'rabies shot', 4);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (2, 8, '2013-01-02 15:30', 'rabies shot', 5);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (3, 8, '2013-01-03 9:45', 'neutered', 5);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (4, 7, '2013-01-04 17:30', 'spayed', 4);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (5, 1, '2013-01-01 13:00', 'rabies shot', 1);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (6, 1, '2020-01-02 15:30', 'rabies shot', 1);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (7, 1, '2020-01-02 15:30', 'rabies shot', 1);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (8, 2, '2013-01-03 9:45', 'neutered', 2);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (9, 3, '2013-01-04 17:30', 'spayed', 3);

INSERT INTO consultations(id,title, is_clinic_comment,status,owner_id,pet_id,creation_date) VALUES (1, 'Consultation about vaccines', 0, 'ANSWERED', 1, 1, '2023-01-04 17:30');
INSERT INTO consultations(id,title, is_clinic_comment,status,owner_id,pet_id,creation_date) VALUES (2, 'My dog gets really nervous', 0, 'PENDING', 1, 1, '2022-01-02 19:30');
INSERT INTO consultations(id,title, is_clinic_comment,status,owner_id,pet_id,creation_date) VALUES (3, 'My cat does not eat', 0, 'PENDING', 2, 2, '2023-04-11 11:20');
INSERT INTO consultations(id,title, is_clinic_comment,status,owner_id,pet_id,creation_date) VALUES (4, 'My lovebird does not sing', 0, 'CLOSED', 2, 2, '2023-02-24 10:30');
INSERT INTO consultations(id,title, is_clinic_comment,status,owner_id,pet_id,creation_date) VALUES (5, 'My snake has layed eggs', 0, 'PENDING', 10, 12, '2023-04-11 11:20');

INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (1, 'What vaccine should my dog receive?', '2023-01-04 17:32', 4, 1);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (2, 'Rabies'' one.', '2023-01-04 17:36', 14, 1);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (3, 'My dog gets really nervous during football matches. What should I do?', '2022-01-02 19:31', 4, 2);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (4, 'It also happens with tennis matches.', '2022-01-02 19:33', 4, 2);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (5, 'My cat han''t been eating his fodder.', '2023-04-11 11:30', 5, 3);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (6, 'Try to give him some tuna to check if he eats that.', '2023-04-11 15:20', 15, 3);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (7, 'My lovebird doesn''t sing as my neighbour''s one.', '2023-02-24 12:30', 5, 4);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (8, 'Lovebirds do not sing.', '2023-02-24 18:30', 16, 4);


INSERT INTO invitations(id, destination_user, source_user, invitation_state) VALUES (1, 'Guille8', 'Lucas24', 'ACCEPTED');
INSERT INTO invitations(id, destination_user, source_user, invitation_state) VALUES (2, 'Alvaro2', 'Guille12', 'PENDING');
INSERT INTO invitations(id, destination_user, source_user, invitation_state) VALUES (3, 'Lucas2', 'Nico1', 'REFUSED');

INSERT INTO achievements(id,name,description,image_url,threshold,metric,status) VALUES (1, 'New hobby', 'Play a total of 3 hours', 'https://i.imgur.com/0Q0M2YV.png', 3, 'TOTAL_PLAY_TIME', 'LOCKED');
INSERT INTO achievements(id,name,description,image_url,threshold,metric,status) VALUES (2, 'Professional', 'Win 5 games', 'https://i.imgur.com/0Q0M2YV.png', 5, 'VICTORIES', 'UNLOCKED');
-- Inserta un registro de PlayerStatistic
INSERT INTO player_statistics (id, number_of_games, win_number, lose_number, competitive_points,avg_duration, max_duration, min_duration) 
VALUES (1, 10, 5, 5, 0, 250, 120, 10);
INSERT INTO player_statistics (id, number_of_games, win_number, lose_number, competitive_points, avg_duration, max_duration, min_duration) 
VALUES (3, 100, 80, 20, 1000, 320, 400, 180);
INSERT INTO player_statistics (id, number_of_games, win_number, lose_number, competitive_points, avg_duration, max_duration, min_duration)
VALUES (2, 15, 10, 5, 50, 280, 150, 20);

-- Inserta un registro de GameStatistic
INSERT INTO game_statistics (id, result, game_duration, points) VALUES (1, 'alvarobernal2412', 120, 14);
INSERT INTO game_statistics (id, result, game_duration, points) VALUES (2, 'ronaldDinamita', 150, 22);
INSERT INTO game_statistics (id, result, game_duration, points) VALUES (3, 'nicherlob', 180, 12);

-- Inserta un registro de Message
INSERT INTO messages (id, content, source_user, message_date) VALUES (1, 'Hola Buenos Dias','guillecoria','2023-02-24 12:30' );
INSERT INTO messages (id, content, source_user, message_date) VALUES (2,'Que calor hace!!!' ,'lucas69','2023-04-11 11:30' );

-- Inserta un registro de Deck
INSERT INTO deck(id, number_of_cards) VALUES (1, 16);
INSERT INTO deck(id, number_of_cards) VALUES (2, 44);


INSERT INTO chats (id) VALUES (1);
INSERT INTO chats (id) VALUES (2);
INSERT INTO chats (id) VALUES (3);



