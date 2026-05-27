create table jwt_refresh_tokens
(
    id         uuid primary key,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    user_id    uuid                     not null,
    token      varchar(512)             not null,
    rotated    boolean                  not null
);

alter table jwt_refresh_tokens
    add constraint fk_jwt_refresh_tokens_user_id foreign key (user_id) references users (id);
