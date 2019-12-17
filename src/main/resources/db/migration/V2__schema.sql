CREATE TABLE IF NOT EXISTS assets(
    id bigserial NOT NULL CONSTRAINT asset_pkey PRIMARY KEY,
    name varchar(100) NOT NULL,
    member_id bigint NOT NULL,
    tenant_id bigint NOT NULL,
    type varchar(50) NOT NULL,
    token varchar NOT NULL,
    is_active boolean,
    created_date timestamp,
    modified_date timestamp
);

CREATE TABLE IF NOT EXISTS asset_attributes(
    asset_id bigint NOT NULL,
    asset_attribute_name varchar NOT NULL,
    asset_attribute_value varchar,
    PRIMARY KEY (asset_id, asset_attribute_name) DEFERRABLE INITIALLY DEFERRED,
    FOREIGN KEY (asset_id) REFERENCES assets(id)
);