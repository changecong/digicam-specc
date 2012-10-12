// Digital Camera Example
//
// Lab 2
// Group Members: 
//   Zhicong Chen
//   Weifu Li
//   Charu Kalra

#include "digicam.sh"
#include "file.sh"

import "c_queue";

behavior sub_FileWrite(i_receiver in_port)
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

      in_port.receive(bytes, num);

      if (fwrite(bytes, sizeof(char), num, f) != num) {
        fprintf(stderr, "Error writing output file %s\n", "test.jpg");
        fclose(f);
        exit(1);
      }
    }

    fclose(f);
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
