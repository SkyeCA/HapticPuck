const { Server } = require("node-osc");
const find = require('local-devices');
const axios = require('axios')

const DEBUG = true;
const DISCOVERY_REQ_TIMEOUT = 1000;
const DISCOVERY_CON_TIMEOUT = 2000;
const DISCOVERY_ENDPOINT = "/cat";
const DISCOVERY_RESPONSE = "Meow >_<"
///avatar/parameters/VFH/Zone/Touch/Headpats/Self
const AVATAR_PARAM = "/avatar/parameters/VFH/Zone/Touch/Headpats"
const SCALAR = 100
const MIN_LEVEL = 0
const TOUCH_SELF = false;

const VIBRATE_ENDPOINT = (level) => `/vibrate?val=${level}`;
const LOG = (loggable) => { if(DEBUG) console.log(`${Date.now()}: ${loggable}`) }


const findNetworkDevices = async () => {
    LOG('Scanning for haptic devices on local network, this may take a while!')
    const allDevices = await find()
    LOG(`Querying ${allDevices.length} local devices for haptics...`)

    const hapticDevices = (await Promise.all(
        allDevices.map(async (device) => {
            try {
                const data = (await axios({
                    method: 'get',
                    url: `http://${device.ip}${DISCOVERY_ENDPOINT}`,
                    timeout: DISCOVERY_REQ_TIMEOUT,
                    signal: AbortSignal.timeout(DISCOVERY_CON_TIMEOUT)
                }))?.data

                if(data !== DISCOVERY_RESPONSE){
                    LOG(`Unknown Resp On Discovery: ${data}`)
                    return;
                }

                return {
                    ip: device.ip,
                    oscParam: null,
                    found: Date.now()
                }
            } catch (error){
                LOG(error)
                return undefined
            }
        })
    )).filter(i => i)

    LOG(`Found ${hapticDevices.length} haptic devices.`)
    return hapticDevices
}

const setVibrationLevel = async (device, level) => {
    try {
        const data = (await axios({
            method: 'get',
            url: `http://${device.ip}${VIBRATE_ENDPOINT(level)}`,
            timeout: 50,
            signal: AbortSignal.timeout(100)
        }))?.data

        if(data !== "Ok"){
            LOG('Non-Ok Resp')
        }
        
        LOG(`SetLvl:${device.ip}:${level}`)
        return;
    } catch (error){
        LOG(error)
    }
}

(async () => {
    try {
        const haptics = await findNetworkDevices()
        await setVibrationLevel(haptics[0], 10)
        LOG(JSON.stringify(haptics))
    } catch (e) {
        LOG(e)
    }   
})();