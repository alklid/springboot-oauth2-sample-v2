DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS oauth_client_details;
DROP TABLE IF EXISTS oauth_client_token;
DROP TABLE IF EXISTS oauth_access_token;
DROP TABLE IF EXISTS oauth_refresh_token;
DROP TABLE IF EXISTS oauth_code;
DROP TABLE IF EXISTS oauth_approvals;


CREATE TABLE account (
	sid bigserial NOT NULL UNIQUE,
	email text NOT NULL UNIQUE,
	name text NOT NULL DEFAULT '',
	pwd text NOT NULL,
	permissions text,
	created_at timestamp,
	last_modified_at timestamp,
	PRIMARY KEY (sid)
) WITHOUT OIDS;

CREATE TABLE oauth_client_details (
  sid bigserial NOT NULL UNIQUE,
  client_id text NOT NULL UNIQUE,
  resource_ids text,
  client_secret text,
  scope text,
  authorized_grant_types text,
  web_server_redirect_uri text,
  authorities text,
  access_token_validity integer,
  refresh_token_validity integer,
  additional_information text,
  autoapprove text,
  name text,
  description text,
  client_secret_key text,
  created_at timestamp,
  last_modified_at timestamp,
  PRIMARY KEY (sid)
) WITHOUT OIDS;

create table oauth_client_token (
  token_id text,
  token bytea,
  authentication_id text,
  user_name text,
  client_id text,
  PRIMARY KEY (authentication_id)
) WITHOUT OIDS;

CREATE TABLE oauth_access_token (
  token_id text,
  token bytea,
  authentication_id text,
  user_name text,
  client_id text,
  authentication bytea,
  refresh_token text,
  PRIMARY KEY (authentication_id)
) WITHOUT OIDS;

CREATE TABLE oauth_refresh_token (
  token_id text,
  token bytea,
  authentication bytea
) WITHOUT OIDS;

CREATE TABLE oauth_code (
  code text,
  authentication bytea
) WITHOUT OIDS;

CREATE TABLE oauth_approvals (
	userId text,
	clientId text,
	scope text,
	status text,
	expiresAt timestamp,
	lastModifiedAt timestamp
) WITHOUT OIDS;