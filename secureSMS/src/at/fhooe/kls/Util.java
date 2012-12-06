package at.fhooe.kls;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder.AudioSource;
import android.view.Gravity;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class Util.
 */
public class Util {
	
	/** The Constant seed. */
	static final String seed = "asdasdasdasdasdqwqwdqawa";

	/**
	 * To hex.
	 *
	 * @param buf the buf
	 * @return the string
	 */
	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	/** The Constant HEX. */
	private final static String HEX = "0123456789ABCDEF";

	/**
	 * Append hex.
	 *
	 * @param sb the sb
	 * @param b the b
	 */
	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}

	/**
	 * Gets the raw key.
	 *
	 * @param seed the seed
	 * @return the raw key
	 * @throws Exception the exception
	 */
	public static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
		kgen.init(256, sr); // 192 and 256 bits may not be available
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}
	
	/**
	 * Show toast.
	 *
	 * @param context the context
	 * @param message the message
	 */
	public static void showToast(Context context,String message){
		Toast toast = Toast.makeText(context, message, 2000);
		toast.setGravity(Gravity.TOP, -30, 50);
		toast.show();
	}
	
	/**
	 * Encrypt.
	 *
	 * @param raw the raw
	 * @param clear the clear
	 * @return the byte[]
	 * @throws Exception the exception
	 */
	public static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	    byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	/**
	 * Decrypt.
	 *
	 * @param raw the raw
	 * @param encrypted the encrypted
	 * @return the byte[]
	 * @throws Exception the exception
	 */
	public static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.DECRYPT_MODE, skeySpec);
	    byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}
	
	/**
	 * To byte.
	 *
	 * @param hexString the hex string
	 * @return the byte[]
	 */
	public static byte[] toByte(String hexString) {
		int len = hexString.length()/2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
		return result;
	}
	
	/**
	 * Generate seed.
	 *
	 * @return the byte[]
	 */
	public static byte[] generateSeed() {

        int recorderBufferSize = AudioRecord.getMinBufferSize(8000,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT) * 2;

        AudioRecord recorder = new AudioRecord(AudioSource.DEFAULT, 8000,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT,
                recorderBufferSize);

       byte[] recordedAudioBuffer = new byte[recorderBufferSize];
        recorder.startRecording();

       int bufferRead = recorder.read(recordedAudioBuffer, 0, recorderBufferSize);

        
            try {
            	Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        

        recorder.stop();
        recorder.release();
        return recordedAudioBuffer;

	}
	
	/**
	 * Gets the key.
	 *
	 * @return the key
	 * @throws Exception the exception
	 */
	public static String getKey() throws Exception{
		return toHex(getRawKey(generateSeed()));
	}
}
