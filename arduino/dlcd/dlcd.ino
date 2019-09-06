#include <UIPEthernet.h>
#include <LiquidCrystal_I2C.h>

/*
  Rest
  Tobias Fritsch
  23.08.2019
*/

#define OKT1 192
#define OKT2 168
#define OKT3 2
#define MACBYTE 0x11

byte mac[] = { MACBYTE, MACBYTE, MACBYTE, MACBYTE, MACBYTE, MACBYTE };
IPAddress ip(OKT1, OKT2, OKT3, 242);
EthernetServer server(80);
LiquidCrystal_I2C lcd(0x3F, 20, 4);

String readString = String(20);

void setup() {
  // Pin 10 bis 13 nicht verwenden
  Ethernet.begin(mac, ip);

  pinMode(4, OUTPUT);
  pinMode(5, INPUT);
  lcd.begin();
  lcd.clear();
  lcd.print("W");
  sendaMsg();
  delay(500);
  server.begin();
  lcd.print("F");
}

void sendaMsg() {
  lcd.print("I");
  EthernetClient client;

  if (client.connect(IPAddress(OKT1, OKT2, OKT3, 10), 8080)) {
    lcd.print("P");
    client.println("GET /s HTTP/1.1\r\nHost: 0.0.0.0:8080\r\n\r\n");
    delay(500);
    client.stop();
  }
  else {
    delay(1000);
    sendaMsg();
  }
}


void loop() {
  EthernetClient client = server.available();
  if (client) {
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        if (readString.length() < 36) {
          readString = readString + c;
        }
        if (c == '\n') {

          client.println("HTTP/1.1 200 OK");
          //client.println("Content-Type: text/html");
          client.println();

          client.print("{");
          client.print(digitalRead(5));
          client.println("}");

          // Nur PUT zulassen
          if (readString.indexOf("PUT /") > -1) {


            if (readString.indexOf("doOn")  > -1) {
              digitalWrite(4, HIGH);
              //client.print("ON");
            }
            else if (readString.indexOf("doOff")  > -1) {
              digitalWrite(4, LOW);
              //client.print("OFF");
            }
            else if (readString.indexOf("cls")  > -1) {
              lcd.clear();
              lcd.setCursor(0, 0);
            }
            else {
              // Format: 103/text
              // row 1
              // col 03
              lcd.setCursor(readString.substring(6, 8).toInt(), readString.substring(5, 6).toInt());

              String s = readString.substring(8, readString.lastIndexOf("HTTP/1.1") - 1);
              s.replace("%20", " ");
              s.remove(s.length());

              lcd.print(s);

            }
          }
          else {
            lcd.print("E1");
          }
          readString = "";

          //stopping client
          client.stop();
        }
      }
    }
  }
}