#include <WiFiManager.h> // 2.0.17
#include <ESP8266WebServer.h> 
#include <ESP8266mDNS.h>

#define NETWORK_PORT 7593
#define VIBRATOR_PIN D5
#define VERSION "0.2"
#define MIN_VIBRATE 75
#define STEP_VAL 7.5

WiFiManager wifiManager;
std::unique_ptr<ESP8266WebServer> server;

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
  server->send(200, "text/plain", "(C) 2025 SkyeCA - https://github.com/SkyeCA");
}

void handleCat() {
  Serial.println("Cat Handler Invoked");
  server->send(200, "text/plain", "Meow (=･ω･=)");
}

void setup() {
  Serial.begin(9600);

  Serial.println("Init");

  // Configure Wifi to work with Bell GigaHub
  WiFi.persistent(false);
  WiFi.setPhyMode(WIFI_PHY_MODE_11G);
  WiFi.hostname("nhd-hapticpuck-0001");
  //WiFi.mode(WIFI_STA); 

  // Start WifiManager AP
  Serial.println("WM Start");
  //wifiManager.setDebugOutput(false);
  wifiManager.autoConnect("NHD-HapticPuck-0001");

  // Start mdns
  Serial.println("mDNS Start");
  if (!MDNS.begin("nhd-hapticpuck-0001")) {
    Serial.println("Error setting up MDNS responder!");
    while (1) { delay(1000); }
  }
  
  Serial.println("Server Start");
  server.reset(new ESP8266WebServer(WiFi.localIP(), NETWORK_PORT));
  server->on("/", handleRoot);
  server->on("/battery", handleBattery);
  server->on("/reset", handleReset);
  server->on("/vibrate", handleVibrate);
  server->on("/version", handleVersion);
  server->on("/copyright", handleCopyright);
  server->on("/cat", handleCat);
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
}
