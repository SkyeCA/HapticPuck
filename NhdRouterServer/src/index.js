require('dotenv').config()
require('./mdnsDeviceFinder')
const { devices, oscParams, oscParamCache } = require('./db')
const { ping, healthCheck, vibrate, reset } = require('./devices/nhdHttpClient')
const oscServer = require('./osc')
const express = require('express')
const app = express()
const port = process.env.PORT

// Init Empty Cache For Params
oscParams.getAll().forEach(({ key }) => {
  oscParamCache.set(key, [])
})

app.use(express.json())

app.get('/devices', (req, res) => {
  res.send(devices.getAll().map(({value }) => value))
})

app.get('/devices/:deviceName', (req, res) => {
  const { deviceName } = req.params;

  if(!devices.has(deviceName)){
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
    return res.status(404).send("Device Not Found")
  }

  const device = devices.get(deviceName)

  if(!device.available){
    return res.status(400).send("Device is not available")
  }

  const response = await ping(device)
  return res.send(response)
})

app.get('/devices/:deviceName/reset', async(req, res) => {
  const { deviceName } = req.params;

  if(!devices.has(deviceName)){
    return res.status(404).send("Device Not Found")
  }

  const device = devices.get(deviceName)

  if(!device.available){
    return res.status(400).send("Device is not available")
  }

  const response = await reset(device)
  return res.send(response)
})

app.get('/devices/:deviceName/health', async (req, res) => {
  const { deviceName } = req.params;

  if(!devices.has(deviceName)){
    return res.status(404).send("Device Not Found")
  }

  const device = devices.get(deviceName)

  if(!device.available){
    return res.status(400).send("Device is not available")
  }

  return res.send({
    passed: await healthCheck(device)
  })
})

app.post('/devices/:deviceName/vibrate/:level', async (req, res) => {
  const { deviceName, level } = req.params;

  if(!devices.has(deviceName)){
    return res.status(404).send("Device Not Found")
  }

  const device = devices.get(deviceName)

  if(!device.available){
    return res.status(400).send("Device is not available")
  }

  const response = await vibrate(device, level)
  return res.send(response)
}) 

app.post('/osc/param', (req, res) => {
  const { deviceName, oscParam } = req.body

  if(!devices.has(deviceName)){
    return res.status(404).send("Device Not Found")
  }

  if(oscParams.has(oscParam)){
    return res.status(400).send("OSC Param Already Assigned")
  }

  oscParams.set(oscParam, deviceName)
  oscParamCache.set(oscParam, [])

  return res.send("Ok")
}) 

app.delete('/osc/param', (req, res) => {
  const { deviceName, oscParam } = req.body

  if(!devices.has(deviceName)){
    return res.status(404).send("Device Not Found")
  }

  if(!oscParams.has(oscParam)){
    return res.status(400).send("OSC Param Not Found")
  }

  oscParams.delete(oscParam)
  oscParamCache.del(oscParam)

  return res.send("Ok")
}) 

app.post('/osc/param/cache', (req, res) => {
  const { oscParam } = req.body

  if(!oscParamCache.has(oscParam)){
    return res.status(400).send("OSC Param Cache Doesn't Exist")
  }

  return res.send(oscParamCache.get(oscParam))
}) 


app.listen(port, () => {
  console.log(`Started NHD Router on port ${port}`)
})

