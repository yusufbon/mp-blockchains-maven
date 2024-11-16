package edu.grinnell.csc207.blockchains;

/**
 * Blocks to be stored in blockchains.
 *
 * @author Your Name Here
 * @author Samuel A. Rebelsky
 */
class Block {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new block from the specified block number, transaction, and
   * previous hash, mining to choose a nonce that meets the requirements
   * of the validator.
   */
  Block(int num, Transaction transaction, Hash prevHash, HashValidator check) {
    // STUB
  } // Block(int, Transaction, Hash, HashValidator)

  /**
   * Create a new block, computing the hash for the block.
   */
  Block(int num, Transaction transaction, Hash prevHash, long nonce) {
    // STUB
  } // Block(int, Transaction, Hash, long)

  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * Compute the hash of the block given all the other info already
   * stored in the block.
   */
  void computeHash() {
    // STUB
  } // computeHash()

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Get the number of the block.
   *
   * @return the number of the block.
   */
  int getNum() {
    return 0;   // STUB
  } // getNum()

  /**
   * Get the transaction stored in this block.
   *
   * @return the transaction.
   */
  Transaction getTransaction() {
    return new Transaction("Here", "There", 0); // STUB
  } // getTransaction()

  /**
   * Get the nonce of this block.
   *
   * @return the nonce.
   */
  long getNonce() {
    return 0;   // STUB
  } // getNonce()

  /**
   * Get the hash of the previous block.
   *
   * @return the hash of the previous block.
   */
  Hash getPrevHash() {
    return new Hash(new byte[] { 0 });  // STUB
  } // getPrevHash

  /**
   * Get the hash of the current block.
   *
   * @return the hash of the current block.
   */
  Hash getHash() {
    return new Hash(new byte[] { 0 });  // STUB
  } // getHash

  /**
   * Get a string representation of the block.
   *
   * @return a string representation of the block.
   */
  public String toString() {
    return "";  // STUB
  } // toString()
} // class Block
