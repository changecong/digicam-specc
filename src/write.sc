/********************************************************
 * File Name: write.sc
 * Created By: Zhicong Chen -- chen.zhico@husky.neu.edu
 * Creation Date: [2012-11-05 19:55]
 * Last Modified: [2012-11-05 22:21]
 * Licence:
 * Description: continuously reads bytes from a queue and
 *              forwards them into an outgoing double-
 *              handshake channel. 
 *********************************************************/

#include <sim.sh>
#include "digicam.sh"

import "i_receiver";
import "i_sender";

behavior WriteBlock(i_receiver jpeg2write, i_sender bytes)
{
  // create a buffer to recieve data by bytes
  unsigned char buffer[1];
  // 
  void main(void) {
    // receive from jpeg & forward to monitor
    while(1) {
      jpeg2write.receive(buffer, sizeof(char));
      bytes.send(buffer, sizeof(char));
    }
  }
};


