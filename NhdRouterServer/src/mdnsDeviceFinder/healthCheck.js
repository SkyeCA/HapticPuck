const { devices } = require('../db')
const { nhdHttpClient } = require('../deviceClients')
const logger = require('../logger')

module.exports = async () => {
    logger.info('Running device health check')
    for(const device of devices.getAll()){
        const result = nhdHttpClient.healthCheck(device);
        devices.set(`${device.name}.available`, result)
        devices.set(`${device.name}.lastHealthCheck`, new Date())
    }
}