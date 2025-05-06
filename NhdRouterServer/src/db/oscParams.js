const { FSDB } = require("file-system-db");

module.exports = new FSDB("./storage/oscParams.json", false);