db.createUser(
  {
    user: "demo",
    pwd: "secret",
    roles: [
      {
        role: "readWrite",
        db: "kiss-demo"
      }
    ]
  }
);
