/**************************************************
 * Author: Zhicong Chen -- 10/06/2012 17:38:43
 * Email: chen.zhico@husky.neu.edu
 * Filename: read.sc
 * Last modified: 10/06/2012 17:38:43
 * Description:
 * :set ts=4
 *************************************************/

#include "digicam.sh"

behavior readblock(in unsigned char ScanBuffer[IMG_HEIGHT_MDU*8][IMG_WIDTH_MDU*8], 
					out int block[64])
{
	void main(void) {
		int i, j;
		int x, y;

		static unsigned int blockNr = 0;

		x = (blockNr % MDU(IMG_WIDTH)) << 3;
		y = (blockNr / MDU(IMG_WIDTH)) << 3;

		for (i=0; i<8; i++) {
			for (j=0; j<8; j++) {
				block[i*8+j] = ScanBuffer[y+i][x+j];
			}
		}

	blockNr++;

	}
};
