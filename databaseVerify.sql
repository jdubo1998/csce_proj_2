--This file can be run to verify the data of the database.
--The database this file is made for is team2_904_cfb.

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
ORDER BY sum(rush.yards) DESC LIMIT 1;

--who had zero out of bound punts in a year
SELECT sum(punt."out of bounds"), player."first name", player."last name"
FROM punt
INNER JOIN player ON punt."player code"=player."player code"
WHERE punt.season = 2005
GROUP BY player."first name", player."last name"
HAVING sum(punt."out of bounds") = 0;

--which team made the most kickoff return touchdowns
SELECT sum(kickoff_return.touchdown), team."name"
FROM kickoff_return
INNER JOIN team ON kickoff_return."team code"=team."team code"
GROUP BY team."name"
ORDER BY sum(kickoff_return.touchdown) DESC LIMIT 1;

--which state has the most stadiums in a year
SELECT count(stadium), state
FROM stadium
WHERE season = 2013
GROUP BY state
ORDER BY count(stadium) DESC LIMIT 1;

--did tamu ever play ut in 2010
SELECT * FROM game
INNER JOIN team as home ON game."home team code"=home."team code" AND game."season"=home."season"
INNER JOIN team as visit ON game."visit team code"=visit."team code" AND game."season"=visit."season"
WHERE ((home.name = 'Texas A&M' AND visit.name = 'Texas')
OR (home.name = 'Texas' AND visit.name = 'Texas A&M'))
AND game.season=2010;

--bring up the game match up and scores during the 2013 season
SELECT 
    game.”date”, game."season", 
    t1."name" AS "home team", 
    t2."name" AS "visiting team", 
    tgs1."points" AS "home team score", 
    tgs2."points" AS "visiting team score" 
FROM game 
INNER JOIN team AS t1 ON t1."team code"=game."home team code"
INNER JOIN team AS t2 ON t2."team code"=game."visit team code"
INNER JOIN team_game_statistics AS tgs1 ON tgs1."game code"=game."game code" 
    AND tgs1."team code"=game."home team code" 
INNER JOIN team_game_statistics AS tgs2 ON tgs2."game code"=game."game code" 
    AND tgs2."team code"=game."visit team code"
WHERE game."season"=2013 
    AND t1."season"=2013
    AND t2."season"=2013 
ORDER BY game.”date” LIMIT 5;

--longest run in a given season
select MAX(rush.yards) AS "Longest Run"
FROM rush WHERE rush.season=2008;

--who had the most receiving yards in a given season
select sum(reception.yards), player."first name", player."last name"
FROM reception
JOIN player ON reception."player code"=player."player code"
WHERE reception.season = 2012
GROUP BY player."first name", player."last name" 
ORDER by sum(reception.yards) DESC LIMIT 1;

--who threw the most interceptions in their college career
select sum(pass.interception) AS "Total INTs", player."first name", player."last name" 
FROM pass
JOIN player on pass."passer player code"=player."player code"
GROUP BY player."first name", player."last name"
ORDER BY sum(pass.yards) DESC LIMIT 1;

--Top 5 QBs of 2013 who threw the most TDs
SELECT player_game_statistics."pass td", player."first name", player."last name", player."season"
FROM player_game_statistics
JOIN player ON player_game_statistics."player code"=player."player code"
WHERE player.season=2013
GROUP BY player_game_statistics."pass td", player."first name", player."last name", player."season"
ORDER BY player_game_statistics."pass td" DESC LIMIT 5;

--highest scorring game of 2008
SELECT game.date, max(play."offense points"+play."defense points") AS "Total Score"
FROM play
INNER JOIN game ON play."game code"=game."game code"
WHERE play.season = 2008
GROUP BY game.date
ORDER BY max(play."offense points"+play."defense points") DESC LIMIT 1;

--conference with the most teams in 2011
SELECT count(team."team code"), team."conference code", conference."name"
FROM team
JOIN conference ON team."conference code"=conference."conference code"
WHERE team.season = 2011 and conference.season = 2011
GROUP BY team."conference code", conference."name"
ORDER BY count(team."team code") DESC LIMIT 1;

--most yards passing
select sum(player_game_statistics."pass yard") AS "Total Passing Yards", player."first name", player."last name"
FROM player_game_statistics
JOIN player ON player_game_statistics."player code"=player."player code"
GROUP BY player."first name", player."last name"
ORDER BY "Total Passing Yards" DESC LIMIT 1;

--who has the most sacks
select sum(player_game_statistics.sack) AS "Total Sacks", player."first name", player."last name"
FROM player_game_statistics
JOIN player on player_game_statistics."player code"=player."player code"
WHERE player."first name" = 'Bruce' AND player."last name" = 'Miller'
GROUP BY player."first name", player."last name";

--most fumbles in a game
SELECT team_game_statistics.fumble, team.name, team_game_statistics.season
FROM team_game_statistics
JOIN team ON team_game_statistics."team code"=team."team code"
WHERE team_game_statistics.season=2013
GROUP BY team_game_statistics.fumble, team.name, team_game_statistics.season
ORDER BY team_game_statistics.fumble DESC LIMIT 1;