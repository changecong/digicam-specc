// common definitions for jpeg encoder part of the digicam
//
//
#ifndef DIGICAM_H
#define DIGICAM_H

// image dimension in pixels
#define IMG_WIDTH   116
#define IMG_HEIGHT   96

// image block size
#define IMG_BLOCK_SIZE 64

// convert pixel to MDU 
#define MDU(pixel) ((pixel+7)>>3)

// image dimension in 8x8 blocks
#define IMG_WIDTH_MDU   (MDU(IMG_WIDTH))
#define IMG_HEIGHT_MDU  (MDU(IMG_HEIGHT))

// total number of blocks in image
#define IMG_BLOCKS (IMG_WIDTH_MDU * IMG_HEIGHT_MDU)

// print a string with a time stamp
#define TPRINT(a) {sim_time_string buff; printf("Time =%5s: %s", time2str(buff, now()), a);};

#endif

