create table user_credentials
(
    id         uuid primary key,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    user_id    uuid                     not null,
    password   varchar(300)             not null
);

CREATE TABLE user_roles
(
    user_id UUID        NOT NULL,
    role    VARCHAR(50) NOT NULL
);

ALTER TABLE user_roles
    ADD PRIMARY KEY (user_id, role),
    add CONSTRAINT fk_user_roles_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;

alter table user_credentials
    add constraint fk_user_credentials_user_id foreign key (user_id) references users (id) on delete cascade;

alter table users
    drop column password;
