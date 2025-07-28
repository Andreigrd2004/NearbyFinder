CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role_name VARCHAR(255) NOT NULL,
                            PRIMARY KEY (user_id, role_name)
);

ALTER TABLE "user_roles" ADD FOREIGN KEY ("user_id") REFERENCES custom_user(id)