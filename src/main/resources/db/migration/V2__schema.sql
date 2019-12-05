CREATE TABLE IF NOT EXISTS assets(
    id bigserial NOT NULL CONSTRAINT asset_pkey PRIMARY KEY,
    member_id bigint NOT NULL,
    tenant_id bigint NOT NULL,
    type varchar(50) NOT NULL,
    token varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS asset_attributes(
    asset_id bigint NOT NULL,
    key varchar NOT NULL,
    value varchar,
    PRIMARY KEY (asset_id, key) DEFERRABLE INITIALLY DEFERRED,
    FOREIGN KEY (asset_id) REFERENCES assets(id)
);