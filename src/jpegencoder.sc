/**************************************************
 * Author: Zhicong Chen -- 10/06/2012 17:56:29
 * Email: chen.zhico@husky.neu.edu
 * Filename: jpegencoder.sc
 * Last modified: 10/06/2012 17:56:29
 * Description:
 * :set ts=4
 *************************************************/
#include "digicam.sh"

import "c_handshake";
import "c_queue";
import "read";
import "dct";
import "quantize";
import "huff";

behavior jpegencoder(in unsigned char ScanBuffer[IMG_HEIGHT_MDU*8][IMG_WIDTH_MDU*8],
					i_receive in_port, i_sender out_port )
{
	unsigned int iter;
	int dctin[64];
	int dctout[64];
	int quantizeout[64];
	int zigzagout[64];
	event start;
	
	readblock R(ScanBuffer, dctin);
	dct D(dctin, dctout);
	quantize Q(dctout, quantizeout);
	huff H(zigzagout, out_port);

	void main(void) {
		
		in_port.receive();
		for (iter = 0; iter < IMG_BLOCKS; iter++) {
			R.main();
			D.main();
			Q.main();
			H.main();
		}

	}
};

