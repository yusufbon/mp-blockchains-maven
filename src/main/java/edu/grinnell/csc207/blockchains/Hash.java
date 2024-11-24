package edu.grinnell.csc207.blockchains;

import java.util.Arrays;

/**
 * Encapsulated hashes.
 *
 * @author Bonsen Yusuf
 * @author Samuel A. Rebelsky
 */
public class Hash {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /** The internal representation of the hash as a byte array. */
  private final byte[] data;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new encapsulated hash.
   *
   * This constructor takes an array of bytes and creates a copy of it to ensure
   * immutability. The internal state of the hash cannot be modified by external clients.
   *
   * @param inputData The data to copy into the hash.
   * @throws IllegalArgumentException if the input data is null.
   */
  public Hash(byte[] inputData) {
    if (inputData == null) {
      throw new IllegalArgumentException("Hash data cannot be null");
    } // end if
    this.data = Arrays.copyOf(inputData, inputData.length);
  } // Hash(byte[])

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Determine how many bytes are in the hash.
   *
   * @return the number of bytes in the hash.
   */
  public int length() {
    return data.length;
  } // length()

  /**
   * Get the ith byte.
   *
   * This method retrieves the byte at the specified index.
   *
   * @param i The index of the byte to get, between 0 (inclusive) and length() (exclusive).
   * @return the ith byte.
   * @throws IndexOutOfBoundsException if the index is out of range.
   */
  public byte get(int i) {
    if (i < 0 || i >= data.length) {
      throw new IndexOutOfBoundsException("Index out of bounds: " + i);
    } // end if
    return data[i];
  } // get()

  /**
   * Get a copy of the bytes in the hash.
   *
   * This method returns a copy of the internal byte array to ensure that the original
   * data cannot be modified externally.
   *
   * @return a copy of the bytes in the hash.
   */
  public byte[] getBytes() {
    return Arrays.copyOf(data, data.length);
  } // getBytes()

  /**
   * Convert to a hex string.
   *
   * This method converts the byte array into a string of uppercase hexadecimal digits,
   * with each byte represented by two characters.
   * @return the hash as a hex string.
   */
  @Override
  public String toString() {
    StringBuilder hexString = new StringBuilder();
    for (byte b: data) {
      hexString.append(String.format("%02X", Byte.toUnsignedInt(b)));
    } // end of for
    return hexString.toString();
  } // toString()

  /**
   * Determine if this is equal to another object.
   *
   * This method checks if the other object is an instance of Hash and compares the
   * underlying byte arrays for equality.
   *
   * @param other The object to compare to.
   * @return true if the two objects are conceptually equal and false otherwise.
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } // end
    if (!(other instanceof Hash)) {
      return false;
    } // end
    Hash otherHash = (Hash) other;
    return Arrays.equals(this.data, otherHash.data);
  } // equals()

  /**
   * Get the hash code of this object.
   *
   * The hash code is derived from the string representation of the hash.
   *
   * @return the hash code.
   */
  @Override
  public int hashCode() {
    return Arrays.hashCode(data);
  } // hashCode()
} // class Hash
