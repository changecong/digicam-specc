/**************************************************
 * Author: Zhicong Chen -- 10/09/2012 19:46:18
 * Email: chen.zhico@husky.neu.edu
 * Filename: dct.sc
 * Last modified: 10/09/2012 19:46:18
 * Description:
 *************************************************/

#include "digicam.sh"

/*
 * sub_readblock -- split image into several 8x8 blocks, and send them out
 * @para: ScanBuffer -- read the image in
 *        out_block -- send each 8x8 block out
 */
behavior sub_readblock(in unsigned char ScanBuffer[IMG_HEIGHT_MDU*8][IMG_WIDTH_MDU*8],
  	out int out_block[64])
{
  int i, j;
  int x, y;

  //static unsigned int blockNr = 0;
  unsigned int blockNr = 0;

  void main(void) {
    x = (blockNr % MDU(IMG_WIDTH)) << 3;
    y = (blockNr / MDU(IMG_WIDTH)) << 3;
	
    // send a 8x8 square out through out_block
    for (i=0; i<8; i++) {
      for (j=0; j<8; j++) {
        out_block[i*8+j] = ScanBuffer[y+i][x+j];
      } 
    } 
    
    blockNr++;
  }
};

/*
 * readblock -- a 'clean' behavior
 * @para: ScanBuffer, out_block
 */
behavior readblock(in unsigned char ScanBuffer[IMG_HEIGHT_MDU*8][IMG_WIDTH_MDU*8],
  	out int out_block[64])
{
  sub_readblock R(ScanBuffer, out_block);

  void main(void) {
    R.main();
  }
};
