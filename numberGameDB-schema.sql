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

CREATE TABLE guess(
guessId INT PRIMARY KEY AUTO_INCREMENT,
`time` TIMESTAMP NOT NULL,
userGuess INT NOT NULL,
result varchar(30) NOT NULL,
gameId INT NOT NULL,
(gameId) REFERENCES game(gameId)
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
-- SELECT * FROM game;
-- SELECT * FROM guess;
