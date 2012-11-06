/********************************************************
 * File Name: jpegencoder.sc
 * Created By: Zhicong Chen -- chen.zhico@husky.neu.edu
 * Creation Date: [2012-11-05 14:49]
 * Last Modified: [2012-11-06 02:15]
 * Licence: chenzc (c) 2012 | all rights reserved
 * Description:  
 *********************************************************/



#include <sim.sh>
#include "digicam.sh"

import "dct";
import "quantize";
import "huff";

import "i_receiver";
import "i_sender";

behavior sub_JpegEncoder( i_receiver read2jpeg, i_sender jpeg2write ) {

  int read2dct[64];
  int dct2quant[64];
  int quant2huff[64];

  DCT dct(read2dct, dct2quant);
  Quantize quantize(dct2quant, quant2huff);
  Huff  huff(quant2huff, jpeg2write);
  
  void main(void) {
    int iter;
      // encode one image
      for (iter = 0; iter < IMG_BLOCKS; iter++) {
        // receive it
        read2jpeg.receive(read2dct, IMG_BLOCK_SIZE*sizeof(int));
        // encode one image
        dct.main();
        quantize.main();
        huff.main();  // huffencod send bytes through the queue. 
      }
  }
};


behavior JpegEncoder( i_receiver read2jpeg, i_sender jpeg2write ) {

  sub_JpegEncoder jpegencoder(read2jpeg, jpeg2write);

  void main(void) {
    for( ; ; ) {
      jpegencoder.main();
    }
  }
};
