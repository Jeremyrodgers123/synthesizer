import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AudioClipTest {

	@Test
	void testGetSample() {
		AudioClip testClip = new AudioClip(1, 44100);
		
		//System.out.println(sample);
		//fail("Not yet implemented");
		for( int i = 0; i < testClip.bytes.length; i++) {
			int byteType = i % 4;
			byte newByte = 0;
			if(byteType == 0) {
				newByte = 0x77;
			}else if(byteType == 1) {
				newByte = (byte)0xbb;
			}else if(byteType == 2) {
				newByte = (byte)0x99;
			}else {
				newByte = (byte)0x00;
			}
			testClip.bytes[i] =newByte;
		}
		
		assertAll(
			()->{
				short sample = (short)testClip.getSample(1);
				
				short checkVal = (short)0x0099;
				System.out.println("Sample: "+ Integer.toHexString(sample));
				System.out.println("checkVal: "+ Integer.toHexString(checkVal));
				assertEquals( checkVal, sample );
				System.out.println("--------------------------");
			},
			()->{
				short sample = (short)testClip.getSample(0);
				
				short checkVal = (short)0xbb77;
				System.out.println("Sample: "+ Integer.toHexString(sample));
				System.out.println("checkVal: "+ Integer.toHexString(checkVal));
				assertEquals( checkVal, sample );
			}
				
		);
		
	}
	
	
	@Test
	void testSetSample() {
		AudioClip testClip = new AudioClip(1, 44100);
		for( int i = 0; i < testClip.bytes.length; i++) {
			int byteType = i % 4;
			byte newByte = 0;
			if(byteType == 0) {
				newByte = 0x77;
			}else if(byteType == 1) {
				newByte = (byte)0xbb;
			}else if(byteType == 2) {
				newByte = (byte)0x99;
			}else {
				newByte = (byte)0x00;
			}
			testClip.bytes[i] =newByte;
		}
		
		int testVal = 0x33BB33BB;
		
		int sample = testClip.setSample(1,testVal);
		assertAll(
			()->{
				assertEquals(0x33BB, sample);
			},
			()->{
				assertEquals(0x33BB, testClip.getSample(1));
			}
		);
		
	}
	
}
