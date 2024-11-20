package edu.grinnell.csc207.blockchains;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Blocks to be stored in blockchains.
 *
 * @author Mina Bakrac
 * @author Samuel A. Rebelsky
 */
public class Block {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  int numInChain;
  Transaction tran;
  Hash hPrev;
  long nonce; //(needs a mine method to create. check q&a.)
  Hash hCurr;


  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new block from the specified block number, transaction, and
   * previous hash, mining to choose a nonce that meets the requirements
   * of the validator.
   *
   * @param num
   *   The number of the block.
   * @param transaction
   *   The transaction for the block.
   * @param prevHash
   *   The hash of the previous block.
   * @param check
   *   The validator used to check the block.
   */
  public Block(int num, Transaction transaction, Hash prevHash,
      HashValidator check) {
    this.numInChain = num;
    this.tran = transaction;
    this.hPrev = prevHash;
    this.nonce = mine(check); // mine for nonce here.
  } // Block(int, Transaction, Hash, HashValidator)

  /**
   * Create a new block, computing the hash for the block.
   *
   * @param num
   *   The number of the block.
   * @param transaction
   *   The transaction for the block.
   * @param prevHash
   *   The hash of the previous block.
   * @param nonce
   *   The nonce of the block.
   */
  public Block(int num, Transaction transaction, Hash prevHash, long nonce) {
    //STUB
  } // Block(int, Transaction, Hash, long)

  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * Compute the hash of the block given all the other info already
   * stored in the block.
   */
  public byte[] computeHash() throws NoSuchAlgorithmException{
    MessageDigest md = MessageDigest.getInstance("sha-256");
    // Add number in chain.
    byte[] numInChainbytes = ByteBuffer.allocate(Integer.BYTES).putInt(this.numInChain).array();
    md.update(numInChainbytes);
    // Add data.
    // UNIMPLEMENTED (stuck on this)
    //
    // Add previous hash.
    md.update(hPrev);
    // Add nonce.
    byte[] noncebytes = ByteBuffer.allocate(Long.BYTES).putLong(this.nonce).array();
    byte[] hash = md.digest();
    return hash;
  } // computeHash()

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Get the number of the block.
   *
   * @return the number of the block.
   */
  public int getNum() {
    return this.numInChain;
  } // getNum()

  /**
   * Get the transaction stored in this block.
   *
   * @return the transaction.
   */
  public Transaction getTransaction() {
    return new Transaction("Here", "There", 0); // STUB
  } // getTransaction()

  /**
   * Get the nonce of this block.
   *
   * @return the nonce.
   */
  public long getNonce() {
    return this.nonce;
  } // getNonce()

  /**
   * Get the hash of the previous block.
   *
   * @return the hash of the previous block.
   */
  Hash getPrevHash() {
    return this.hPrev;
  } // getPrevHash

  /**
   * Get the hash of the current block.
   *
   * @return the hash of the current block.
   */
  Hash getHash() {
    return hCurr;
  } // getHash

  /**
   * Get a string representation of the block.
   *
   * @return a string representation of the block.
   */
  public String toString() {
    return "";  // STUB
  } // toString()

  public long mine(HashValidator check) {
    for (long nonce = 0; nonce < Long.MAX_VALUE; nonce++) {
      computeHash();
      if (HashValidator.isValid(this.hCurr) == true) {
        return nonce;
      } // if
    } // for
  } // mine(HashValidator)

} // class Block
