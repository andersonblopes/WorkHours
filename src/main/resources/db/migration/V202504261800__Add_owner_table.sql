-- Create the owner table
CREATE TABLE public.owner
(
    id           BIGSERIAL PRIMARY KEY,
    name         TEXT        NOT NULL,
    nick_name    TEXT        NOT NULL,
    email        TEXT        NOT NULL,
    phone_number TEXT,
    created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    active       BOOLEAN     NOT NULL DEFAULT TRUE,
    uuid         UUID        NOT NULL DEFAULT uuid_generate_v4() UNIQUE,
    version      INTEGER     NOT NULL DEFAULT 0,
    user_id      BIGINT,

    -- Foreign key
    CONSTRAINT fk_owner_user FOREIGN KEY (user_id) REFERENCES public."user" (id) ON DELETE SET NULL,

    -- Individual unique constraints
    CONSTRAINT uk_owner_name UNIQUE (name),
    CONSTRAINT uk_owner_email UNIQUE (email),

    -- Combined unique constraint
    CONSTRAINT uk_owner_name_email UNIQUE (name, email)
);

-- Optional: Add indexes for faster lookup
CREATE INDEX idx_owner_uuid ON public.owner (uuid);
CREATE INDEX idx_owner_name ON public.owner (name);
CREATE INDEX idx_owner_email ON public.owner (email);

-- Commit
COMMIT;
