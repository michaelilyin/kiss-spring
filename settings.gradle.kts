rootProject.name = "kiss-spring"

includeBuild("buildSrc")

include(
    ":starters:service-starter",
    ":starters:graphql-starter",
    ":starters:test-starter",

    ":demo:users",
    ":demo:cart"
);
