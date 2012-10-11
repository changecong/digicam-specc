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

behavior sub_FileWrite(i_receiver in_port)
{
  FILE *f;  // = NULL;
  unsigned char bytes[2];
  int flag = 1;
  // write number of bytes to file  
  void main(void) {
    if(!f) {
       f=fopen("test.jpg","wb");
    }
    if(!f) {
        fprintf(stderr, "Cannot open output file %s\n", "test.jpg");
    }

    while(bytes[0] != 0xd9 || flag == 1) {
      flag = 1;
      if (bytes[0] == 0xff)
        flag = 0;
      // read data from queue.
      // get the length of each queue
      in_port.receive(bytes, 1);

      if (fwrite(bytes, sizeof(char), 1, f) != 1) {
        fprintf(stderr, "Error writing output file %s\n", "test.jpg");
        fclose(f);
        exit(1);
      }	  
    }

   
      fclose(f);
      //f = NULL;
      printf ("Encoded JPEG file written successfully!\n");
   }
};

behavior FileWrite(i_receiver in_port) 
{
  sub_FileWrite F(in_port);

  void main(void) {
    F.main();
  }
};
