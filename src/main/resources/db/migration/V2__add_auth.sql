CREATE TABLE app_user (
                          user_id UUID NOT NULL DEFAULT gen_random_uuid(),
                          username TEXT NOT NULL,
                          password TEXT NOT NULL,
                          role TEXT NOT NULL DEFAULT 'USER',
                          enabled BOOLEAN NOT NULL DEFAULT TRUE,
                          CONSTRAINT pk_app_user PRIMARY KEY (user_id)
);

ALTER TABLE app_user ADD CONSTRAINT uq_app_user_username UNIQUE (username);

CREATE TABLE refresh_token (
                               refresh_token_id UUID NOT NULL DEFAULT gen_random_uuid(),
                               user_id UUID NOT NULL,
                               token_hash TEXT NOT NULL,
                               expires_at TIMESTAMP NOT NULL,
                               revoked BOOLEAN NOT NULL DEFAULT FALSE,
                               created_at TIMESTAMP NOT NULL DEFAULT now(),
                               CONSTRAINT pk_refresh_token PRIMARY KEY (refresh_token_id),
                               CONSTRAINT fk_refresh_token_user FOREIGN KEY (user_id)
                                   REFERENCES app_user (user_id)
);

CREATE INDEX idx_refresh_token_user_id ON refresh_token (user_id);
ALTER TABLE refresh_token ADD CONSTRAINT uq_refresh_token_token_hash UNIQUE (token_hash);
