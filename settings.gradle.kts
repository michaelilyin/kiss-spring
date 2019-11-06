rootProject.name = "kiss-spring"

includeBuild("buildSrc")

include(
    ":starters:service-starter",
    ":starters:graphql-starter",

    ":demo:users",
    ":demo:cart"
);
