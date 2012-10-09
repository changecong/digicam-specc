/**************************************************
 * Author: Zhicong Chen -- 10/06/2012 14:18:15
 * Email: chen.zhico@husky.neu.edu
 * Filename: huff.sc
 * Last modified: 10/06/2012 14:18:15
 * Description:
 * :set ts=4
 *************************************************/

import "zigzag";
import "huffencode";

//#include <c_queue.sh>
import "c_queue";

/* huff
 * implement the sequential composition of zigzag and huffencode
 * child behaviors
 * @para in_block[], out_block[]*/
behavior huff(in int in_block[64], i_sender port)
{
	Zigzag Z(in_block, out_block);
	Huffencode H(out_block, port);

	void main(void) {
		// runs in sequential
		Z.main();
		H.main();
	}	
};
