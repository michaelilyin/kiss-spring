const {ApolloServer} = require("apollo-server");
const {ApolloGateway} = require("@apollo/gateway");

const gateway = new ApolloGateway({
  serviceList: [
    {name: "users", url: "http://localhost:6010/graphql"},
  ]
});

const server = new ApolloServer({
  gateway: gateway,
  subscriptions: false
});

server.listen().then(({url}) => {
  console.log(`🚀 Server ready at ${url}`);
});
