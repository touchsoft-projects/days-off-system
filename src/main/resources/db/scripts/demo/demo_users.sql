#admin@mail.com:1/admin@mail.com:01
INSERT INTO `days_off_system`.`users` (`id`, `first_name`, `second_name`, `last_name`, `passport_id`, `email`, `password`, `enabled`) VALUES ('1', 'admin', 'admin', 'admin', '01', 'admin@mail.com', '$2a$10$eWiMjd/WzyOoa4Io8cxNSujI6kTUWHvJuU3vWREMcA8myvAaPuYYq', 1);
#user@mail.com:1/user@mail.com:02/
INSERT INTO `days_off_system`.`users` (`id`, `first_name`, `second_name`, `last_name`, `passport_id`, `email`, `password`, `enabled`) VALUES ('2', 'user', 'user', 'user', '02', 'user@mail.com', '$2a$10$eWiMjd/WzyOoa4Io8cxNSujI6kTUWHvJuU3vWREMcA8myvAaPuYYq', 1);

INSERT INTO `days_off_system`.`user_roles` (`user_id`, `role_name`) VALUES ('1', 'ROLE_ADMIN');
INSERT INTO `days_off_system`.`user_roles` (`user_id`, `role_name`) VALUES ('2', 'ROLE_EMPLOYEE');
