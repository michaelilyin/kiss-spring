rootProject.name = "kiss-spring"

includeBuild("buildSrc")

include(
  ":starters:commons:auth-api",

  ":starters:service-starter",
  ":starters:resource-service-starter",
  ":starters:graphql-starter",
  ":starters:test-starter",

  ":demo:auth",
  ":demo:users",
  ":demo:cart"
);
