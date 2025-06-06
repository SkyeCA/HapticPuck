#define WEBSERVER_H // Required to prevent collision between WebRequestMethod defined in WifiManager and ESPAsyncWebServer
#define NETWORK_PORT 7593
#define VIBRATOR_PIN D1
#define BATTERY_READ_EN_PIN D6
#define BATTERY_READ_PIN A0
#define RESET_BUTTON_PIN D2
#define VERSION "0.4"
#define MIN_VIBRATE 75
#define STEP_VAL 7.5
// The following numbers account for voltage divider differences! Ex. 410 * 0.0032 = 1.31
// 1.31v would be far too low for a li-ion battery, but the voltage divider difference needs to be added: 1.31 + 1.4 = 2.71
#define BATTERY_DISCHARGE_STEPS 410
#define BATTERY_LOW_STEPS 530
#define BATTERY_CHARGED_STEPS 875
#define VOLTAGE_DIVIDER_DIFF_VOLTS 1.4
#define ADC_STEP_DIVISION_VOLTS 0.0032

#include <WiFiManager.h> // 2.0.17
#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <ESPAsyncTCP.h>
#include <ESPAsyncWebServer.h>
#include <ESP8266mDNS.h>

char deviceId[28];
int batterySteps;
bool lowBattery, depletedBattery = false;
unsigned long lastBatteryCheckMillis;
WiFiManager wifiManager;
AsyncWebServer server(NETWORK_PORT);
AsyncWebSocket ws("/ws");

void ICACHE_RAM_ATTR resetDevice(){
  Serial.println("Factory reset in progress.");
  WiFi.disconnect(true);
  ESP.eraseConfig();
  ESP.restart();
}

void lowBatteryStateHandler(){
  if(!lowBattery && batterySteps <= BATTERY_LOW_STEPS){
    lowBattery = true;
    ws.textAll("BAT_LOW");
    //vibrate motor in a pattern here
  }

  if(!depletedBattery && batterySteps <= BATTERY_DISCHARGE_STEPS){
    depletedBattery = true;
    ws.textAll("BAT_EMPTY");
  }
}

void updateBatteryState(){
  if (millis() - lastBatteryCheckMillis >= 30*1000UL) {
    lastBatteryCheckMillis = millis();  
   
    digitalWrite(BATTERY_READ_EN_PIN, HIGH);
    delay(200);
    batterySteps = analogRead(BATTERY_READ_PIN);
    digitalWrite(BATTERY_READ_EN_PIN, LOW);

    Serial.print("New battery level: ");
    Serial.println(batterySteps);

    lowBatteryStateHandler();
  }
}

float calculateBatteryVoltage(){
  return (ADC_STEP_DIVISION_VOLTS * batterySteps) + VOLTAGE_DIVIDER_DIFF_VOLTS;
}

float calculateBatteryPercent(){
  return ((batterySteps-BATTERY_DISCHARGE_STEPS) * 100) / (BATTERY_CHARGED_STEPS - BATTERY_DISCHARGE_STEPS);
}

void handleVibrate() {
  Serial.println("Vibrate Handler Invoked");

  if(depletedBattery){
    Serial.println("Low battery, vibrate disabled.");
    ws.textAll("BAT_LOW_DISABLED");
    return;
  }

  //if(!server->hasArg("val")){
  //  Serial.println("No val provided.");
  //  ws.textAll("No Vibrate Val Provided. [0..20]");
  //  return;
  //}

  //int vibrateValue = server->arg("val").toInt();
  int vibrateValue = 0;

  if(vibrateValue < 0 || vibrateValue > 20){
    Serial.println("Invalid val range");
    ws.textAll("Only range 0..20 is valid");
    return;
  } else if(vibrateValue == 0){
    Serial.println("Turned off");
    analogWrite(VIBRATOR_PIN, 0);
    ws.textAll("Ok");
    return;
  }

  Serial.println("Set vibration level.");
  analogWrite(VIBRATOR_PIN, MIN_VIBRATE + (vibrateValue * STEP_VAL));
  ws.textAll("Ok");
  return;
}

void handleBatteryPercent() {
  Serial.println("Battery Percent Handler Invoked");
  ws.textAll(String(calculateBatteryPercent()));
}

void handleBatteryVoltage() {
  Serial.println("Battery Voltage Handler Invoked");
  ws.textAll(String(calculateBatteryVoltage()));
}

void handleReset() {
  Serial.println("Reset Handler Invoked");
  ws.textAll("Ok");
  resetDevice();
}

void handleVersion() {
  Serial.println("Version Handler Invoked");
  ws.textAll(VERSION);
}

void handleCopyright() {
  Serial.println("Copyright Handler Invoked");
  ws.textAll("(C) 2025 SkyeCA - https://github.com/SkyeCA");
}

void handleCat() {
  Serial.println("Cat Handler Invoked");
  ws.textAll("Meow (=･ω･=)");
}

void handleDeviceId() {
  Serial.println("Device ID Handler Invoked");
  ws.textAll(deviceId);
}

void handleCompatibility() {
  Serial.println("Handle Compatibility Invoked");
  ws.textAll("HapticPuck3/nhdws");
}

void handleWebSocketMessage(void *arg, uint8_t *data, size_t len) {
  AwsFrameInfo *info = (AwsFrameInfo*)arg;
  if (info->final && info->index == 0 && info->len == len && info->opcode == WS_TEXT) {
    data[len] = 0;
    String message = (char*)data;
    // Check if the message is "getReadings"
    if (strcmp((char*)data, "BAT_PCT") == 0) {
      handleBatteryPercent();
    }
  }
}

void onEvent(AsyncWebSocket *server, AsyncWebSocketClient *client, AwsEventType type, void *arg, uint8_t *data, size_t len) {
  switch (type) {
    case WS_EVT_CONNECT:
      Serial.printf("WebSocket client #%u connected from %s\n", client->id(), client->remoteIP().toString().c_str());
      break;
    case WS_EVT_DISCONNECT:
      Serial.printf("WebSocket client #%u disconnected\n", client->id());
      break;
    case WS_EVT_DATA:
      handleWebSocketMessage(arg, data, len);
      break;
    case WS_EVT_PONG:
    case WS_EVT_ERROR:
      break;
  }
}

void setup() {
  Serial.begin(9600);
  Serial.println("Init...");

  // Set device name
  snprintf(deviceId,28, "nhd-hapticpuck-%04X%08X", (uint32_t) ESP.getChipId());
  Serial.print("Device ID is: ");
  Serial.println(deviceId);

  // Setup Pins
  attachInterrupt(RESET_BUTTON_PIN, resetDevice, RISING);

  // Configure Wifi to work with Bell GigaHub
  WiFi.persistent(false);
  WiFi.setPhyMode(WIFI_PHY_MODE_11G);
  WiFi.hostname(deviceId);
  //WiFi.mode(WIFI_STA); 

  // Start WifiManager AP
  Serial.println("WM Start");
  //wifiManager.setDebugOutput(false);
  wifiManager.autoConnect(deviceId);

  // Start mdns
  Serial.println("mDNS Start");
  if (!MDNS.begin(deviceId)) {
    Serial.println("Error setting up MDNS responder!");
    while (1) { delay(1000); }
  }

  // Setup Websocket
  ws.onEvent(onEvent);
  server.addHandler(&ws);
  server.begin();

  // Add mDNS Service
  Serial.println("Add mDNS Service");
  MDNS.addService("nhd", "ws", NETWORK_PORT);

  Serial.println("Ready!");
}

void loop() {
  ws.cleanupClients();
  MDNS.update();
  updateBatteryState();
}
