#include <UIPEthernet.h>
#include <LiquidCrystal_I2C.h>

/*
  Rest
  Tobias Fritsch
  20.08.2019
*/

byte mac[] = { 0x12, 0x34, 0x56, 0x78, 0x90, 0x12 };
IPAddress ip(192, 168, 2, 242);
EthernetServer server(80);
LiquidCrystal_I2C lcd(0x3F, 20, 4);

String readString = String(20);

void setup() {
  Ethernet.begin(mac, ip);

  pinMode(4, OUTPUT);
  lcd.begin();
  lcd.clear();
  lcd.print("W");
  sendaMsg();

  server.begin();
  lcd.print("F");
}

void sendaMsg() {
  lcd.print("I");
  EthernetClient client;

  if (client.connect(IPAddress(192, 168, 2, 43), 8080)) {
    
    while ((client.available()) < 0) {
      delay(10);
      lcd.print(".");
    }
    client.println(F("GET /wipf/s HTTP/1.1\r\nHost: 192.168.2.43:8080\r\n\r\n"));
    //delay(1000);
    client.stop();
    //client.flush();
    lcd.print("P");
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
        if (readString.length() < 55) {
          readString = readString + c;
        }
        if (c == '\n') {

          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println();

          client.println("{}");

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
            else if (readString.indexOf("/!~") == 4) {
              // Jede Position ansteuern mit z.B. /!~103
              int col = (readString.substring(7, 9).toInt());
              int row = readString.substring(9, 10).toInt();

              lcd.setCursor(col, row);

            }

            else {
              String ss = readString;
              ss.replace("%20", " ");

              // GET /xxx HTTP/1.1
              //      xxx
              String s = ss.substring(ss.indexOf("/") + 1, ss.lastIndexOf("HTTP/1.1") - 1);
              s.remove(s.length());
              if (s.length() <= 20) {
                lcd.print(s);
              } else {
                lcd.print("E2");
              }
            }
          }
          else {
            lcd.print("E1");
          }
          //client.print("}");
          readString = "";

          //stopping client
          client.stop();
        }
      }
    }
  }
}