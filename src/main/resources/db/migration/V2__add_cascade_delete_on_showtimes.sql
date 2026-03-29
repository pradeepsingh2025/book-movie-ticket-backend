-- Drop the original non-cascading constraints
ALTER TABLE reservations DROP CONSTRAINT FKcubw8fydhk608t81rugxohfj5;
ALTER TABLE seats DROP CONSTRAINT FKpll7nkbc1cr8oikmkao5uqq4h;

-- Add new constraints with ON DELETE CASCADE
ALTER TABLE reservations 
    ADD CONSTRAINT FKcubw8fydhk608t81rugxohfj5 
    FOREIGN KEY (show_id) 
    REFERENCES show_times(id) 
    ON DELETE CASCADE;

ALTER TABLE seats 
    ADD CONSTRAINT FKpll7nkbc1cr8oikmkao5uqq4h 
    FOREIGN KEY (show_id) 
    REFERENCES show_times(id) 
    ON DELETE CASCADE;
