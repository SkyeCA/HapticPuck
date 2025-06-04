# Firmware

## HapticPuck_WemosD1Mini_FW

Wemos D1 Mini specific firmware.

### Compatibility

- Physically tested on the NodeMCU 0.9 and Wemos D1 Mini
- Wemos D1 Mini is the target device.

### Naming

<ins>AP Name:</ins> NHD-HapticPuck-[chipId], ex: `nhd-hapticpuck-AA0FD3`

<ins>Hostname:</ins> nhd-hapticpuck-[chipId], ex: `nhd-hapticpuck-AA0FD3`

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
- /battery/percent [GET]
    - Returns: 
        - 200:
            - `87`
- /battery/voltage [GET]
    - Returns: 
        - 200:
            - `3.7`
- /reset [GET]
    - Returns: 
        - 200:
            - `Ok`
    - Notes: Factory Resets Device Immediately
- /vibrate [GET]
    - Params:
        - `val`: `0..20`
    - Returns:
        - 500:
            - When no `val` parameter is provided.
        - 503:
            - Device battery is depleted, device is disabled.
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
- /id [GET]
    - Returns: 
        - 200:
            - `nhd-hapticpuck-AA0FD3`
- /compatibility [GET]
    - Returns: 
        - 200:
            - `HapticPuck3/nhd`


### Serial Debug Settings

9600/8/N/1