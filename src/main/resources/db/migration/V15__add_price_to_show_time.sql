-- Add price column (money) to show_times
ALTER TABLE show_times
ADD COLUMN price numeric(10,2) NOT NULL DEFAULT 0;
