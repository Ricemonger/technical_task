CREATE TABLE `shop_user`
(
    `username` VARCHAR(255) NOT NULL UNIQUE,
    `password` TEXT         NOT NULL,
    `role`     VARCHAR(255) NOT NULL DEFAULT 'USER',
    PRIMARY KEY (`username`)
);
