/********************************************************
 * File Name: design.sc
 * Created By: Zhicong Chen -- chen.zhico@husky.neu.edu
 * Creation Date: [2012-11-05 14:26]
 * Last Modified: [2012-11-06 00:19]
 * Licence: chenzc (c) 2012 | all rights reserved
 * Description:  
 *********************************************************/

#include <sim.sh>
#include "digicam.sh"

import "read.sc";
import "jpegencoder.sc";
import "write.sc";

import "i_receive";
import "i_sender";
import "c_queue";  // inner

behavior Design(in unsigned char ScanBuffer[IMG_HEIGHT_MDU*8][IMG_WIDTH_MDU*8],
                i_receive start, i_sender blocks ) {

  // initialize the queue
  const unsigned long qSize = 512;
  c_queue read2jpeg(qSize);
  c_queue jpeg2write(qSize);

  int block[64];

  ReadBlock read(ScanBuffer, start, read2jpeg);
  JpegEncoder jpeg(read2jpeg, jpeg2write);
  WriteBlock write(jpeg2write, blocks);

  void main(void) {
      par {
        read.main();
        jpeg.main();
        write.main();
      }
  } 

};
