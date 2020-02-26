create schema if not exists cars;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table vehicle
(
    id uuid primary key DEFAULT uuid_generate_v4(),
    registration_number text,
    state text,
    registration_date timestamp default CURRENT_TIMESTAMP
);