create schema if not exists cars;

create table vehicle
(
    id uuid primary key,
    registration_number text,
    state text,
    registration_date timestamp
);