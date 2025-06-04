#include <WiFiManager.h> // 2.0.17
#include <ESP8266WebServer.h> 
#include <ESP8266mDNS.h>

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

char deviceId[28];
int batterySteps;
bool lowBattery, depletedBattery = false;
unsigned long lastBatteryCheckMillis;
WiFiManager wifiManager;
std::unique_ptr<ESP8266WebServer> server;

void ICACHE_RAM_ATTR resetDevice(){
  Serial.println("Factory reset in progress.");
  WiFi.disconnect(true);
  ESP.eraseConfig();
  ESP.restart();
}

void lowBatteryStateHandler(){
  if(!lowBattery && batterySteps <= BATTERY_LOW_STEPS){
    lowBattery = true;
    //vibrate motor in a pattern here
  }

  if(!depletedBattery && batterySteps <= BATTERY_DISCHARGE_STEPS){
    depletedBattery = true;
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

void handleRoot() {
  Serial.println("Root Handler Invoked");
  server->send(200, "text/plain", "Ok");
}

void handleNotFound(){
  Serial.println("Not Found Handler Invoked");
  server->send(404, "text/plain", "404");
}

void handleVibrate() {
  Serial.println("Vibrate Handler Invoked");

  if(depletedBattery){
    Serial.println("Low battery, vibrate disabled.");
    server->send(503, "text/plain", "Low battery, device disabled.");
    return;
  }

  if(!server->hasArg("val")){
    Serial.println("No val provided.");
    server->send(500, "text/plain", "No Vibrate Val Provided. [0..20]");
    return;
  }

  int vibrateValue = server->arg("val").toInt();

  if(vibrateValue < 0 || vibrateValue > 20){
    Serial.println("Invalid val range");
    server->send(400, "text/plain", "Only range 0..20 is valid");
    return;
  } else if(vibrateValue == 0){
    Serial.println("Turned off");
    analogWrite(VIBRATOR_PIN, 0);
    server->send(200, "text/plain", "Ok");
    return;
  }

  Serial.println("Set vibration level.");
  analogWrite(VIBRATOR_PIN, MIN_VIBRATE + (vibrateValue * STEP_VAL));
  server->send(200, "text/plain", "Ok");
  return;
}

void handleBatteryPercent() {
  Serial.println("Battery Percent Handler Invoked");
  server->send(200, "text/plain", String(calculateBatteryPercent()));
}

void handleBatteryVoltage() {
  Serial.println("Battery Voltage Handler Invoked");
  server->send(200, "text/plain", String(calculateBatteryVoltage()));
}

void handleReset() {
  server->send(200, "text/plain", "Ok");
  resetDevice();
}

void handleVersion() {
  Serial.println("Version Handler Invoked");
  server->send(200, "text/plain", VERSION);
}

void handleCopyright() {
  Serial.println("Copyright Handler Invoked");
  server->send(200, "text/plain", "(C) 2025 SkyeCA - https://github.com/SkyeCA");
}

void handleCat() {
  Serial.println("Cat Handler Invoked");
  server->send(200, "text/plain", "Meow (=･ω･=)");
}

void handleDeviceId() {
  Serial.println("Device ID Handler Invoked");
  server->send(200, "text/plain", deviceId);
}

void handleCompatibility() {
  Serial.println("Handle Compatibility Invoked");
  server->send(200, "text/plain", "HapticPuck3/nhd");
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
  
  Serial.println("Server Start");
  server.reset(new ESP8266WebServer(WiFi.localIP(), NETWORK_PORT));
  server->on("/", handleRoot);
  server->on("/battery/percent", handleBatteryPercent);
  server->on("/battery/voltage", handleBatteryVoltage);
  server->on("/reset", handleReset);
  server->on("/vibrate", handleVibrate);
  server->on("/version", handleVersion);
  server->on("/copyright", handleCopyright);
  server->on("/compatibility", handleCompatibility);
  server->on("/cat", handleCat);
  server->on("/id", handleDeviceId);
  server->onNotFound(handleNotFound);
  server->begin();

  // Add mDNS Service
  Serial.println("Add mDNS Service");
  MDNS.addService("nhd", "tcp", NETWORK_PORT);

  Serial.println("Ready!");
}

void loop() {
  server->handleClient();
  MDNS.update();
  updateBatteryState();
}
