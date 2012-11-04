//
// Huffman 
//
//

import "huffencode";
import "zigzag";
import "i_sender";


behavior Huff(in int in_block[64], i_sender file){
  int zig2huf[64];

  ZigZag zigzag(in_block, zig2huf);
  Huffencode huffencode(zig2huf, file); 


  void main(void) {
    zigzag;
    huffencode;
  }
};
