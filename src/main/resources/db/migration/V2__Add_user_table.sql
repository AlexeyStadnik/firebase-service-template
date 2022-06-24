CREATE TABLE template_user
(
    id         TEXT PRIMARY KEY,
    role       TEXT NOT NULL,
    email      TEXT,
    email_verified BOOLEAN,
    display_name   TEXT,
    custom_claims  TEXT[],
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ,
    deleted_at TIMESTAMPTZ
);