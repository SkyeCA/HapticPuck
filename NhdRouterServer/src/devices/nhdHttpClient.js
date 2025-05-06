const axios = require('axios')
const { sleep } = require('../utils')
 
const cat = async (device) => {
    try{
        const response = (await axios.get(`http://${device.ip}:${device.port}/cat`))?.data

        return response
    } catch (error){
        console.error(error)
    }
}

const copyright = async (device) => {
    try{
        const response = (await axios.get(`http://${device.ip}:${device.port}/copyright`))?.data

        return response
    } catch (error){
        console.error(error)
    }
}

const version = async (device) => {
    try{
        const response = (await axios.get(`http://${device.ip}:${device.port}/version`))?.data

        return response
    } catch (error){
        console.error(error)
    }
}

const healthCheck = async (device) => {
    try{
        const response = (await axios.get(`http://${device.ip}:${device.port}/cat`, {
            timeout: 1000
        }))?.data

        if(response === "Meow (=･ω･=)"){
            return true
        }

        return false
    } catch (error){
        console.error(error)
        return false
    }
}

const batteryLevel = async (device) => {
    try{
        const response = (await axios.get(`http://${device.ip}:${device.port}/battery`))?.data

        return response
    } catch (error){
        console.error(error)
    }
}

const ping = async (device) => {
    try{
        await vibrate(device, 20)
        await sleep(500)
        await vibrate(device, 5)
        await sleep(500)
        await vibrate(device, 0)
    } catch (error){
        console.error(error)
    }
}

const reset = async (device) => {
    try{
        const response = (await axios.get(`http://${device.ip}:${device.port}/reset`))?.data

        return response
    } catch (error){
        console.error(error)
    }
}

const vibrate = async (device, step) => {
    try{
        const response = (await axios.get(`http://${device.ip}:${device.port}/vibrate?val=${step}`))?.data

        return response
    } catch (error){
        console.error(error)
    }
}

module.exports = {
    cat,
    copyright,
    version,
    healthCheck,
    batteryLevel,
    ping,
    reset,
    vibrate
}