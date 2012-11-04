//
// Monitor
//   -- read bytes from queue and write into file

#include <stdio.h>
#include <stdlib.h>
#include <sim.sh>
#include <unistd.h>
#include "digicam.sh"

import "i_receiver";


behavior Monitor(i_receiver bytes) {
  // initialize file pointer to NULL, changed from 
  // NULL to 0 as init value to avoid compiler limitation
  FILE *f = 0;
  unsigned char buf[2];
  unsigned int nBytes = 0;
  char fname[20], bmp[20];
  unsigned int fnum = 0;

  void main(void) {

    buf[0] = 0;
    buf[1] = 1;
 
    sprintf(bmp, "ccd_%d.bmp", fnum);

    while(access(bmp, R_OK)) {  // check corresponding bmp file
      // name
      sprintf(fname, "test_%d.jpg", fnum);  

      if(!f) {
        f=fopen(fname,"wb");
      }
      if(!f) {
        fprintf(stderr, "Cannot open output file %s\n", fname);
      }
    
  
      do{
        nBytes++;

        // receive a single byte (note write it in alternating 
        // location to detect EOF later
        bytes.receive(&buf[nBytes & 1], sizeof(char));

        // write single byte into file 
        if (fwrite(&buf[nBytes & 1], sizeof(char),1,f) != 1) {
          fprintf(stderr, "Error writing output file %s\n", fname);
          fclose(f);
          exit(1);
        }
      
      
        // repeat until seing the EOF marker in last two byts 
      }while ( !((buf[nBytes     & 1] == 0xd9 ) &&
                 (buf[(nBytes+1) & 1] == 0xff ))); 

      fclose(f);
      f = 0;
        
      printf ("Encoded JPEG file written successfully!\n");
    
      fnum++;
      sprintf(bmp, "ccd_%d.bmp", fnum);
    }

    waitfor(200 MILLI_SEC);
  }  
};
