--This file can be run to verify the data of the database.
--The database this file is made for is team2_904_cfb.

--show amount of rows in each table
SELECT count(*) AS Rows FROM conference;
SELECT count(*) AS Rows FROM drive;
SELECT count(*) AS Rows FROM game;
SELECT count(*) AS Rows FROM game_statistics;
SELECT count(*) AS Rows FROM kickoff;
SELECT count(*) AS Rows FROM kickoff_return;
SELECT count(*) AS Rows FROM pass;
SELECT count(*) AS Rows FROM play;
SELECT count(*) AS Rows FROM player;
SELECT count(*) AS Rows FROM player_game_statistics;
SELECT count(*) AS Rows FROM punt;
SELECT count(*) AS Rows FROM punt_return;
SELECT count(*) AS Rows FROM reception;
SELECT count(*) AS Rows FROM rush;
SELECT count(*) AS Rows FROM stadium;
SELECT count(*) AS Rows FROM team;
SELECT count(*) AS Rows FROM team_game_statistics;
SELECT count(*) AS Rows FROM reception;

--average height and weight of all players in a year
SELECT 
    avg(height) AS "Average Height",
    avg(weight) AS "Average Weight"
FROM player WHERE season=2010;

--how many games TEAM played in a year
SELECT count("game code") AS "Games Played" FROM game
WHERE ("home team code"=86 OR "visit team code"=86) AND season=2012;

--Maximum duration of a game across all years
SELECT max(duration) FROM game_statistics;

--average yards of a player on kickoff
SELECT avg(kickoff.yards), player."first name", player."last name"
FROM kickoff
INNER JOIN player ON kickoff."player code"=player."player code"
WHERE player."first name" = 'Dan' AND player."last name" = 'Kelly'
GROUP BY player."first name", player."last name";

--who threw passes longer than 50 yards in a year
SELECT max(pass.yards), 
    player."first name", player."last name"
FROM pass
INNER JOIN player ON pass."passer player code"=player."player code"
WHERE pass.season = 2009
GROUP BY player."first name", player."last name"
HAVING max(pass.yards) > 50
limit 20; --needed to limit data size for testing

--who made the most rush yards across all years
SELECT sum(rush.yards), player."first name", player."last name"
FROM rush
INNER JOIN player ON rush."player code"=player."player code"
GROUP BY player."first name", player."last name"
ORDER BY sum(rush.yards) DESC
LIMIT 1;

--who had zero out of bound punts in a year
SELECT sum(punt."out of bounds"), player."first name", player."last name"
FROM punt
INNER JOIN player ON punt."player code"=player."player code"
WHERE punt.season = 2005
GROUP BY player."first name", player."last name"
HAVING sum(punt."out of bounds") = 0;

--which team made the most kickoff return touchdowns

--which state has the most stadiums in a year

--which team made the most sacks in a year