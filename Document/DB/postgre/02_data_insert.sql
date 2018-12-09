-- 1*6

INSERT INTO users(
  email,
  name,
  pwd,
  created_at,
  last_modified_at
) VALUES (
  'alklid@sample.com',
  'alklid',
  '{bcrypt}$2a$08$6J.nxiOn6T4tCQO0OrcJxOmtmyFaaW0tSbusAZsZi9Q1yWMpUtzqu',
  timezone('utc'::text, now()),
  timezone('utc'::text, now())
);


INSERT INTO oauth_client_details(
  client_id,
  resource_ids,
  client_secret,
  scope,
  authorized_grant_types,
  web_server_redirect_uri,
  authorities,
  access_token_validity,
  refresh_token_validity,
  autoapprove,
  name,
  client_secret_key,
  created_at,
  last_modified_at
) VALUES (
  'oauth_test_client_id',
  'oauth_test_resources_id',
  '{bcrypt}$2a$04$aXWSdqTTmFvQKZ4kVSeVHuGqXApQJqlkwLkuS/NSpil4p1tEa0bnG',
  'MANAGE',
  'authorization_code,password,refresh_token,client_credentials,implicit',
  'http://localhost:20000/version',
  null,
  14400,
  604800,
  'false',
  'oauth_test',
  'oauth_test_client_secret',
  timezone('utc'::text, now()),
  timezone('utc'::text, now())
);
