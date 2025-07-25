CREATE TABLE "custom_user" (
  "id" integer PRIMARY KEY,
  "email" varchar,
  "password" varchar,
  "username" varchar,
  "displayName" varchar,
  "enabled" Boolean,
  "accountNonExpired" Boolean,
  "accountNonLocked" Boolean,
  "credentialsNonExpired" Boolean
);

CREATE TABLE "api_key" (
  "value" varchar PRIMARY KEY,
  "name" varchar,
  "user_id" integer
);

CREATE TABLE "country" (
  "id" integer PRIMARY KEY,
  "name" varchar
);

CREATE TABLE "region" (
  "id" integer PRIMARY KEY,
  "name" varchar,
  "country_id" integer
);

CREATE TABLE "city" (
  "id" integer PRIMARY KEY,
  "name" varchar,
  "region_id" integer
);

CREATE TABLE "user_country" (
  "id" integer PRIMARY KEY,
  "user_id" integer,
  "country_id" integer
);

CREATE TABLE "currency" (
  "id" integer PRIMARY KEY,
  "name" varchar,
  "amount" integer,
  "country_id" integer
);

ALTER TABLE "api_key" ADD CONSTRAINT "user_api" FOREIGN KEY ("user_id") REFERENCES "custom_user" ("id");

ALTER TABLE "user_country" ADD FOREIGN KEY ("user_id") REFERENCES "custom_user" ("id");

ALTER TABLE "user_country" ADD FOREIGN KEY ("country_id") REFERENCES "country" ("id");

ALTER TABLE "region" ADD CONSTRAINT "coutry_city" FOREIGN KEY ("country_id") REFERENCES "country" ("id");

ALTER TABLE "city" ADD FOREIGN KEY ("region_id") REFERENCES "region" ("id");

ALTER TABLE "currency" ADD FOREIGN KEY ("country_id") REFERENCES "country" ("id");
