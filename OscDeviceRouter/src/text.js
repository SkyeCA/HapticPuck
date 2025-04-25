const { Server} = require("node-osc");

///avatar/parameters/VFH/Zone/Touch/Headpats/Self
const AVATAR_PARAM = "/avatar/parameters/VFH/Zone/Touch/Headpats"
const DEVICE_IP = "192.168.0.232"
const SCALAR = 100
const MIN_LEVEL = 0
const TOUCH_SELF = false;

var oscServer = new Server(9001, '0.0.0.0', () => {
  console.log('Found haptics device 192.168.0.232');
  console.log("Battery Level: 37%")
  console.log('Haptics Router is listening for OSC messages');
  console.log('Touch Self: false');
  console.log("--------------------")
});

let messageQueue = []
let lastMessage = Date.now()

const average = array => Math.floor(array.reduce((a, b) => a + b) / array.length);

const handleMessages = async () => {
    try{
        const now = Date.now();

        if(messageQueue.length === 0 || now - lastMessage > 400){
            setLevel = 0;
        } else {
            setLevel = average(messageQueue);
        }

        lastMessage = now;
        messageQueue = [];
        await fetch(`http://${DEVICE_IP}/vibrate?val=${setLevel}`);
        console.log(`SetLvl:${setLevel}`)
    } catch(error){
        console.log(error)
    }
}

oscServer.on('message', async function (msg) {
    if((msg[0] === AVATAR_PARAM + "/Self" && TOUCH_SELF) || msg[0] === AVATAR_PARAM + "/Others"){
        try {
            messageQueue.push(((parseInt(msg[1].toFixed(2) * SCALAR) / 5) + MIN_LEVEL))
        } catch (error){
            console.log(error);
        }
    }
});

setInterval(handleMessages, 300);