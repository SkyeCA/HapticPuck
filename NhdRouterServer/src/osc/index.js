const { Server } = require("node-osc");
const { oscParams, oscParamCache } = require('../db')

const oscServer = new Server(process.env.OSC_PORT, '0.0.0.0', () => {
    console.log(`Started OSC server on port ${process.env.OSC_PORT}`)
});

oscServer.on('message', async (msg) => {
    const cache = oscParamCache.get(msg[0])

    if(cache){
        oscParamCache.set(msg[0], [...cache, msg[1]])
    }
});

module.exports = {
    oscServer
}