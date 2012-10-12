/**************************************************
 * Author: Zhicong Chen -- 10/09/2012 19:46:18	  	
 * Email: chen.zhico@husky.neu.edu
 * Filename: dct.sc
 * Last modified: 10/09/2012 19:46:18
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
  FILE *f;  // = NULL;
  // bytes buffer
  unsigned char bytes[SIZE];
  unsigned long num;
  // used to store the first 4 typeless bytes
  unsigned int numl[0];

  // receive and write number of bytes to file  
  void main(void) {
    // open the file
    if(!f) {
       f=fopen("test.jpg","wb");
    }
    if(!f) {
        fprintf(stderr, "Cannot open output file %s\n", "test.jpg");
    }

    // read block by block
    while(!(bytes[num-2] == 0xff && bytes[num-1] == 0xd9)) {
      
	  // get the length of each queue
      in_port.receive(numl, 4);
      num = (unsigned long)numl[0];

      // read bytes from queue
      in_port.receive(bytes, num);

      // write to the file and check the correctness
      if (fwrite(bytes, sizeof(char), num, f) != num) {
        fprintf(stderr, "Error writing output file %s\n", "test.jpg");
        fclose(f);
        exit(1);
      }
    }

    // close the file
    fclose(f);
    // f = NULL; it doesn't work with scc
    printf ("Encoded JPEG file written successfully!\n");
  }
};  // sub_FileWrite end

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
