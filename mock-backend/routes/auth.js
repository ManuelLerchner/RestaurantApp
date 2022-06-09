const express = require("express");
const router = express.Router();
const db = require("../db");

var crypto = require("crypto");

router.post("/login", (req, res) => {
  let email = req.body.email;
  let password = req.body.password;

  let user = db.users.find((user) => user.email === email && user.password === password);

  if (user) {
    let authToken = crypto.randomBytes(64).toString("hex");

    res.json({
      username: user.username,
      email: user.email,
      authToken: authToken,
    });

    console.log("login success");

    return;
  }

  console.log("login failure");

  res.status(401).send("Incorrect email or password.");
});

module.exports = router;
