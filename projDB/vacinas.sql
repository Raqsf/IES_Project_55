SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET TIME_ZONE = "+00:00";

CREATE DATABASE IF NOT EXISTS `vaccinationdb` DEFAULT CHARACTER SET latin1;
USE `vaccinationdb`;

CREATE TABLE IF NOT EXISTS `Pessoa` (
    `n_utente`			INT		  AUTO_INCREMENT          NOT NULL,
	`nome`		        VARCHAR(256)		NOT NULL,
	`email`		        VARCHAR(256)		NOT NULL,
	`morada`		    VARCHAR(256)		NOT NULL,
	`data_nascimento`	DATE            	NOT NULL,
	`doencas`		    VARCHAR(500),

	PRIMARY KEY(`n_utente`)
);

CREATE TABLE IF NOT EXISTS `Centro_vacinacao` (
    `id`			                INT		  AUTO_INCREMENT          NOT NULL,
    `nome`		                    VARCHAR(256)		NOT NULL,
    `morada`	        	        VARCHAR(256)		NOT NULL,
    `capacidade_max`		        INT         		NOT NULL,
    `capacidade_atual`		        INT,

    PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS `Vacina` (
    `n_vacina`			    INT		  AUTO_INCREMENT          NOT NULL,
    `lote`		            INT         		NOT NULL,
    `nome`		            VARCHAR(256)		NOT NULL,
    `data_rececao`	        DATE            	NOT NULL,
    `administrada_a`		INT                 NOT NULL,
    `data_administracao`    DATE,
    `centro_vacinacao`		INT        NOT NULL,

    PRIMARY KEY(`n_vacina`),
    FOREIGN KEY(`administrada_a`) REFERENCES `Pessoa`(`n_utente`),
    FOREIGN KEY(`centro_vacinacao`) REFERENCES `Centro_vacinacao`(`id`)
);

CREATE TABLE IF NOT EXISTS `Lote` (
    `id`			                INT		  AUTO_INCREMENT          NOT NULL,
    `quantidade`	        	    INT         		NOT NULL,
    `data_validade`		            DATE            	NOT NULL,
    `atribuida_ao_centro`		    INT            	    NOT NULL,

    PRIMARY KEY(`id`),
    FOREIGN KEY (`atribuida_ao_centro`) REFERENCES `Centro_vacinacao`(`id`)
);

INSERT INTO `Centro_vacinacao` (`id`, `nome`, `morada`, `capacidade_max`, `capacidade_atual`) VALUES 
(1, 'Centro de Vacinação do Porto', 'Porto', 15, 0),
(2, 'Centro de Vacinação do Lisboa', 'Lisboa', 23, 0),
(3, 'Centro de Vacinação do Coimbra', 'Coimbra', 5, 0),
(4, 'Centro de Vacinação do Aveiro', 'Aveiro', 8, 0);