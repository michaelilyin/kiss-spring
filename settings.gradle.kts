rootProject.name = "kiss-spring"

includeBuild("buildSrc")

include(
  ":starters:commons:service-api",
  ":starters:commons:auth-api",

  ":starters:service-starter",
//  ":starters:service-jpa-starter",
//  ":starters:auth-service-starter",
//  ":starters:resource-service-starter",
//  ":starters:graphql-starter-old",
//  ":starters:graphql-starter",
  ":starters:test-starter",

  ":demo:goods"
//  ":demo:auth",
//  ":demo:users",
//  ":demo:cart",
//  ":demo:products"
);
