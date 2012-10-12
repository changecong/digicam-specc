/**************************************************
 * Author: Zhicong Chen -- 10/09/2012 19:46:18
 * Email: chen.zhico@husky.neu.edu
 * Filename: dct.sc
 * Last modified: 10/09/2012 19:46:18
 * Description:
 *************************************************/

import "zigzag";
import "huffencoder";

import "c_queue";

/*
 * huff -- integrate the zigzag and huffencoder process
 *         they run parallelly as sub-behaviors
 * @para: in_block -- read block in
 *        out_port -- used to write the processed data into
 *                    queue
 */
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
