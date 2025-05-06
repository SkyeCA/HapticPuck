module.exports = async (sleepMs) => {
    await new Promise(resolve => setTimeout(resolve, sleepMs));
}