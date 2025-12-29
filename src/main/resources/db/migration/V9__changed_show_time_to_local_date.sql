ALTER TABLE show_times
    DROP COLUMN show_date;

ALTER TABLE show_times
    ADD show_date date NOT NULL;