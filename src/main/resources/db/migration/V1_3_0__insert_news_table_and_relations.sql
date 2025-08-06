CREATE TABLE "news"(
    "id" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "title" varchar,
    "content" varchar,
    "link" varchar,
    "country_id" int
);
ALTER TABLE "news" ADD CONSTRAINT "country_news" FOREIGN KEY ("country_id") REFERENCES "country" ("id");