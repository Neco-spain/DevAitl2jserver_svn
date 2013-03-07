@echo off
title Community Server Console
echo Starting L2PS Community Server.
echo.
java -Xms128m -Xmx128m -cp ./../libs/*;L2PS_Community.jar com.l2jserver.communityserver.L2CommunityServer
pause
