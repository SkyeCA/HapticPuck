require('dotenv').config()
require('./mdnsDeviceFinder')
const { devices, oscParams, oscParamCache } = require('./db')
const { nhdHttpClient } = require('./deviceClients')
const { oscServer, oscParamQueueHandler } = require('./osc')
const deviceHealthCheck = require('./mdnsDeviceFinder/healthCheck')
const config = require('./config')
const logger = require('./logger')
const express = require('express')

const app = express()

app.use(express.json())

// Init Empty Cache For Params
oscParams.getAll().forEach(({ key }) => {
  oscParamCache.set(key, [])
})

// Start OSC queue handler
setInterval(oscParamQueueHandler, config.queueHandlerInterval)

// Start periodic device health check
setInterval(deviceHealthCheck, config.deviceHealthCheckInterval)

app.get('/devices', (req, res) => {
  res.send(devices.getAll().map(({value }) => value))
})

app.get('/devices/:deviceName', (req, res) => {
  const { deviceName } = req.params;

  if(!devices.has(deviceName)){
    logger.warn(`Device ${deviceName} not found`)
    return res.status(404).send("Device Not Found")
  }

  res.send(devices.get(req.params.deviceName))
})

app.post('/devices/:deviceName', (req, res) => {
  const { deviceName } = req.params;

  return "Update Device Info/Settings"
})

app.get('/devices/:deviceName/ping', async (req, res) => {
  const { deviceName } = req.params;

  if(!devices.has(deviceName)){
    logger.warn(`Device ${deviceName} not found`)
    return res.status(404).send("Device Not Found")
  }

  const device = devices.get(deviceName)

  if(!device.available){
    logger.warn(`Device ${deviceName} not available`)
    return res.status(400).send("Device is not available")
  }

  const response = await nhdHttpClient.ping(device)
  return res.send(response)
})

app.get('/devices/:deviceName/reset', async(req, res) => {
  const { deviceName } = req.params;

  if(!devices.has(deviceName)){
    logger.warn(`Device ${deviceName} not found`)
    return res.status(404).send("Device Not Found")
  }

  const device = devices.get(deviceName)

  if(!device.available){
    logger.warn(`Device ${deviceName} not available`)
    return res.status(400).send("Device is not available")
  }

  const response = await nhdHttpClient.reset(device)
  return res.send(response)
})

app.get('/devices/:deviceName/health', async (req, res) => {
  const { deviceName } = req.params;

  if(!devices.has(deviceName)){
    logger.warn(`Device ${deviceName} not found`)
    return res.status(404).send("Device Not Found")
  }

  const device = devices.get(deviceName)

  if(!device.available){
    logger.warn(`Device ${deviceName} not available`)
    return res.status(400).send("Device is not available")
  }

  return res.send({
    passed: await nhdHttpClient.healthCheck(device)
  })
})

app.post('/devices/:deviceName/vibrate/:level', async (req, res) => {
  const { deviceName, level } = req.params;

  if(!devices.has(deviceName)){
    logger.warn(`Device ${deviceName} not found`)
    return res.status(404).send("Device Not Found")
  }

  const device = devices.get(deviceName)

  if(!device.available){
    logger.warn(`Device ${deviceName} not available`)
    return res.status(400).send("Device is not available")
  }

  const response = await nhdHttpClient.vibrate(device, level)
  return res.send(response)
}) 

app.post('/osc/param', (req, res) => {
  const { deviceName, oscParam } = req.body

  if(!devices.has(deviceName)){
    logger.warn(`Device ${deviceName} not found`)
    return res.status(404).send("Device Not Found")
  }

  if(oscParams.has(oscParam)){
    logger.warn(`OSC Param ${oscParam} already assigned`)
    return res.status(400).send("OSC Param Already Assigned")
  }

  oscParams.set(oscParam, deviceName)
  oscParamCache.set(oscParam, [])

  return res.send("Ok")
}) 

app.delete('/osc/param', (req, res) => {
  const { deviceName, oscParam } = req.body

  if(!devices.has(deviceName)){
    logger.warn(`Device ${deviceName} not found`)
    return res.status(404).send("Device Not Found")
  }

  if(!oscParams.has(oscParam)){
    logger.warn(`OSC Param ${oscParam} not found`)
    return res.status(400).send("OSC Param Not Found")
  }

  oscParams.delete(oscParam)
  oscParamCache.del(oscParam)

  return res.send("Ok")
}) 

app.post('/osc/param/cache', (req, res) => {
  const { oscParam } = req.body

  if(!oscParamCache.has(oscParam)){
    logger.warn(`OSC Param Cache ${oscParam} doesn't exist`)
    return res.status(400).send("OSC Param Cache Doesn't Exist")
  }

  return res.send(oscParamCache.get(oscParam))
}) 

app.get('health', (req, res) => {
  return res.send("Ok")
}) 

app.listen(config.nhdServerPort, () => {
  logger.info(`Started NHD Router on port ${config.nhdServerPort}`)
})

