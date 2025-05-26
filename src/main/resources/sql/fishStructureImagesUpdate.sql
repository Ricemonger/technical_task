CREATE TABLE `fish_image`
(
    `id`              INT          NOT NULL AUTO_INCREMENT,
    `fish_id`         INT          NOT NULL,
    `image_file_name` VARCHAR(255) NOT NULL,
    CONSTRAINT `image_fish_id` FOREIGN KEY (`fish_id`) REFERENCES `fish` (`id`)
        ON DELETE CASCADE,
    PRIMARY KEY (`id`)
);


INSERT INTO `fish_image` (`fish_id`, `image_file_name`)
SELECT `id`, `image_file_name`
FROM `fish`
WHERE `image_file_name` IS NOT NULL;


ALTER TABLE `fish`
DROP COLUMN `image_file_name`;