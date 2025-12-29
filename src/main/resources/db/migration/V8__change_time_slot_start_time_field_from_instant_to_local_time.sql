ALTER TABLE time_slots
DROP
COLUMN start_time;

ALTER TABLE time_slots
    ADD start_time time WITHOUT TIME ZONE NOT NULL;