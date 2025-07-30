drop table nearby_finder.api_key;

create table nearby_finder.api_key
(
    "id"      INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "value"   varchar,
    "name"    varchar,
    "user_id" integer
)
