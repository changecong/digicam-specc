/**************************************************
 * Author: Zhicong Chen -- 10/09/2012 19:25:36
 * Email: chen.zhico@husky.neu.edu
 * Filename: monitor.sc
 * Last modified: 10/09/2012 19:25:36
 * Description:
 *************************************************/

#include "digicam.sh"
#include "file.sh"

//#define SIZE 3000

import "c_queue";

behavior FileWrite(i_receiver in_port)
{
  FILE *f;  // = NULL;
  unsigned char bytes[SIZE];
  unsigned long num;
  unsigned int numl[4];
  // write number of bytes to file  
  void main(void) {
    if(!f) {
       f=fopen("test.jpg","wb");
    }
    if(!f) {
        fprintf(stderr, "Cannot open output file %s\n", "test.jpg");
    }

	while(!(bytes[num-2] == 0xff && bytes[num-1] == 0xd9)) {
	  // read data from queue.
      // get the length of each queue
	  in_port.receive(numl, 4);
      num = (unsigned long)numl[0];

      //bytes = (unsigned char*)malloc(num);

	  in_port.receive(bytes, num);

      if (fwrite(bytes, sizeof(char), num, f) != num) {
        fprintf(stderr, "Error writing output file %s\n", "test.jpg");
        fclose(f);
        exit(1);
      }
	
	  //*bytes = NULL;
    }

    //if (bytes[num-2] == 0xff && bytes[num-1] == 0xd9) {
      fclose(f);
      //f = NULL;
      printf ("Encoded JPEG file written successfully!\n");
    //}
  }
};
