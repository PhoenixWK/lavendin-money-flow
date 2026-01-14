create table user_role(
    user_id varchar(255),
    role_id int,
    primary key(user_id, role_id),
    constraint fk_user foreign key(user_id) references user(id),
    constraint fk_role foreign key(role_id) references role(id)
);
