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
guessNumber INT NOT NULL,
`time` TIMESTAMP NOT NULL,
userGuess INT NOT NULL,
result varchar(30) NOT NULL,
gameId INT NOT NULL,
FOREIGN KEY (gameId) REFERENCES game(gameId)
);

INSERT INTO `status` (StatusName)
VALUES 
	("in progress"),
	("finished"); 
    
INSERT INTO game(answer, statusId)
VALUES
    (1234, 1),
    (2468, 2),
    (3579, 1);
-- SELECT * FROM game;
-- SELECT * FROM guess;
INSERT INTO guess(guessNumber, time, userGuess, result, gameId)
VALUES
    (1, '2022-01-19 03:14:07', 1047, "testing", 1),
    (2, '2022-01-19 03:11:06', 2183, "testing2", 1),
    (1, '2022-02-11 03:01:07', 2468, "testing10", 2);
