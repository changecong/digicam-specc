/**************************************************
 * Author: Zhicong Chen -- 10/09/2012 19:25:36
 * Email: chen.zhico@husky.neu.edu
 * Filename: monitor.sc
 * Last modified: 10/09/2012 19:25:36
 * Description:
 *************************************************/

#include "digicam.sh"
#include "file.sh"

import "c_queue";

behavior FileWrite(i_receiver in_port)
{
  FILE *f = NULL;
  unsigned char *bytes;

  // write number of bytes to file  
  void main(void) {
    if(!f) {
       f=fopen("test.jpg","wb");
    }
    if(!f) {
        fprintf(stderr, "Cannot open output file %s\n", "test.jpg");
    }

	// read data from queue.
	in_port.receive(bytes, 2000);

    if (fwrite(bytes, sizeof(char), sizeof(bytes), f) != sizeof(bytes)) {
        fprintf(stderr, "Error writing output file %s\n", "test.jpg");
        fclose(f);
        exit(1);
    }

    if (bytes[num-2] == 0xff && bytes[num-1] == 0xd9) {
      fclose(f);
      f = NULL;
      printf ("Encoded JPEG file written successfully!\n");
    }
  }
};
