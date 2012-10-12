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

/*
 * sub_FileWrite -- read bytes from queue and then write them into
 *                  a file.
 * @para: in_port -- read bytes
 */
behavior sub_FileWrite(i_receiver in_port)
{
  FILE *f;
  unsigned char bytes[1];  // receive 1 bytes each time.
  int flag = 1;  // used to show if the 0xff is the previous one
    
  void main(void) {
    // open the file
    if(!f) {
       f=fopen("test.jpg","wb");
    }
    if(!f) {
        fprintf(stderr, "Cannot open output file %s\n", "test.jpg");
    }

    // read one byte a time
    while(bytes[0] != 0xd9 || flag == 1) {
      flag = 1;
      if (bytes[0] == 0xff)
      flag = 0;
      
      // one byte
      in_port.receive(bytes, 1);

      // write each byte into the file as well as checking the correctness
      if (fwrite(bytes, sizeof(char), 1, f) != 1) {
        fprintf(stderr, "Error writing output file %s\n", "test.jpg");
        fclose(f);
        exit(1);
      }	  
    }

      fclose(f);
      printf ("Encoded JPEG file written successfully!\n");
   }
};

/*
 * FileWrite -- a 'clean' behavior
 */
behavior FileWrite(i_receiver in_port)
{
  sub_FileWrite F(in_port);

  void main(void) {
   F.main();
  }
};
