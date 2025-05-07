const mdns = require('mdns');
const { devices } = require('../db')
const logger = require('../logger')

/* MDNS example object:

{
  interfaceIndex: 11,
  type: ServiceType {
    name: 'nhd',
    protocol: 'tcp',
    subtypes: [],
    fullyQualified: true
  },
  replyDomain: 'local.',
  flags: 2,
  name: 'nhd-hapticpuck-0001',
  networkInterface: 'en0',
  fullname: 'nhd-hapticpuck-0001._nhd._tcp.local.',
  host: 'nhd-hapticpuck-0001.local.',
  port: 7593,
  addresses: [ '192.168.0.232' ]
}*/

const browser = mdns.createBrowser(mdns.tcp('nhd'));

browser.on('serviceUp', service => {
  devices.set(service.name, {
    name: service.name,
    type: service.type.name,
    port: service.port,
    ip: service.addresses[0],
    currentStep: 0,
    available: true,
    lastHealthCheck: new Date()
  })
  logger.info("Found MDNS Service: ", service.name);
});

browser.on('serviceDown', service => {
  devices.set(`${service.name}.available`, false)
  devices.set(`${service.name}.lastHealthCheck`, null)
  logger.info("Lost MDNS Service: ", service.name);
});

browser.start()