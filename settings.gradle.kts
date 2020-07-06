rootProject.name = "kiss-spring"

include(
  ":starters:commons:service-api",
  ":starters:commons:auth-api",

  ":starters:service-starter",
  ":starters:session-security-starter",
  ":starters:service-r2dbc-starter",
//  ":starters:service-jpa-starter",
//  ":starters:auth-service-starter",
  ":starters:resource-service-starter",
//  ":starters:graphql-starter-old",
//  ":starters:graphql-starter",
  ":starters:test-starter",

  ":demo:goods",
  ":demo:demo-shopping-list",
//  ":demo:auth",
//  ":demo:users",
//  ":demo:cart",
//  ":demo:products"

  ":hrh:houses",
  ":hrh:items"
);
