CREATE TABLE summary
(
    id               UUID PRIMARY KEY,
    title            TEXT NOT NULL,
    content          TEXT NOT NULL,
    tags             TEXT[],
    language         TEXT NOT NULL,
    original_content TEXT NOT NULL,
    created_at       TIMESTAMPTZ,
    updated_at       TIMESTAMPTZ,
    deleted_at       TIMESTAMPTZ
)