const { oscParams, oscParamCache, devices } = require('../db')
const { mathUtils } = require('../utils')
const { vibrate } = require('../deviceClients/nhdHttpClient')
const config = require('../config')
const logger = require('../logger')

module.exports = async () => {
    const caches = oscParams.getAll()

    for(const { key: param, value: deviceName } of caches) {
        const cache = oscParamCache.get(param)
        const device = devices.get(deviceName)
        let newStep;

        if(cache.length === 0){ // or if last message was more than 400ms ago
            newStep = 0;
        } else {
            newStep = mathUtils.averageArray(cache)
        }

        if(!device || (!device.available && !config.debugMode)){
            logger.info("Device doesn't exist or isn't available")
        } else if (newStep !== device.currentStep){
            logger.info('Set new level to ' + newStep)

            if(!config.debugMode){
                await vibrate(device, newStep)
            } else {
                logger.info(`Debug: Set vibrate to ${newStep}`)
            }

            devices.set(`${device.name}.currentStep`, newStep)
        } else {
            logger.info("Level unchanged")
        }

        oscParamCache.set(param, [])
        logger.info("Queue handled")
    }
}