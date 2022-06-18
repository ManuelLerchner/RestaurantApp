const express = require("express");
const Cors = require("cors");
const bodyParser = require("body-parser");

const app = express();

//use cors
app.use(Cors());
app.use(bodyParser.json());

app.use("/auth", require("./routes/auth"));
app.use("/restaurants", require("./routes/restaurants"));

app.listen(8080, () => {
  console.log("Server started on port 8080");
});
