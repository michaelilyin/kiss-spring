const {ApolloServer} = require("apollo-server");
const {ApolloGateway, RemoteGraphQLDataSource} = require("@apollo/gateway");
const uuid = require('uuid/v5');

const gateway = new ApolloGateway({
  serviceList: [
    // {name: "users", url: "http://localhost:6010/graphql"},
    {name: "products", url: "http://localhost:6013/graphql"},
    {name: "cart", url: "http://localhost:6011/graphql"},
  ],
  experimental_pollInterval: 1000000
});

const server = new ApolloServer({
  gateway: gateway,
  subscriptions: false
});

server.listen().then(({url}) => {
  console.log(`🚀 Server ready at ${url}`);
});
