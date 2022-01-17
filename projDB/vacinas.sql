SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET TIME_ZONE = "+00:00";
 
CREATE DATABASE IF NOT EXISTS `vaccinationdb` DEFAULT CHARACTER SET latin1;
USE `vaccinationdb`;

CREATE TABLE IF NOT EXISTS `pessoa` (
    `n_utente`			INT		  AUTO_INCREMENT          NOT NULL,
	`nome`		        VARCHAR(256)		NOT NULL,
	`email`		        VARCHAR(256)		NOT NULL,
	`morada`		    VARCHAR(256)		NOT NULL,
	`data_nascimento`	DATE            	NOT NULL,

	PRIMARY KEY(`n_utente`)
);

CREATE TABLE IF NOT EXISTS `centro_vacinacao` (
    `id`			                INT		  AUTO_INCREMENT          NOT NULL,
    `nome`		                    VARCHAR(256)		NOT NULL,
    `morada`	        	        VARCHAR(256)		NOT NULL,
    `capacidade_max`		        INT         		NOT NULL,
    `capacidade_atual`		        INT,

    PRIMARY KEY(`id`)
);

CREATE TABLE `lote` (
    `id`			                VARCHAR(6)          NOT NULL,
    `quantidade`	        	    INT         		NOT NULL,
    `atribuida_ao_centro`		    INT            	    NOT NULL,
    `data_chegada`                  DATE                NOT NULL,

    PRIMARY KEY(`id`),
    FOREIGN KEY (`atribuida_ao_centro`) REFERENCES `centro_vacinacao`(`id`)
);

CREATE TABLE `vacina` (
    `n_vacina`			    INT		  AUTO_INCREMENT    NOT NULL,
    `lote`		            VARCHAR(6)         		NOT NULL,
    `nome`		            VARCHAR(256)		NOT NULL,
    `data_validade`		    DATE            		NOT NULL,
    `administrada_a`		INT,
    `data_administracao`    DATETIME,

    PRIMARY KEY(`n_vacina`),
    FOREIGN KEY(`administrada_a`) REFERENCES `pessoa`(`n_utente`),
    FOREIGN KEY(`lote`) REFERENCES `lote`(`id`)
);

CREATE TABLE IF NOT EXISTS `agendamento` (
    `id`			                INT		  AUTO_INCREMENT          NOT NULL,
    `dia_vacinacao`             DATETIME        ,
    `n_utente`                INT           NOT NULL,
    `centro_vacinacao`          INT,

    PRIMARY KEY(`id`),
    FOREIGN KEY(`n_utente`) REFERENCES `pessoa`(`n_utente`), 
    FOREIGN KEY(`centro_vacinacao`) REFERENCES `centro_vacinacao`(`id`)
);

CREATE TABLE IF NOT EXISTS `lista_de_espera` (
    `id`			                INT		  AUTO_INCREMENT          NOT NULL,
    `n_utente`                INT           NOT NULL,
    `data_inscricao`        datetime            NOT NULL,

    PRIMARY KEY(`id`),
    FOREIGN KEY(`n_utente`) REFERENCES `pessoa`(`n_utente`)
);

CREATE TABLE IF NOT EXISTS `doencas` (
    `id`    INT     AUTO_INCREMENT      NOT NULL,
    `doenca`    VARCHAR(200)    NOT NULL,

    PRIMARY KEY(`id`)
);


CREATE TABLE IF NOT EXISTS `doencas_por_utente` (
    `n_utente`   INT     NOT NULL,
    `doenca`    INT     NOT NULL,

    PRIMARY KEY(`n_utente`, `doenca`),
    FOREIGN KEY(`n_utente`) REFERENCES `pessoa`(`n_utente`),
    FOREIGN KEY(`doenca`) REFERENCES `doencas`(`id`)
);

INSERT INTO `doencas` (`doenca`) VALUES  
('Doença Cardíaca'),
('Doença Pulmonar'),
('Diabetes'),
('Cancro'),
('Obesidade'),
('Doença AutoImune');

CREATE TABLE IF NOT EXISTS `capacidade_por_dia` (
    `id`			            INT		  AUTO_INCREMENT          NOT NULL,
    `dia`                       DATE            NOT NULL,
    `quantidade`                INT             NOT NULL,

    PRIMARY KEY(`id`),
);

INSERT INTO `centro_vacinacao` (`id`, `nome`, `morada`, `capacidade_max`, `capacidade_atual`) VALUES 
(1, 'Centro de Vacinação do Porto', 'Porto', 15, 0),
(2, 'Centro de Vacinação do Lisboa', 'Lisboa', 23, 0),
(3, 'Centro de Vacinação do Coimbra', 'Coimbra', 5, 0),
(4, 'Centro de Vacinação do Aveiro', 'Aveiro', 8, 0);


CREATE PROCEDURE getListaEsperaByAge(IN age int)
BEGIN
    select * from lista_de_espera as le
    join pessoa as p on p.n_utente = le.n_utente
    join doencas_por_utente as dpu on dpu.n_utente = le.n_utente
    where TIMESTAMPDIFF(year,p.data_nascimento,CURRENT_DATE) > age;
END


CREATE PROCEDURE getListaEsperaByAgeAndDoenca(IN age int, IN doenca int)
BEGIN
    select * from lista_de_espera as le
    join pessoa as p on p.n_utente = le.n_utente
    join doencas_por_utente as dpu on dpu.n_utente = le.n_utente
    where TIMESTAMPDIFF(year,p.data_nascimento,CURRENT_DATE) > age
    AND dpu.doenca = doenca;
END

CREATE PROCEDURE getListaEsperaByDoenca(IN doenca int)
BEGIN
    select * from lista_de_espera as le
    join pessoa as p on p.n_utente = le.n_utente
    join doencas_por_utente as dpu on dpu.n_utente = le.n_utente
    where dpu.doenca = doenca;
END

CREATE PROCEDURE getAgendamentosPorDia(IN dia DATE)
BEGIN
    select * from agendamento as a
    WHERE DATE(a.dia_vacinacao) BETWEEN dia AND dia;
END
