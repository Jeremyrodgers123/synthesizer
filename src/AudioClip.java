

public class AudioClip {
	private static double duration;
	private static int sampleRate;
	public byte[] bytes; 
	int maxVal = (int)Math.pow(2, 15) - 1;
	int minVal = (int)Math.pow(2, 15)* -1;
	public AudioClip (double d, int sr){
		duration = d;
		sampleRate = sr;
		bytes = new byte[sampleRate * 2]; 
	}
	
	public int getSampleRate() {
		return sampleRate;
	}
	
	public double getDuration() {
		return duration;
	}
	
	private int createByteFilter(byte bigByte) {
		//check the first bit of big byte. 
		byte filter =  (byte)0x80;
		byte bigByteCheckVal = (byte)(bigByte & filter);
		
		int first2bytes = 0;
		//if it's a 1, fill in all 1's before it. 
		if(bigByteCheckVal == -128) {
			first2bytes = 0xFFFF0000;
		}else if(bigByteCheckVal == 0){
			first2bytes = 0x00000000;
		}else {
			System.exit(0);
		}
		return first2bytes;
	}
	
	public int getSample(int index) {
		//index refers to the sample index. 
		//two bytes = one sample
		
		byte littleByte = bytes[index * 2];
		byte bigByte = bytes[((index * 2) + 1)];
		int first2Bytes = createByteFilter(bigByte);
		int bigByteInt = (bigByte << 8);
		int lastTwoBytes = ((bigByteInt & 0x0000FF00) | (littleByte& 0x000000FF)) ;
		lastTwoBytes = first2Bytes | lastTwoBytes;
		return lastTwoBytes;
	}
	
	public int setSample(int index, int val) {
		//take int value, throw away first 16 bits;
		
		if(val > maxVal) {
			val = maxVal;
		}
		if(val < minVal) {
			val = minVal;
		}
		
		int bigByteFilter = 0x0000FF00;
		//filter for big bytes
		byte bigByte = (byte) ((bigByteFilter & val) >> 8);
		int littleByteFilter = 0x000000FF;
		byte littleByte = (byte) (littleByteFilter & val);
		//store vals in right place.
		bytes[index * 2] = littleByte;
		bytes[index * 2 + 1] = bigByte;
		int ret = (littleByte & 0x000000FF) | ((bigByte & 0x000000FF) << 8);
		return ret;

	}
}
