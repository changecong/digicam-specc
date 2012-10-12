// Digital Camera Example
//
// Lab 2
// Group Members: 
//   Zhicong Chen
//   Weifu Li
//   Charu Kalra

/*
 * sub_zigzag -- read bytes in a 'z' order
 * @para: in_block -- read block in
 *        out_block -- write block out
 */
behavior sub_zigzag(in int in_block[64], out int out_block[64])
{
  // used for zigzag index.
  int ZigzagIndex[] =
    {0,  1,  5,  6, 14, 15, 27, 28,
     2,  4,  7, 13, 16, 26, 29, 42,
     3,  8, 12, 17, 25, 30, 41, 43,
     9, 11, 18, 24, 31, 40, 44, 53,
    10, 19, 23, 32, 39, 45, 52, 54,
    20, 22, 33, 38, 46, 51, 55, 60,
    21, 34, 37, 47, 50, 56, 59, 61,
    35, 36, 48, 49, 57, 58, 62, 63};

  // useless
  int IZigzagIndex[] =
    {0,  1,  8, 16,  9,  2,  3, 10,
    17, 24, 32, 25, 18, 11,  4,  5,
    12, 19, 26, 33, 40, 48, 41, 34,
    27, 20, 13,  6,  7, 14, 21, 28,
    35, 42, 49, 56, 57, 50, 43, 36,
    29, 22, 15, 23, 30, 37, 44, 51,
    58, 59, 52, 45, 38, 31, 39, 46,
    53, 60, 61, 54, 47, 55, 62, 63};

  int i, z;

  void main(void) {

    for (i = 0; i < 64; i++)
    {
		z = ZigzagIndex[i]; // set the index
		out_block[z] = in_block[i];
    }
  }
};

/*
 * zigzag -- a 'clean' behavior
 * @para: in_block, out_block
 */
behavior zigzag(in int in_block[64], out int out_block[64]) {
  sub_zigzag Z(in_block, out_block);

  void main(void) {
   Z.main();
  }
};
