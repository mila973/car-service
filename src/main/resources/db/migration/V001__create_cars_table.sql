create schema if not exists cars;

create table vehicle
(
    id uuid primary key not null,
    registration_number text,
    state text,
    registration_date timestamp default CURRENT_TIMESTAMP
);