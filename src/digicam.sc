// Digital Camera Example
//
// Lab 2
// Group Members: 
//   <login name>, <student id>
//
//


#include <stdio.h>
import "jpegencoder";
import "ReadBmp_aux";
import "monitor";

import "c_handshake";
import "c_queue";


behavior Main {

	c_handshake HS;
	c_queue Q
	
	// the simu
	ReadBmp S(ScanBuffer, HS);
	// the encoder
	jpegencoder J(ScanBuffer, HS, Q);
	// the moniter
	FileWrite M(Q);
		
  int main(void) {
	
	S.main();
	J.main();
    M.main()

    return 0;
  }
};
