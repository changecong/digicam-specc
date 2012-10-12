// Digital Camera Example
//
// Lab 2
// Group Members: 
//   Zhicong Chen
//   Weifu Li
//   Charu Kalra

#include "digicam.sh"	// includes macros

import "read";
import "dct";
import "quantize";
import "huff";
// channel lib
import "c_handshake";
import "c_queue";

/*
 * in_port -- handshake channel
 * out_port -- queue channel
 */
behavior JpegEncoder(in unsigned char ScanBuffer[IMG_HEIGHT_MDU*8][IMG_WIDTH_MDU*8],
  i_receive in_port, i_sender out_port)
{
  // external communication variables
  unsigned int iter;
  int dctin[64];
  int dctout[64];
  int quantizeout[64];

  // implementation of behaviors
  readblock R(ScanBuffer, dctin);
  dct D(dctin, dctout);
  quantize Q(dctout, quantizeout);
  huff H(quantizeout, out_port);

  // main
  void main(void) {
    for (iter = 0; iter < IMG_BLOCKS; iter++) {
      R.main();
      D.main();
      Q.main();
      H.main();
    }
  }
};
