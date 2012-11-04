//
// 
//  stimulus for digicam  (prior ReadBmp_aux.c)
//
//
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sim.sh>
#include "digicam.sh"

import "i_send";

behavior Stimulus(unsigned char ScanBuffer[IMG_HEIGHT_MDU*8][IMG_WIDTH_MDU*8],
                  i_send start){
  typedef short WORD;
  typedef long DWORD;
  typedef char BYTE;

  typedef struct tagBITMAPFILEHEADER {
    WORD bfType;
    DWORD bfSize;
    WORD bfReserved1;
    WORD bfReserved2;
    DWORD bfOffBits;
  } BITMAPFILEHEADER;

  typedef struct tagBITMAPINFOHEADER {
    /* BITMAP core header info -> OS/2 */
    DWORD biSize;
    DWORD biWidth;
    DWORD biHeight;
    WORD biPlanes;
    WORD biBitCount;

    /* BITMAP info -> Windows 3.1 */
    DWORD biCompression;
    DWORD biSizeImage;
    DWORD biXPelsPerMeter;
    DWORD biYPelsPerMeter;
    DWORD biClrUsed;
    DWORD biClrImportant;
  } BITMAPINFOHEADER;

  typedef struct tagRGBTRIPLE {
    BYTE B, G, R;
  } RGBTRIPLE;


  FILE* ifp;
  BITMAPFILEHEADER BmpFileHeader;
  BITMAPINFOHEADER BmpInfoHeader;
  RGBTRIPLE *BmpColors;
  int BmpScanWidth, BmpScanHeight;

  
  int ReadRevWord()
    {
      int c;
      c = fgetc(ifp);
      c |= fgetc(ifp) << 8;
    
      return c;
    }

  int ReadWord()
    {
      int c;
      c = fgetc(ifp) << 8;
      c |= fgetc(ifp);

      return c;
    }

  int ReadByte()
    {
      return fgetc(ifp);
    }

  long ReadRevDWord()
    {
      long c;
      c = fgetc(ifp);
      c |= fgetc(ifp) << 8;
      c |= fgetc(ifp) << 16;
      c |= fgetc(ifp) << 24;
    
      return c;
    }

  long ReadDWord()
    {
      long c;
      c = fgetc(ifp) << 24;
      c |= fgetc(ifp) << 16;
      c |= fgetc(ifp) << 8;
      c |= fgetc(ifp);
    
      return c;
    }
    
  int  IsBmpFile()
    {
      int t = ('B'<<8) | 'M';
      int c;
      c = ReadWord();
      fseek(ifp, -2, 1);
    
      return t == c;
    }

  // read and analyze BMP header  
  void ReadBmpHeader()
    {
      int i, count;

      if (!IsBmpFile()) {
        fprintf(stderr, "This file is not compatible with BMP format.\n");
        exit(1);
      }

      /* BMP file header */
      BmpFileHeader.bfType = ReadWord();
      BmpFileHeader.bfSize = ReadRevDWord();
      BmpFileHeader.bfReserved1 = ReadRevWord();
      BmpFileHeader.bfReserved2 = ReadRevWord();
      BmpFileHeader.bfOffBits = ReadRevDWord();

      /* BMP core info */
      BmpInfoHeader.biSize = ReadRevDWord();
      BmpInfoHeader.biWidth = ReadRevDWord();
      BmpInfoHeader.biHeight = ReadRevDWord();
      BmpInfoHeader.biPlanes = ReadRevWord();
      BmpInfoHeader.biBitCount = ReadRevWord();



      if (BmpInfoHeader.biSize > 12) {
        BmpInfoHeader.biCompression = ReadRevDWord();
        BmpInfoHeader.biSizeImage = ReadRevDWord();
        BmpInfoHeader.biXPelsPerMeter = ReadRevDWord();
        BmpInfoHeader.biYPelsPerMeter = ReadRevDWord();
        BmpInfoHeader.biClrUsed = ReadRevDWord();
        BmpInfoHeader.biClrImportant = ReadRevDWord();

        /* read RGBQUAD */
        count = BmpFileHeader.bfOffBits - ftell(ifp);
        count >>= 2;
 
#ifdef USE_BMPCOLORS
        // disable dynamic allocation of color array
        BmpColors = (RGBTRIPLE*) calloc(sizeof(RGBTRIPLE), count);
#endif

        for (i=0; i<count; i++) {
#ifdef USE_BMPCOLORS
          BmpColors[i].B = ReadByte();
          BmpColors[i].G = ReadByte();
          BmpColors[i].R = ReadByte();
          ReadByte();
#else
          // still do the read bytes to maintain file pointer operation
          ReadByte();
          ReadByte();
          ReadByte();
          ReadByte();
#endif
        }
      }
      else {
        /* read RGBTRIPLE */
        count = BmpFileHeader.bfOffBits - ftell(ifp);
        count /= 3;
      
#ifdef USE_BMPCOLORS
        BmpColors = (RGBTRIPLE*) calloc(sizeof(RGBTRIPLE), count);
#endif
      
        for (i=0; i<count; i++) {
#ifdef USE_BMPCOLORS
          BmpColors[i].B = ReadByte();
          BmpColors[i].G = ReadByte();
          BmpColors[i].R = ReadByte();
#else
          ReadByte();
          ReadByte();
          ReadByte();
#endif
        }
      }

      /* BMP scan line is aligned by LONG boundary */
      if (BmpInfoHeader.biBitCount == 24) {
        BmpScanWidth = ((BmpInfoHeader.biWidth*3 + 3) >> 2) << 2;
        BmpScanHeight = BmpInfoHeader.biHeight;
      }
      else {
        BmpScanWidth = ((BmpInfoHeader.biWidth + 3) >> 2) << 2;
        BmpScanHeight = BmpInfoHeader.biHeight;
      }

      // validate that image dimensions are according to specification
      if (BmpScanWidth != IMG_WIDTH) {
        printf("Image width (%d) not according to specification (%d)\n", 
               BmpScanWidth, IMG_WIDTH);
        exit(1);
      }

      if (BmpScanHeight != IMG_HEIGHT) {
        printf("Image height (%d) not according to specification (%d)\n", 
               BmpScanHeight, IMG_HEIGHT);
        exit(1);
      }
  
    }
    

  // read BMP image from file "ccd.bmp" and store it into ScanBuffer
  // 
  void main(void){
    unsigned int i, r;
    unsigned int fnum = 0;
    unsigned int mduwide, mduhigh;
    unsigned int imagewidth, imageheight;
    // file name
    char fname[20];
    // the first file name
    sprintf(fname, "ccd_%d.bmp", fnum);

    while (access(fname, R_OK)) {
    // while (1) {
      // Open file
      ifp = fopen(fname, "rb");
      if (!ifp) {
        fprintf(stderr, "Cannot open input file %s\n", fname);
        exit(1);
      }

      // Read BMP file header
      ReadBmpHeader();

      // get number of MDUs (8x8 blocks)
      imagewidth  = BmpInfoHeader.biWidth;
      imageheight = BmpInfoHeader.biHeight;

      mduwide = MDU(imagewidth);
      mduhigh = MDU(imageheight);
  
      // Loop over rows
      for (r = 0; r < (unsigned int) BmpInfoHeader.biHeight; r++) {
        // Position file pointer to corresponding row
        fseek (ifp, BmpFileHeader.bfOffBits 
               + (BmpInfoHeader.biHeight - r - 1) * BmpScanWidth, 0);
        
        // Read pixel row, throw error on unexpected end of file, and bitwidth
        if (ferror(ifp) || 
            (fread(ScanBuffer[r], 1, BmpInfoHeader.biWidth, ifp) 
             !=  (unsigned int) BmpInfoHeader.biWidth)){
          fprintf(stderr, "Error reading data from file %s\n", "ccd.bmp");
          fclose (ifp);
          exit(1);
        }

        // fill remaining overhang pixels by copying last pixels 
        for(i = BmpInfoHeader.biWidth; 
            i <  (unsigned int) MDU(BmpInfoHeader.biWidth) * 8; i++) {
          ScanBuffer[r][i] = ScanBuffer[r][BmpInfoHeader.biWidth-1];
        }
      }
    
      fclose (ifp);

      start.send();
      fnum++;
      sprintf(fname, "ccd_%d.bmp", fnum);
      // return the number of 8x8 blocks to be processed
      // no longer needed due to static dimensions
      // simulated time
      waitfor(200 MILLI_SEC);
    }
    
    return;
  }
};
