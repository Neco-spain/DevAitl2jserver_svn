CREATE TABLE `bot_reports_cache` (
`id` BIGINT( 24 ) NOT NULL ,
`reporter_name` VARCHAR( 100 ) NOT NULL ,
`reported_name` VARCHAR( 100 ) NOT NULL ,
`reported_time` BIGINT( 13 ) NOT NULL ,
`bot_x` MEDIUMINT( 9 ) NOT NULL ,
`bot_y` MEDIUMINT( 9 ) NOT NULL ,
`bot_z` MEDIUMINT( 9 ) NOT NULL ,
PRIMARY KEY ( `id` )
) ENGINE = MYISAM ;