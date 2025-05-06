module.exports = {
    nhdServerPort: process.env.PORT,
    oscServerPort: process.env.OSC_PORT,
    debugMode: process.env.DEBUG_MODE === "true",
    queueHandlerInterval: process.env.QUEUE_HANDLER_INTERVAL,
    deviceHealthCheckInterval: process.env.DEVICE_HEALTH_CHECK_INTERVAL
}