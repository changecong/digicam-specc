//
// Read behavior 
//

#include "digicam.sh"


behavior Read(in unsigned char ScanBuffer[IMG_HEIGHT_MDU*8][IMG_WIDTH_MDU*8],
              out int block[64]){

  unsigned int blockNr = 0;

  
  // from function readblock
  void main(void){
    int i, j;
    int x, y;
    
    
    x = (blockNr % MDU(IMG_WIDTH)) << 3;
    y = (blockNr / MDU(IMG_WIDTH)) << 3;
    
    for (i=0; i<8; i++) {
      for (j=0; j<8; j++) {
        block[i*8+j] = ScanBuffer[y+i][x+j];
      } 
    } 
    
    blockNr++;
    

  }
};
