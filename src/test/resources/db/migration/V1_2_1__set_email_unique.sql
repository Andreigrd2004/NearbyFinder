ALTER TABLE nearby_finder.custom_user ADD UNIQUE (email);

ALTER TABLE nearby_finder.custom_user
    ADD COLUMN version INT;