SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET TIME_ZONE = "+00:00";

CREATE DATABASE IF NOT EXISTS `vacinas` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `vacinas`;

CREATE TABLE IF NOT EXISTS `Pessoa` (
    `n_utente`			INT		  AUTO_INCREMENT          NOT NULL,
	`nome`		        VARCHAR(256)		NOT NULL,
	`email`		        VARCHAR(256)		NOT NULL,
	`morada`		    VARCHAR(256)		NOT NULL,
	`data_nascimento`	DATE            	NOT NULL,
	`doencas`		    VARCHAR(500),

	PRIMARY KEY(`n_utente`)
);
GO


CREATE TABLE IF NOT EXISTS `Vacina` (
    `n_vacina`			    INT		  AUTO_INCREMENT          NOT NULL,
    'lote'		            INT         		NOT NULL,
    `nome`		            VARCHAR(256)		NOT NULL,
    `data_rececao`	        DATE            	NOT NULL,
    `administrada_a`		INT,
    `data_administracao`    DATE,
    `centro_vacinacao`		INT        NOT NULL,

    PRIMARY KEY(`n_vacina`),
    FOREIGN KEY(`atribuida_a`) REFERENCES `Pessoa`(`n_utente`)
    FOREIGN KEY(`administrada_a`) REFERENCES `Pessoa`(`n_utente`)
    FOREIGN KEY(`centro_vacinacao`) REFERENCES `Centro_vacionacao`(`id`)
);
GO

CREATE TABLE IF NOT EXISTS `Centro_vacinacao` (
    `id`			                INT		  AUTO_INCREMENT          NOT NULL,
    `nome`		                    VARCHAR(256)		NOT NULL,
    `morada`	        	        VARCHAR(256)		NOT NULL,
    `capacidade_max`		        INT         		NOT NULL,
    `capacidade_atual`		        INT,

    PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS `Lote` (
    `id`			                INT		  AUTO_INCREMENT          NOT NULL,
    `quantidade`	        	    INT         		NOT NULL,
    `data_validade`		            DATE            	NOT NULL,
    `atribuida_ao_centro`		    INT            	    NOT NULL,

    PRIMARY KEY(`id`)
    FOREIGN KEY (`atribuida_ao_centro`) REFERENCES `Centro_vacinacao`(`id`)
);