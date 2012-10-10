/**************************************************
 * Author: Zhicong Chen -- 10/09/2012 19:40:46
 * Email: chen.zhico@husky.neu.edu
 * Filename: read.sc
 * Last modified: 10/09/2012 19:40:46
 * Description:
 *************************************************/

#include "digicam.sh"

behavior readblock(in unsigned char ScanBuffer[IMG_HEIGHT_MDU*8][IMG_WIDTH_MDU*8],
  	out int out_block[64])
{
  int i, j;
  int x, y;

  static unsigned int blockNr = 0;

  void main(void) {
    x = (blockNr % MDU(IMG_WIDTH)) << 3;
    y = (blockNr / MDU(IMG_WIDTH)) << 3;
	
    for (i=0; i<8; i++) {
      for (j=0; j<8; j++) {
        out_block[i*8+j] = ScanBuffer[y+i][x+j];
      } 
    } 
    
    blockNr++;
  }
};
