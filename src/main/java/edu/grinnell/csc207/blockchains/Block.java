package edu.grinnell.csc207.blockchains;

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

/**
 * All the variables needed.
 *
 */
  private final int numInChain;
/**
 * All the variables needed.
 *
 */
  private final Transaction tran;
/**
 * All the variables needed.
 *
 */
  private final Hash hPrev;
/**
 * All the variables needed.
 *
 */
  private long nonce; // Updated to non-final to allow mining process.
/**
 * All the variables needed.
 *
 */
  private final Hash hCurr;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new block from the specified block number, transaction, and
   * previous hash, mining to choose a nonce that meets the requirements
   * of the validator.
   *
   * @param num The number of the block.
   * @param transaction The transaction for the block.
   * @param prevHash The hash of the previous block.
   * @param check The validator used to check the block.
   */
  public Block(int num, Transaction transaction, Hash prevHash, HashValidator check) {
    this.numInChain = num;
    this.tran = transaction;
    this.hPrev = prevHash;
    this.nonce = mine(check); // Find a valid nonce.
    this.hCurr = computeHash(); // Compute the current block's hash.
  } // Block(int, Transaction, Hash, HashValidator)

  /**
   * Create a new block, computing the hash for the block.
   *
   * @param num The number of the block.
   * @param transaction The transaction for the block.
   * @param prevHash The hash of the previous block.
   * @param nonce The nonce of the block.
   */
  public Block(int num, Transaction transaction, Hash prevHash, long nonce) {
    this.numInChain = num;
    this.tran = transaction;
    this.hPrev = prevHash;
    this.nonce = nonce;
    this.hCurr = computeHash(); // Compute the current block's hash.
  } // Block(int, Transaction, Hash, long)

  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * Compute the hash of the block given all the other info already stored in the block.
   *
   * @return the computed hash.
   */
  private Hash computeHash() {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");

      // Add number in chain.
      md.update(ByteBuffer.allocate(Integer.BYTES).putInt(numInChain).array());

      // Add transaction details.
      md.update(tran.getSource().getBytes());
      md.update(tran.getTarget().getBytes());
      md.update(ByteBuffer.allocate(Integer.BYTES).putInt(tran.getAmount()).array());

      // Add previous hash.
      md.update(hPrev.getBytes());

      // Add nonce.
      md.update(ByteBuffer.allocate(Long.BYTES).putLong(nonce).array());

      return new Hash(md.digest());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("SHA-256 algorithm not found", e);
    } //
  } // computeHash()

  /**
   * Find a nonce that makes the hash valid.
   *
   * @param check the validator.
   * @return a valid nonce.
   */
  private long mine(HashValidator check) {
    long attemptNonce = 0;
    while (true) {
      nonce = attemptNonce; // Update nonce for mining process.
      Hash candidateHash = computeHash();
      if (check.isValid(candidateHash)) {
        return attemptNonce;
      } // end of if
      attemptNonce++;
    } // end of while loop
  } // mine(HashValidator)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Get the number of the block in the chain.
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
    return this.tran;
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
  public Hash getPrevHash() {
    return this.hPrev;
  } // getPrevHash()

  /**
   * Get the hash of the current block.
   *
   * @return the hash of the current block.
   */
  public Hash getHash() {
    return this.hCurr;
  } // getHash()

  /**
   * Get a string representation of the block.
   *
   * @return a string representation of the block.
   */
  @Override
  public String toString() {
    return String.format("Block %d [Transaction: %s, Nonce: %d, PrevHash: %s, Hash: %s]",
      numInChain, tran.toString(), nonce,
      (hPrev == null ? "null" : hPrev.toString()), hCurr.toString());
  } // toString()

} // class Block
