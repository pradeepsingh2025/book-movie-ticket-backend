-- V14: Remove obsolete availability_status column from halls
-- This column was removed from the Hall model; drop it from the database.
ALTER TABLE halls
    DROP COLUMN IF EXISTS availability_status;
