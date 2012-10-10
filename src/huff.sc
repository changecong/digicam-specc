/**************************************************
 * Author: Zhicong Chen -- 10/09/2012 20:13:44
 * Email: chen.zhico@husky.neu.edu
 * Filename: huff.sc
 * Last modified: 10/09/2012 20:13:44
 * Description:
 *************************************************/

import "zigzag";
import "huffencoder";

import "c_queue";

behavior huff(in int in_block[64], i_sender out_port)
{
  int zigzagout[64];

  zigzag Z(in_block, zigzagout);
  huffencoder H(zigzagout, out_port);

  void main(void) {
    Z.main();
    H.main();
  }
};
