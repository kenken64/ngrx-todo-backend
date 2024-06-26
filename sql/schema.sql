CREATE TABLE todo (
  `id` INT NOT NULL AUTO_INCREMENT,
  `recorded_audio` MEDIUMBLOB NOT NULL,
  `title` VARCHAR(100) NULL,
  `description` VARCHAR(100) NULL,
  `priorty` VARCHAR(30) NULL,
  `status` VARCHAR(5) NULL,
  `audiourl` VARCHAR(100) NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`));