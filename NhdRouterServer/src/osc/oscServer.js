const { Server } = require("node-osc");
const { oscParamCache } = require('../db')
const { mathUtils } = require('../utils')
const logger = require('../logger')

const oscServer = new Server(process.env.OSC_PORT, '0.0.0.0', () => {
    logger.info(`Started OSC server on port ${process.env.OSC_PORT}`)
});

oscServer.on('message', async (msg) => {
    const cache = oscParamCache.get(msg[0])

    if(cache){
        oscParamCache.set(msg[0], [...cache, mathUtils.scaleOscParam(msg[1])])
    }
});

module.exports = oscServer