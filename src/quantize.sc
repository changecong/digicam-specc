/**************************************************
 * Author: Zhicong Chen -- 10/09/2012 19:46:18
 * Email: chen.zhico@husky.neu.edu
 * Filename: dct.sc
 * Last modified: 10/09/2012 19:46:18
 * Description:
 *************************************************/

/*
 * sub_quantize -- contains the full functionality
 * @para: in_block -- read block in
 *        out_block -- write block out
 */
behavior sub_quantize(in int in_block[64], out int out_block[64])
{

  int LuminanceQuantization[] = {
  16, 11, 10, 16, 24, 40, 51, 61,
  12, 12, 14, 19, 26, 58, 60, 55,
  14, 13, 16, 24, 40, 57, 69, 56,
  14, 17, 22, 29, 51, 87, 80, 62,
  18, 22, 37, 56, 68, 109, 103, 77,
  24, 35, 55, 64, 81, 104, 113, 92,
  49, 64, 78, 87, 103, 121, 120, 101,
  72, 92, 95, 98, 112, 100, 103, 99};

  int i,  m,  q,  o;
  
  void main(void) {
    q = LuminanceQuantization[0];

    for (i = 0; i < 64; i++)
    {
      m = in_block[i];
      if (m > 0) {
        o = (m + q/2) / q;
      }
      else {
        o = (m - q/2) / q;
      }
     
      if (i < 63) {
        q = LuminanceQuantization[i+1];
      }
		
      out_block[i] = o;
    }
  }
};

/*
 * quantize -- a 'clean' behavior
 */
behavior quantize(in int in_block[64], out int out_block[64])
{
  sub_quantize Q(in_block, out_block);

  void main(void) {
    Q.main();
  }
};
