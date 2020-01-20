package me.coley.recaf.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Faster I/O utils
 *
 * @author xxDark
 */
public final class IOUtil {
	/**
	 * Indicates that we can read as many bytes
	 * as we want.
	 */
	public static final int ANY = Integer.MIN_VALUE;

	private IOUtil() { }

	/**
	 * Transfers data from input to output stream.
	 *
	 * @param in     an input stream
	 * @param out    an output stream
	 * @param buffer data buffer
	 * @param max    maximum amount of bytes to transfer
	 * @return amount of bytes read
	 * @throws IOException if any I/O error occurs
	 */
	public static int transfer(InputStream in, OutputStream out, byte[] buffer, int max) throws IOException {
		int transferred = 0;
		int r;
		while ((max == ANY || max > 0) && (r = in.read(buffer, 0, buffer.length)) != -1) {
			transferred += r;
			out.write(buffer, 0, r);
			if (max != ANY) {
				max -= r;
			}
		}
		return transferred;
	}

	/**
	 * Transfers data from input to output stream.
	 * No limits.
	 *
	 * @param in     an input stream
	 * @param out    an output stream
	 * @param buffer data buffer
	 * @return amount of bytes read
	 * @throws IOException if any I/O error occurs
	 */
	public static int transfer(InputStream in, OutputStream out, byte[] buffer) throws IOException {
		return transfer(in, out, buffer, ANY);
	}

	/**
	 * Reads data from input stream to byte array.
	 *
	 * @param in     an input stream
	 * @param out    an output stream
	 * @param buffer data buffer
	 * @param max    maximum amount of bytes to transfer
	 * @return array of bytes
	 * @throws IOException if any I/O error occurs
	 */
	public static byte[] toByteArray(InputStream in, ByteArrayOutputStream out, byte[] buffer, int max)
			throws IOException {
		transfer(in, out, buffer, max);
		return out.toByteArray();
	}

	/**
	 * Reads data from input stream to byte array.
	 * No limits.
	 *
	 * @param in     an input stream
	 * @param out    an output stream
	 * @param buffer data buffer
	 * @return array of bytes
	 * @throws IOException if any I/O error occurs
	 */
	public static byte[] toByteArray(InputStream in, ByteArrayOutputStream out, byte[] buffer) throws IOException {
		return toByteArray(in, out, buffer, ANY);
	}

	/**
	 * Closes resource quietly.
	 *
	 * @param c resource to close
	 */
	public static void close(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException ignored) {
				// No-op
			}
		}
	}
}
