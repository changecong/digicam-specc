// Digital Camera Example
//
// Lab 2
// Group Members: 
//   <login name>, <student id>
//
//


#include <stdio.h>
#include <sim.sh>
#include "digicam.sh"

import "stimulus";
import "read";
import "dct";
import "quantize";
import "huff";
import "monitor";

import "c_queue";
import "c_handshake";


behavior JpegEncoder(unsigned char ScanBuffer[IMG_HEIGHT_MDU*8][IMG_WIDTH_MDU*8], 
                i_receive start, i_sender bytes) {

  int read2dct[64];
  int dct2quant[64];
  int quant2huff[64];

  Read read(ScanBuffer, read2dct);
  DCT dct(read2dct, dct2quant);
  Quantize quantize(dct2quant, quant2huff);
  Huff  huff(quant2huff, bytes);
  
  void main(void) {
    int iter;
  
    while(1) {
      start.receive();
      for (iter = 0; iter < IMG_BLOCKS; iter++) {
        read;
        dct;
        quantize;
        huff;
      }
      waitfor(200 MILLI_SEC);
    }
  }
};

behavior Main {  
  unsigned char ScanBuffer[IMG_HEIGHT_MDU*8][IMG_WIDTH_MDU*8];
  c_handshake start;
  const unsigned long qSize = 512;
  c_queue bytes(qSize);

  Stimulus stimulus(ScanBuffer, start);
  JpegEncoder jpegEncoder(ScanBuffer, start, bytes);
  Monitor monitor(bytes);

  int main(void) {

    par{
      stimulus;
      jpegEncoder;
      monitor;

    };

    return 0;
  }
};
