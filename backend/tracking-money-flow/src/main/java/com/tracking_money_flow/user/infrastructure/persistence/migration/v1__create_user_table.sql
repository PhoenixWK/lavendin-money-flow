create table user (
	id varchar(255) primary key,
    username varchar(255) not null,
    date_of_birth date default null,
    email varchar(255) not null unique,
    hashed_password varchar(255) default null,
    status enum('ACTIVE', 'INACTIVE', 'LOCKED') default 'ACTIVE',
    created_at timestamp default current_timestamp,
    updated_at timestamp on update current_timestamp
);
