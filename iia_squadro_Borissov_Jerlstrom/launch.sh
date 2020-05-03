#!/bin/bash

gradle build

jar cf iia_BORISSOV_JERLSTROM.jar build/classes/java/main/iia/.*


xterm -rightbar -fa dejavumono -fs 12 -title  "Server" -geometry 90x26 -fg grey -bg black -e  "java -cp iia-referee.jar iia.games.squadro.DuelSquadro -p 4536 hold; bash" &

sleep 0.5

xterm -rightbar -fa dejavumono -fs 12 -title  "Random" -geometry 90x26 -fg grey -bg black -e  "java -cp iia-referee.jar iia.games.contest.Client -p 4536 -s localhost -c iia.games.squadro.ChallengerSquadro;bash" &

xterm -rightbar -fa dejavumono -fs 12 -title  "Nous" -geometry 90x26 -fg grey -bg black -e  "java -cp iia_BORISSOV_JERLSTROM.jar:commons-cli-1.4.jar iia.games.contest.Client -p 4536 -s localhost -c iia.games.squadro.ChallengerSquadro;bash" &



wait
