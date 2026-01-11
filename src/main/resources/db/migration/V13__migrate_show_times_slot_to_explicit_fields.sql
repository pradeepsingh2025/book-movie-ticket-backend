-- V13: Migrate show_times from slot-based (slot_id -> day_time_slots/time_slots)
-- to explicit fields: start_time, end_time, day, hall_id

-- 1) Add new nullable columns
ALTER TABLE show_times
    ADD COLUMN hall_id BIGINT;

ALTER TABLE show_times
    ADD COLUMN start_time time WITHOUT TIME ZONE;

ALTER TABLE show_times
    ADD COLUMN end_time time WITHOUT TIME ZONE;

ALTER TABLE show_times
    ADD COLUMN day VARCHAR(20);

-- 2) Populate new columns from existing slot relation (if present)
-- end_time is computed as start_time + movie.duration minutes
UPDATE show_times st
SET day = d.day,
        start_time = ts.start_time,
        end_time = (ts.start_time + (m.duration::text || ' minutes')::interval)::time
FROM day_time_slots d, time_slots ts, movies m
WHERE st.slot_id = d.id
    AND d.time_slot_id = ts.id
    AND m.id = st.movie_id;

-- 3) Backfill any remaining nulls with sensible defaults to allow NOT NULL migration later
UPDATE show_times SET start_time = '00:00' WHERE start_time IS NULL;
UPDATE show_times SET end_time = '00:00' WHERE end_time IS NULL;
UPDATE show_times SET day = 'Monday' WHERE day IS NULL;

-- 4) Drop old foreign key and slot_id column
ALTER TABLE show_times DROP CONSTRAINT IF EXISTS FK_SHOW_TIMES_ON_SLOT;
ALTER TABLE show_times DROP COLUMN IF EXISTS slot_id;

-- 5) Add hall foreign key (keep nullable for now — set NOT NULL in a later migration after assigning halls)
ALTER TABLE show_times
    ADD CONSTRAINT FK_SHOW_TIMES_ON_HALL FOREIGN KEY (hall_id) REFERENCES halls (id);

-- 6) Make new columns NOT NULL (start_time, end_time, day). Hall remains nullable.
ALTER TABLE show_times ALTER COLUMN start_time SET NOT NULL;
ALTER TABLE show_times ALTER COLUMN end_time SET NOT NULL;
ALTER TABLE show_times ALTER COLUMN day SET NOT NULL;

-- NOTE: This migration preserves existing data by deriving start/end/day from the linked slot/time_slot/movie.
-- If your business rules require a different end_time calculation (e.g., using movie runtime + buffer), adjust the SQL above accordingly.
