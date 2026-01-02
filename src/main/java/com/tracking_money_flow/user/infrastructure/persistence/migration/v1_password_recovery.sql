create table password_recovery(
	attached_id varchar(10),
    requested_by varchar(255) not null,
    is_expired boolean default false,
    requested_at timestamp default current_timestamp,
    expired_at timestamp
);