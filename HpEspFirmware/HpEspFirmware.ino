#include <WiFiManager.h>
#include <ESP8266WebServer.h>
#include "mdns.h"

#define VIBRATOR_PIN D5
#define VERSION "0.1"
#define MIN_VIBRATE 75
#define STEP_VAL 7.5

WiFiManager wifiManager;
std::unique_ptr<ESP8266WebServer> server;

void handleRoot() {
  Serial.println("Root Handler Invoked");
  server->send(200, "text/plain", "hello from esp8266!");
}

void handleNotFound(){
  Serial.println("Not Found Handler Invoked");
  server->send(404, "text/plain", "404: Not found");
}

void handleVibrate() {
  Serial.println("Vibrate Handler Invoked");

  if(!server->hasArg("val")){
    Serial.println("No val provided.");
    server->send(500, "text/plain", "No Vibrate Val Provided. [0..100]");
    return;
  }

  int vibrateValue = server->arg("val").toInt();

  if(vibrateValue < 0 || vibrateValue > 20){
    Serial.println("Invalid val range.");
    server->send(400, "text/plain", "Only range 0..20 is valid");
    return;
  } else if(vibrateValue == 0){
    Serial.println("Turned off.");
    server->send(200, "text/plain", "Ok");
    analogWrite(VIBRATOR_PIN, 0);
    return;
  }

  Serial.println("Set vibration level.");
  analogWrite(VIBRATOR_PIN, MIN_VIBRATE + (vibrateValue * STEP_VAL));
  server->send(200, "text/plain", "Ok");
  return;
}

void handleBattery() {
  Serial.println("Battery Handler Invoked");
  server->send(200, "text/plain", "24");
}

void handleReset() {
  server->send(200, "text/plain", "Ok");
  WiFi.disconnect(true);
  ESP.eraseConfig();
}

void handleVersion() {
  Serial.println("Version Handler Invoked");
  server->send(200, "text/plain", VERSION);
}

void handleCopyright() {
  Serial.println("Copyright Handler Invoked");
  server->send(200, "text/plain", "(C) 2025 Connor Oliver/SkyeCA (connor@muezza.ca), http://muezza.ca");
}

void handleCat() {
  Serial.println("Cat Handler Invoked");
  server->send(200, "text/plain", "Meow >_<");
}

void setup() {
  Serial.begin(9600);

  Serial.println("Init");

  //digitalWrite(VIBRATOR_PIN, HIGH);

  // Configure Wifi to work with Bell GigaHub
  WiFi.persistent(false);
  WiFi.setPhyMode(WIFI_PHY_MODE_11G);
  WiFi.hostname("MZA-HapticPuck-001");
  //WiFi.mode(WIFI_STA); 

  // Start WifiManager AP
  Serial.println("WM Start");
  //wifiManager.setDebugOutput(false);
  wifiManager.autoConnect("MZA_HapticPuck");
  
  Serial.println("Server Start");
  server.reset(new ESP8266WebServer(WiFi.localIP(), 80));

  server->on("/", handleRoot);
  server->on("/battery", handleBattery);
  server->on("/reset", handleReset);
  server->on("/vibrate", handleVibrate);
  server->on("/version", handleVersion);
  server->on("/copyright", handleCopyright);
  server->on("/cat", handleCat);
  server->onNotFound(handleNotFound);
  server->begin();
  Serial.println("Ready!");
}

void loop() {
  server->handleClient();
}
