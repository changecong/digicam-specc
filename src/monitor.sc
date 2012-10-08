/**************************************************
 * Author: Zhicong Chen -- 10/06/2012 18:31:02
 * Email: chen.zhico@husky.neu.edu
 * Filename: monitor.sc
 * Last modified: 10/06/2012 18:31:02
 * Description:
 * :set ts=4
 *************************************************/

//#include <c_queue.sh>
import "c_queue"

behavior FileWrite(i_receiver in_port)
{
	FILE *f = NULL;
	unsigned char *bytes;
	unsigned long num;

	void main(void) {
		if(!f) {
			f=fopen("test.jpg","wb");
		}
		if(!f) {
			fprintf(stderr, "Cannot open output file %s\n", "test.jpg");
		}

		in_port.receive(bytes, num);

		if (fwrite(bytes,sizeof(char),sizeof(bytes),f) != sizeof(bytes)) {
			fprintf(stderr, "Error writing output file %s\n", "test.jpg");
			fclose(f);
			exit(1);
		}

		if (bytes[num-2] == 0xff && bytes[num-1] == 0xd9) {
			fclose(f);
			f = NULL;
			printf ("Encoded JPEG file written successfully!\n");
		}
		
	}
};
