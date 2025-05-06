const { FSDB } = require("file-system-db");

module.exports = new FSDB("./storage/db.json", false);