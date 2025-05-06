const SCALAR = 100
const MIN_LEVEL = 0

module.exports = {
    averageArray: (array) => Math.floor(array.reduce((a, b) => a + b) / array.length),
    scaleOscParam: (number) => (parseInt(number.toFixed(2) * SCALAR) / 5) + MIN_LEVEL
}