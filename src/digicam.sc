/********************************************************
 * File Name: digicam.sc
 * Created By: Zhicong Chen -- chen.zhico@husky.neu.edu
 * Creation Date: [2012-11-05 14:45]
 * Last Modified: [2012-11-06 00:19]
 * Licence: chenzc (c) 2012 | all rights reserved
 * Description:  
 *********************************************************/

#include <sim.sh>
#include "digicam.sh"

import "stimulus";
import "design";
import "monitor";

import "c_queue";
import "c_handshake";
import "c_double_handshake";


behavior TestBench() {
  
  unsigned char ScanBuffer[IMG_HEIGHT_MDU*8][IMG_WIDTH_MDU*8];  // buffer
  c_handshake start;  // handshake
  c_double_handshake bytes;  // double handshake

  Stimulus stimulus(ScanBuffer, start);
  Design design(ScanBuffer, start, bytes);
  Monitor monitor(bytes);

  void main(void) {

    par{
      stimulus.main();
      design.main();
      monitor.main();
    }
  }
};

behavior Main {
  TestBench testbench;
  
  int main(void) {
    testbench.main();

    return 0;
  }
  
};
