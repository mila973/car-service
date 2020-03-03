CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO vehicle(
        id,
        registration_number,
        state
)
VALUES(
    uuid_generate_v4(),
    'LDM570',
    'SCHEDULED'
);

INSERT INTO vehicle(
        id,
        registration_number,
        state
)
VALUES(
    uuid_generate_v4(),
    'ABC400',
    'SCHEDULED'
);