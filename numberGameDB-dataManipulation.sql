use numbergamedb;

select * from status;

select * from game;

-- Get by gameId
select
game.gameId, 
status.statusName,
answer
from game
join status
	on game.statusId = status.statusId
where game.gameId = 2;

-- Get rounds by gameId and order by time
select 
guess.guessId,
guess.time,
guess.guessNumber,
guess.userGuess,
guess.result
from guess
where guess.gameId = 1
order by guess.time;

select * from guess;
