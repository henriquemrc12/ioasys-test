CREATE TABLE `tb_users` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `user_type` INTEGER NOT NULL,
    `user_status` INTEGER NOT NULL,
    `update_at` DATETIME NOT NULL,
    `created_at` DATETIME NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `tb_movies` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `genre` VARCHAR(100) NOT NULL,
    `directed_by` VARCHAR(255) NOT NULL,
    `vote_average` FLOAT NOT NULL,
    `total_vote` INT NOT NULL,
    `update_at` DATETIME NOT NULL,
    `created_at` DATETIME NOT NULL,
    PRIMARY KEY (`id`)
);