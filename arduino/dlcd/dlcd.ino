#include <UIPEthernet.h>
#include <LiquidCrystal_I2C.h>

/*
  RestClient GET

  Tobias Fritsch
  02.08.2019
*/

byte mac[] = { 0x12, 0x34, 0x56, 0x78, 0x90, 0x12 };
IPAddress ip(192, 168, 2, 242);
EthernetServer server(80);
LiquidCrystal_I2C lcd(0x3F, 20, 4);

String readString = String(20);

void setup() {
  Ethernet.begin(mac, ip);
  server.begin();
  pinMode(4, OUTPUT);

  lcd.begin();
  lcd.clear();

  lcd.print("Wipf");
}

void loop() {
  EthernetClient client = server.available();
  if (client) {
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        if (readString.length() < 24) {
          readString = readString + c;
        }
        if (c == '\n') {

          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println();

          client.print("{");


          if (readString.indexOf("doOn")  > -1) {
            digitalWrite(4, HIGH);
            client.print("ON");
          }
          else if (readString.indexOf("doOff")  > -1) {
            digitalWrite(4, LOW);
            client.print("OFF");
          }
          else if (readString.indexOf("cls")  > -1) {
            lcd.clear();
            lcd.setCursor(0, 0);
          }
          else if (readString.indexOf("gL0") > -1)
            lcd.setCursor(0, 0);
          else if (readString.indexOf("gL1") > -1)
            lcd.setCursor(0, 1);
          else if (readString.indexOf("gL2") > -1)
            lcd.setCursor(0, 2);
          else if (readString.indexOf("gL3") > -1)
            lcd.setCursor(0, 3);

          else {
            String s = readString.substring(readString.indexOf('/') + 1, readString.lastIndexOf("HTTP") - 1);
            s.remove(s.length());

            s.replace("%20", " ");

            lcd.print(s);
          }
          client.print("}");
          readString = "";

          //stopping client
          client.stop();
        }
      }
    }
  }
}
