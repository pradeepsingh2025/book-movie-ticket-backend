-- Flyway migration: remove duration column from time_slots
ALTER TABLE time_slots DROP COLUMN IF EXISTS duration;
