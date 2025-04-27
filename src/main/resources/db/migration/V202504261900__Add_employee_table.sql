-- Create the employee table
CREATE TABLE public.employee
(
    id           BIGSERIAL PRIMARY KEY,
    name         TEXT        NOT NULL,
    nick_name    TEXT        NOT NULL,
    phone_number TEXT,
    email        TEXT        NOT NULL,
    created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    active       BOOLEAN     NOT NULL DEFAULT TRUE,
    uuid         UUID        NOT NULL DEFAULT uuid_generate_v4() UNIQUE,
    version      INTEGER     NOT NULL DEFAULT 0,
    user_id      BIGINT,

    -- Foreign key
    CONSTRAINT fk_employee_user FOREIGN KEY (user_id) REFERENCES public."user" (id) ON DELETE SET NULL,

    -- Individual unique constraints
    CONSTRAINT uk_employee_name UNIQUE (name),
    CONSTRAINT uk_employee_email UNIQUE (email),

    -- Combined unique constraint
    CONSTRAINT uk_employee_name_email UNIQUE (name, email)
);

-- Optional: Add indexes for faster lookup
CREATE INDEX idx_employee_uuid ON public.employee (uuid);
CREATE INDEX idx_employee_name ON public.employee (name);
CREATE INDEX idx_employee_email ON public.employee (email);

-- Commit
COMMIT;
