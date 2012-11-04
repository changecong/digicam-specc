//
//
//
//
//



#include "digicam.sh"


behavior Quantize(in int in_block[64], out int out_block[64]){

  int LuminanceQuantization[] = {
    16, 11, 10, 16, 24, 40, 51, 61,
    12, 12, 14, 19, 26, 58, 60, 55,
    14, 13, 16, 24, 40, 57, 69, 56,
    14, 17, 22, 29, 51, 87, 80, 62,
    18, 22, 37, 56, 68, 109, 103, 77,
    24, 35, 55, 64, 81, 104, 113, 92,
    49, 64, 78, 87, 103, 121, 120, 101,
    72, 92, 95, 98, 112, 100, 103, 99};



  void main(void){
    int i,  m,  q,  o;

    q = LuminanceQuantization[0];

    for (i=0; i<64; i++) {
      m = in_block[i];
      if (m > 0) {
        o = (m + q/2) / q;
      } else {
        o = (m - q/2) / q;
      }
      if (i < 63) {
        q = LuminanceQuantization[i+1];
      }
      out_block[i] = o;
    }
  }
};
