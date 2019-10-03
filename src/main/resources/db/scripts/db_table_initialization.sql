CREATE TABLE IF NOT EXISTS `days_off_system`.`users`
(
    `id`          INT AUTO_INCREMENT PRIMARY KEY,
    `first_name`  VARCHAR(64)  NOT NULL,
    `second_name` VARCHAR(64)  NOT NULL,
    `last_name`   VARCHAR(64)  NULL,
    `passport_id` VARCHAR(64)  NOT NULL UNIQUE,
    `email`       VARCHAR(256) NOT NULL UNIQUE,
    `password`    VARCHAR(64) NOT NULL,
    `enabled`     TINYINT(1) NOT NULL DEFAULT 1
)
    CHARSET = utf8
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;

CREATE TABLE IF NOT EXISTS `days_off_system`.`periods`
(
    `id`         INT AUTO_INCREMENT PRIMARY KEY,
    `user_id`    INT                         NOT NULL,
    `type_id`    ENUM ('VACATION','DAY_OFF') NOT NULL,
    `start_date` DATE                        NOT NULL,
    `end_date`   DATE                        NOT NULL,
    CONSTRAINT `fk_periods_user_id`
        FOREIGN KEY (`user_id`)
            REFERENCES `days_off_system`.`users` (`id`)
            ON DELETE CASCADE
)
    CHARSET = utf8
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;

CREATE TABLE IF NOT EXISTS `days_off_system`.`user_roles` (
    `user_id` INT NOT NULL,
    `role_name` VARCHAR(256) NOT NULL,
    PRIMARY KEY (`user_id`, `role_name`),
      CONSTRAINT `fk_user_roles_users`
        FOREIGN KEY (`user_id`)
        REFERENCES `days_off_system`.`users` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)
    CHARSET = utf8
	COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;
