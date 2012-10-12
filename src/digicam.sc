// Digital Camera Example
//
// Lab 2
// Group Members: 
//   Zhicong Chen
//   Weifu Li
//   Charu Kalra

#include "digicam.sh"

import "jpegencoder";
import "monitor";
import "stimulus";

/*
 * Main behavior -- in which 3 sub-behavior run parallelly
 */
behavior Main {

  unsigned char ScanBuffer[IMG_HEIGHT_MDU*8][IMG_WIDTH_MDU*8];
  // the length of queue must be initialized
  const unsigned long Size = SIZE;
  c_handshake HS;	// handshake interface
  c_queue Q((Size));	// queue interface

  ReadBmp R(ScanBuffer, HS);	// stimulus
  JpegEncoder J(ScanBuffer, HS, Q);  // Jpegencoder
  FileWrite F(Q);	// monitor
 
  int main(void) {
    // runs in sequential
    par {
      R.main();
      J.main();
      F.main();   
    }
    return 0;
  }
};  // Main end
