/**************************************************
 * Author: Zhicong Chen -- 10/06/2012 13:03:13
 * Email: chen.zhico@husky.neu.edu
 * Filename: zigzag.sc
 * Last modified: 10/06/2012 13:03:13
 * Description:
 * :set ts=4
 *************************************************/

/* zigzag 
 * we don't need a channel here, 'cos zigzag is only a sub behavior
 * of huff.sc 
 * @para: in_block[64], out_block[64] */
behavior zigzag(in int in_block[64], out int out_block[64]) {
	
	int ZigzagIndex[] =
		{0,  1,  5,  6, 14, 15, 27, 28,
		2,  4,  7, 13, 16, 26, 29, 42,
		3,  8, 12, 17, 25, 30, 41, 43,
		9, 11, 18, 24, 31, 40, 44, 53,
		10, 19, 23, 32, 39, 45, 52, 54,
		20, 22, 33, 38, 46, 51, 55, 60,
		21, 34, 37, 47, 50, 56, 59, 61,
		35, 36, 48, 49, 57, 58, 62, 63};
	int IZigzagIndex[] =
		{0,  1,  8, 16,  9,  2,  3, 10,
		17, 24, 32, 25, 18, 11,  4,  5,
		12, 19, 26, 33, 40, 48, 41, 34,
 		27, 20, 13,  6,  7, 14, 21, 28,
		35, 42, 49, 56, 57, 50, 43, 36,
		29, 22, 15, 23, 30, 37, 44, 51,
		58, 59, 52, 45, 38, 31, 39, 46,
		53, 60, 61, 54, 47, 55, 62, 63};
	
	void main(void) {
		int i, z;

		for (i = 0; i < 64; i++) {
			z = ZigzagIndex[i];
			out_block[z] = in_block[i];
		}
	}
};


// :set ts=4
