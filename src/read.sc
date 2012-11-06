/********************************************************
 * File Name: read.sc
 * Created By: Zhicong Chen -- chen.zhico@husky.neu.edu
 * Creation Date: [2012-11-05 14:14]
 * Last Modified: [2012-11-06 02:15]
 * Licence: chenzc (c) 2012 | all rights reserved
 * Description:  
 *********************************************************/


#include "digicam.sh"

import "i_receive";
import "i_sender";

behavior ReadBlock(in unsigned char ScanBuffer[IMG_HEIGHT_MDU*8][IMG_WIDTH_MDU*8],
              i_receive start, i_sender read2jpeg) {


  unsigned int blockNr = 0;
  int block[64];
  
  // from function readblock
  void main(void){
    int i, j;
    int x, y;
    for ( ; ; ) {
      // wait for the start signal
      start.receive();
      // one image come in, read and send each block
      for (blockNr = 0; blockNr < IMG_BLOCKS; blockNr++) {
      
        x = (blockNr % MDU(IMG_WIDTH)) << 3;
        y = (blockNr / MDU(IMG_WIDTH)) << 3;
    
        for (i=0; i<8; i++) {
          for (j=0; j<8; j++) {
            block[i*8+j] = ScanBuffer[y+i][x+j];
          } 
        }
        
        // send the block into a queue
        read2jpeg.send(block, IMG_BLOCK_SIZE*sizeof(int));
      }
    }
  }
};
