// common definitions for jpeg encoder part of the digicam
//
//
#ifndef DIGICAM_H
#define DIGICAM_H

// image dimension in pixels
#define IMG_WIDTH   116
#define IMG_HEIGHT   96

// convert pixel to MDU 
#define MDU(pixel) ((pixel+7)>>3)

// image dimension in 8x8 blocks
#define IMG_WIDTH_MDU   (MDU(IMG_WIDTH))
#define IMG_HEIGHT_MDU  (MDU(IMG_HEIGHT))

// total number of blocks in image
#define IMG_BLOCKS (IMG_WIDTH_MDU * IMG_HEIGHT_MDU)

#endif
