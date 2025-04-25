# Firmware

## HapticPuck_ESP8266_FW

ESP8266 specific firmware.

### Compatibility

- Physically tested on the NodeMCU 0.9.
- Should work as is on other ESP based boards like the Wemos D1 Mini that have pin 14 available.

### Naming

<ins>AP Name:</ins> NHD-HapticPuck-[serial], ex: `NHD-HapticPuck-0001`
<ins>Hostname:</ins> nhd-hapticpuck-[serial], ex: `nhd-hapticpuck-0001`

### Services Provided

- mDNS
    - Advertises itself as `nhd` protocol on TCP port `7593`
- HTTP
    - Listens on port `7593`

### Library Versions

WiFiManager 2.0.17

### Endpoints

All responses are `text/plain` unless otherwise stated.

- / [GET]
    - Returns: 
        - 200:
            - `Ok`
- /battery [GET]
    - Returns: 
        - 200:
            - `0..100`
    - Notes: Not implemented, currently always returns `24`
- /reset [GET]
    - Returns: 
        - 200:
            - `Ok`
    - Notes: Factory Resets Device
- /vibrate [GET]
    - Params:
        - `val`: `0..20`
    - Returns:
        - 500:
            - When no `val` parameter is provided.
        - 400:
            - When `val` parameter is outside acceptable range.
        - 200:
            - `Ok`
- /version [GET]
    - Returns: 
        - 200:
            - A version string, ex: `0.2`
- /copyright [GET]
    - Returns: 
        - 200:
            - A copyright notice, ex: `(C) 2025 SkyeCA - https://github.com/SkyeCA`
- /cat [GET]
    - Returns: 
        - 200:
            - `Meow (=･ω･=)`

### Serial Debug Settings

9600/8/N/1