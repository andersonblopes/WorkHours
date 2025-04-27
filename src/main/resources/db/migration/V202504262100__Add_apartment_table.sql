-- Create apartment table
CREATE TABLE public.apartment
(
    id             BIGSERIAL PRIMARY KEY,
    description    TEXT        NOT NULL,
    access_code    TEXT,
    duration_type  TEXT,
    currency_value NUMERIC(19, 2),
    created_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    active         BOOLEAN     NOT NULL DEFAULT TRUE,
    uuid           UUID        NOT NULL DEFAULT uuid_generate_v4() UNIQUE,
    version        INTEGER     NOT NULL DEFAULT 0,
    build_id       BIGINT      NOT NULL,

    -- Foreign key
    CONSTRAINT fk_apartment_build FOREIGN KEY (build_id) REFERENCES public.build (id) ON DELETE SET NULL,

    -- Unique constraint on build_id + description
    CONSTRAINT uk_apartment_build_description UNIQUE (build_id, description)
);

-- Optional: Indexes for faster lookup
CREATE INDEX idx_apartment_uuid ON public.apartment (uuid);
CREATE INDEX idx_apartment_build_id ON public.apartment (build_id);

-- Commit
COMMIT;
