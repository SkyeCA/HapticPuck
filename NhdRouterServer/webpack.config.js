const path = require('path');

module.exports = {
    mode: "production",
    entry: "./src/index.js",
    target: "node",
    output: {
      path: path.resolve(__dirname, "build", "bundle"),
      chunkFormat: "commonjs",
    },
  };