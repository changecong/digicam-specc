/**************************************************
 * Author: Zhicong Chen -- 10/09/2012 19:32:27
 * Email: chen.zhico@husky.neu.edu
 * Filename: digicam.sc
 * Last modified: 10/09/2012 19:32:27
 * Description:
 *************************************************/
#include "digicam.sh"

import "stimulus";
import "jpegencoder";
import "monitor";


behavior Main {

  unsigned char ScanBuffer[IMG_HEIGHT_MDU*8][IMG_WIDTH_MDU*8];
  c_handshake HS;	// handshake interface
  c_queue Q;	// queue interface

  ReadBmp R(ScanBuffer, HS);	// stimulus
  JpegEncoder J(ScanBuffer, HS, Q);  // Jpegencoder
  FileWrite F(Q);	// monitor
 
  int main(void) {
    // runs in sequential
    R.main();
    J.main();
    F.main();   

    return 0;
  }
};
