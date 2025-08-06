CREATE TABLE "exchange"
(
    "id" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "source" varchar,
    "target" varchar,
    "amount" DOUBLE PRECISION,
    "expiration_date" DATE
)