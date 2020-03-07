/*

This Programs Connects To the Internet through WifiShield (esp8266 / esp32)

and Then Recieves Text From Our Andriod App and Displays it on Serial Monitor

*/
#include <SPI.h>
#include <WiFi.h>

char ssid[] = "yourNetwork";     //  your network SSID (name)
char pass[] = "secretPassword";  // your network password
int status = WL_IDLE_STATUS;     // the Wifi radio's status

// how much serial data we expect before a newline
const unsigned int MAX_INPUT = 50;

void setup() {
  //Initialize serial and wait for port to open:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }

  // check for the presence of the shield:
  if (WiFi.status() == WL_NO_SHIELD) {
    Serial.println("WiFi shield not present");
    // don't continue:
    while (true);
  }

  String fv = WiFi.firmwareVersion();
  if (fv != "1.1.0") {
    Serial.println("Please upgrade the firmware");
  }

  // attempt to connect to Wifi network:
  while (status != WL_CONNECTED) {
    Serial.print("Attempting to connect to WPA SSID: ");
    Serial.println(ssid);
    // Connect to WPA/WPA2 network:
    status = WiFi.begin(ssid, pass);

    // wait 10 seconds for connection:
    delay(10000);
  }

  // you're connected now, so print out the data:
  Serial.print("You're connected to the network");
  printWifiData();

}
void printWifiData() {

  //Print the ssid of the network
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());

  //Print the Ip address
  IPAddress ip = WiFi.localIP();
  Serial.print("IP Address: ");
  Serial.println(ip);
}

// here to process incoming serial data after a terminator received
void process_data (const char * data)
{
  // for now just display it
  // (but you could compare it to some value, convert to an integer, etc.)
  Serial.println (data);
}  // end of process_data

void processIncomingByte (const byte inByte) //ToBe Modified For more Processing
{
  static char input_line [MAX_INPUT];
  static unsigned int input_pos = 0;

  switch (inByte)
  {

    case '\n':   // end of text
      input_line [input_pos] = 0;  // terminating null byte

      // terminator reached! process input_line here ...
      process_data (input_line);

      // reset buffer for next time
      input_pos = 0;
      break;

    case '\r':   // discard carriage return
      break;

    default:
      // keep adding if not full ... allow for terminating null byte
      if (input_pos < (MAX_INPUT - 1))
        input_line [input_pos++] = inByte;
      break;

  }  // end of switch

} // end of processIncomingByte

void loop()
{
  // if serial data available, process it
  while (Serial.available () > 0)
    processIncomingByte (Serial.read ());

  // do other stuff here like testing digital input (button presses) ...

} // end of loop