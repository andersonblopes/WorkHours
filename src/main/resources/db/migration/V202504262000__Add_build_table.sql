-- Create build table
CREATE TABLE public.build
(
    id          BIGSERIAL PRIMARY KEY,
    description TEXT        NOT NULL,
    address     TEXT,
    access_code TEXT,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    active      BOOLEAN     NOT NULL DEFAULT TRUE,
    uuid        UUID        NOT NULL DEFAULT uuid_generate_v4() UNIQUE,
    version     INTEGER     NOT NULL DEFAULT 0,
    owner_id    BIGINT      NOT NULL,

    -- Foreign key
    CONSTRAINT fk_build_owner FOREIGN KEY (owner_id) REFERENCES public.owner (id) ON DELETE SET NULL,

    -- Unique constraint on owner_id + description
    CONSTRAINT uk_build_owner_description UNIQUE (owner_id, description)
);

-- Optional: Indexes for faster querying
CREATE INDEX idx_build_uuid ON public.build (uuid);
CREATE INDEX idx_build_owner_id ON public.build (owner_id);

-- Commit
COMMIT;
