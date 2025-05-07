const { devices } = require('../db')
const { nhdHttpClient } = require('../deviceClients')
const logger = require('../logger')
const config = require('../config')

module.exports = async () => {
    logger.info('Running device health check')
    for(const { key, value } of devices.getAll()){
        const result = await nhdHttpClient.healthCheck(value);
        devices.set(`${key}.available`, result)
        devices.set(`${key}.lastHealthCheck`, new Date())
    }

    if(config.debugMode){
        logger.info('Health check report')

        devices.getAll().forEach(({ value }) => {
            logger.info(`${value.name}: ${value.available}`)
        })
    }

}