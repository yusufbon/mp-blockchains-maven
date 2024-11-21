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

  // simple hash validator
  HashValidator simpleValidator =
    (hash) -> (hash.length() >= 1) && (hash.get(0) == 0);

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
     * Create a new encapsulated hash.
     *
     * This constructor takes an array of bytes and creates a copy of it to ensure
     * immutability. The internal state of the hash cannot be modified by external clients.
     *
     * @param data The data to copy into the hash.
     * @throws IllegalArgumentException if the input data is null.
     */
    public Hash(byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException("Hash data cannot be null");
        }
        this.data = Arrays.copyOf(data, data.length);
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
      }
      return data[i];
  } //get()


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
  } //getBytes()


  /**
   * Convert to a hex string.
   *
   * This method converts the byte array into a string of uppercase hexadecimal digits,
   * with each byte represented by two characters.
   * @return the hash as a hex string.
   */
  @Override
    public String toString() {
        StringBuilder hexString = new StringBuilder(); //initialized as empty; hexString is a variable that will hold the hex representation of data array as it's built.
        for (byte b : data) { //iterate through each byte in the data array
            hexString.append(String.format("%02X", Byte.toUnsignedInt(b))); //create formatted string, 2 characters wide and uppercase
        }
        return hexString.toString();
    }// toString()

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
        if (this == other) { //do the objects point to the same memory address?
            return true;
        }
        if (!(other instanceof Hash)) { //check if it is not a hash object
            return false;
        }
        Hash otherHash = (Hash) other; //makes the "other" a hash if it passes the checks, allows for comparison
        return Arrays.equals(this.data, otherHash.data); //compare the data of the arrays
    } //equals()


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
    } //hashCode()
} // class Hash
