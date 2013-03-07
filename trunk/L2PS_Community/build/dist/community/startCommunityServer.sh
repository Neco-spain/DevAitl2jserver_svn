#!/bin/sh
java -Xms128m -Xmx128m -cp ./../libs/*:L2PS_Community.jar com.l2jserver.communityserver.L2CommunityServer > log/stdout.log 2>&1