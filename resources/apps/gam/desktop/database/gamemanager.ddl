use gamemanager;

drop table game;

CREATE TABLE `game` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Title` varchar(255) NOT NULL,
  `DateOfCompletion` date DEFAULT NULL,
  `Platform` int DEFAULT NULL,
  `Rate` int DEFAULT NULL,
  `Status` int DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

drop table platform;

CREATE TABLE `platform` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Description` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

drop table rate;

CREATE TABLE `rate` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Description` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

drop table status;

CREATE TABLE `status` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Description` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

drop table user;

CREATE TABLE `user` (
  `ID` varchar(10) NOT NULL,
  `Password` double NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

drop view all_games;

CREATE VIEW `all_games` AS
select 	g.id, g.title, p.description as platform, r.description as rate, s.description as status  
from 	game g,
		platform p,
        rate r,
        status s
where	g.platform = p.id and
		g.rate = r.id and
        g.status = s.id;

commit;

