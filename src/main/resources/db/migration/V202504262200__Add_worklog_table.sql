-- Create work_log table
CREATE TABLE public.work_log
(
    id             BIGSERIAL PRIMARY KEY,
    execution_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    duration_type  TEXT,
    duration       BIGINT,
    currency_value NUMERIC(19, 2),
    notes          TEXT,
    created_at     TIMESTAMPTZ                 NOT NULL DEFAULT now(),
    updated_at     TIMESTAMPTZ                 NOT NULL DEFAULT now(),
    active         BOOLEAN                     NOT NULL DEFAULT TRUE,
    uuid           UUID                        NOT NULL DEFAULT uuid_generate_v4() UNIQUE,
    version        INTEGER                     NOT NULL DEFAULT 0,

    -- Foreign keys
    employee_id    BIGINT                      NOT NULL,
    apartment_id   BIGINT                      NOT NULL,
    user_id        BIGINT                      NOT NULL,

    CONSTRAINT fk_work_log_employee FOREIGN KEY (employee_id) REFERENCES public.employee (id) ON DELETE SET NULL,
    CONSTRAINT fk_work_log_apartment FOREIGN KEY (apartment_id) REFERENCES public.apartment (id) ON DELETE SET NULL,
    CONSTRAINT fk_work_log_user FOREIGN KEY (user_id) REFERENCES public."user" (id) ON DELETE SET NULL,

    -- Unique constraint on execution_date + employee_id + apartment_id
    CONSTRAINT uk_work_log_unique_execution_employee_apartment UNIQUE (execution_date, employee_id, apartment_id)
);

-- Optional: Indexes for better search performance
CREATE INDEX idx_work_log_uuid ON public.work_log (uuid);
CREATE INDEX idx_work_log_employee_id ON public.work_log (employee_id);
CREATE INDEX idx_work_log_apartment_id ON public.work_log (apartment_id);
CREATE INDEX idx_work_log_user_id ON public.work_log (user_id);
CREATE INDEX idx_work_log_execution_date ON public.work_log (execution_date);

-- Commit
COMMIT;
