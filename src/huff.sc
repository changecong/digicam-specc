// Digital Camera Example
//
// Lab 2
// Group Members: 
//   Zhicong Chen
//   Weifu Li
//   Charu Kalra

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
