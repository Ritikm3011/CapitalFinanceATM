CREATE TABLE `atm`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `avl_bal` INT NULL,
  `address` VARCHAR(45) NULL,
  `phone` VARCHAR(45) NULL,
  `time` VARCHAR(45) NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));





CREATE TABLE `atm`.`transaction` (
  `t_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `amount` INT NULL,
  `type` VARCHAR(45) NOT NULL,
  `note` VARCHAR(45) NULL,
  `date` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`t_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `atm`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);





CREATE TABLE `atm`.`admin` (
  `userId` INT NOT NULL,
  `userPassword` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`userId`));





class com.atm.Main
	java.lang.UnsupportedClassVersionError: com/atm/Main has been compiled by a more recent version of the Java Runtime (class file version 62.0), this version of the Java Runtime only recognizes class file versions up to 61.0
C:\Users\ritik\AppData\Local\NetBeans\Cache\19\executor-snippets\run.xml:111: The following error occurred while executing this line:
C:\Users\ritik\AppData\Local\NetBeans\Cache\19\executor-snippets\run.xml:68: Java returned: 1


https://www.baeldung.com/java-lang-unsupportedclassversion

https://rollbar.com/blog/java-unsupportedclassversionerror/


https://www.youtube.com/watch?v=ugRwFv5Nfow&ab_channel=BoostMyTool