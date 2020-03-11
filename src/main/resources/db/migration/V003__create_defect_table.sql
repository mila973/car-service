create table defect
(
        id uuid not null,
        service_id int not null,
        vehicle_id uuid not null,
        primary key (id),
        foreign key (vehicle_id)
            references vehicle (id)
            on delete cascade
)