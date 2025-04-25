# HapticPuck

[![The first working version of the Puck](Resources/Images/IMG_1495_small.jpeg)](Resources/Images/IMG_1495_full.jpeg)

This repo contains all the code, models, and other resources needed to create a small, general purpose haptic feedback device.

The primary use case for this project is as a Haptic feedback device for VRChat (and a custom OSC router is included), but if you know how to code, or have basic knowledge of OSC than it could be used for a wide range of applications.

## Disclaimer

I am just some random hobbyist on the internet who is sharing their personal project. This device has not been formally tested and I am not trained in electronic design/theory.

**Everything in this repo is to be used AT YOUR OWN RISK.**

If you build this device or use any component of this project you agree that I will not be held personally responsible for any harm that comes to you or others as a result of it.

## Supported Hardware

- ESP8266 (Wifi)
    - Fully tested and confirmed working.
    - Something like the Wemos D1 Mini (without presoldered pins!) is ideal

## Build Requirements

**Parts:**
- (1) ESP8266
- (1) 1k resistor
- (1) 56ohm resistor
- (1) 200UF 16v capacitor
- (1) 2n2222 transistor
- (4) 10x3mm 3V DC "Coin style" vibration motors
- (1) AMS1117 board
- (1) SS-12D00 switch
- (1) TP4056 Li-ion battery charger board
- (1) 3.7V Li-ion battery
  - I used a CS-PM150SL, but you can use *any* flat battery that fits!
- Gorilla Tough & Clear Mounting Tape and/or hot glue.


**Non-parts:**
- The ability to 3d print.
    - Check out your local library, many have 3d printers these days for use at very low cost!
- The ability and tools to solder.
- Patience

## Components

### Case/

This folder contains all the 3D models used for the HapticPuck. These were created in SketchUp 2017 and I have provided both .DAE exports of the models and Gcode files for 3d printing.

The Gcode files were generated for my printer, the Elegoo Neptune 3 Pro and I can not guarantee they'll print fine on other 3D printers.

### Firmware/

This folder contains different versions of the firmware for the microcontroller(s) that can run this project.

### OscDeviceRouter/

A custom OSC router primarily intended for getting OSC commands from VRChat and forwarding them to the HapticPuck in a format that the firmware can understand.

### Resources/

Random repo specific resources like the image at the top of this readme.

## Images

[![The puck being worn](Resources/Images/IMG_1494_small.jpeg)](Resources/Images/IMG_1494_full.jpeg)

[![Internal view of the puck](Resources/Images/IMG_1478_small.jpeg)](Resources/Images/IMG_1478_full.jpeg)
