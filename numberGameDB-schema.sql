DROP DATABASE IF EXISTS numberGameDB;
CREATE DATABASE numberGameDB;

USE numberGameDB;

CREATE TABLE `status`(
statusId INT PRIMARY KEY AUTO_INCREMENT,
statusName VARCHAR(20) NOT NULL
);

CREATE TABLE game(
gameId INT PRIMARY KEY AUTO_INCREMENT,
answer INT NOT NULL,
statusId INT NOT NULL,
FOREIGN KEY (statusId) REFERENCES `status`(statusId)
);

CREATE TABLE `round`(
roundId INT PRIMARY KEY AUTO_INCREMENT,
gameId INT NOT NULL,
FOREIGN KEY (gameId) references game(gameId)
);

CREATE TABLE gameRound(
gameId INT NOT NULL,
roundId INT NOT NULL,
 primary key (gameId, roundId),
    foreign key fk_gameRound_gameId (gameId)
		references game(gameId),
	foreign key fk_gameRound_roundId (roundId)
		references `round`(roundId)
);

CREATE TABLE guess(
guessId INT PRIMARY KEY AUTO_INCREMENT,
`time` DATETIME NOT NULL,
userGuess INT NOT NULL,
result varchar(15) NOT NULL,
roundId INT NOT NULL,
FOREIGN KEY (roundId) REFERENCES `round`(roundId)
);

CREATE TABLE roundGuess(
roundId INT NOT NULL,
guessId INT NOT NULL,
 primary key (roundId, guessId),
    foreign key fk_roundGuess_roundId (roundId)
		references `round`(roundId),
	foreign key fk_roundGuess_guessId (guessId)
		references guess (guessId)
);

INSERT INTO `status` (StatusName)
VALUES 
	("in progress"),
	("finished"); 
    
INSERT INTO game(answer, statusId)
VALUES
	(1234, 1),
    (2468, 1),
    (3579, 1);
