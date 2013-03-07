CREATE TABLE IF NOT EXISTS `bot_report_punish` (
`bot_name` VARCHAR( 100 ) NOT NULL ,
`punish_name` VARCHAR( 30 ) NOT NULL ,
`punish_time_left` BIGINT( 13 ) NOT NULL
) ENGINE = MYISAM ;
