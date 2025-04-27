CREATE SEQUENCE user_id_seq START 1;
create table "user"
(
    id         integer default nextval(('public.user_id_seq'::text)::regclass) not null
        primary key,
    name       TEXT                                                            NOT NULL CHECK (length(name) > 0),
    email      TEXT                                                            NOT NULL CHECK (length(email) > 0) UNIQUE,
    nick_name  TEXT                                                            NOT NULL,
    login      TEXT                                                            NOT NULL CHECK (length(login) > 0) UNIQUE,
    password   TEXT                                                            NOT NULL CHECK (length(password) > 0),
    created_at TIMESTAMP WITH TIME ZONE                                        NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE                                        NOT NULL DEFAULT now(),
    active     BOOLEAN DEFAULT TRUE,
    uuid       UUID    DEFAULT public.uuid_generate_v4()                       NOT NULL UNIQUE,
    version    INTEGER DEFAULT 0                                               NOT NULL,

    -- Create indexes for efficient queries
    CONSTRAINT user_username_email UNIQUE (email),
    CONSTRAINT user_username_unique UNIQUE (login)
);

-- Add index for faster lookups by UUID and created_at
CREATE INDEX idx_user_uuid ON "user" (uuid);
CREATE INDEX idx_user_created_at ON "user" (created_at);

-- Commit changes
COMMIT;
