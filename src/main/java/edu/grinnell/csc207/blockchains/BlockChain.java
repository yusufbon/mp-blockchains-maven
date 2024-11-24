package edu.grinnell.csc207.blockchains;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A full blockchain implemented with a Node-based linked list.
 *
 * @author Slok Rajbhandari
 * @author Samuel A. Rebelsky
 */
public class BlockChain implements Iterable<Transaction> {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The head (first node) of the blockchain.
   */
  private Node head;

  /**
   * The tail (last node) of the blockchain.
   */
  private Node tail;

  /**
   * The size of the blockchain.
   */
  private int size;

  /**
   * The validator used for mining and checking hashes.
   */
  private final HashValidator validator;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new blockchain using a validator to check elements.
   *
   * @param evalidator The validator used to check elements.
   */
  public BlockChain(HashValidator evalidator) {
    this.validator = evalidator;
    // Create the initial block with a deposit.
    Transaction genesisTransaction = new Transaction("", "", 0);
    Block genesisBlock = new Block(0, genesisTransaction, new Hash(new byte[0]), evalidator);
    this.head = this.tail = new Node(genesisBlock);
    this.size = 1;
  } // BlockChain ()

  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * A node class for the linked list structure.
   */
  private static class Node {
    /**
     * Block.
     */
    Block block;
    /**
     * Node next.
     */
    Node next;

    /**
     * Node (Block block).
     * @param nblock
     */
    Node(Block nblock) {
      this.block = nblock;
    } // Node
  } // Node

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Mine for a new valid block for the end of the chain, returning that block.
   *
   * @param transaction The transaction that goes in the block.
   * @return a new block with correct number, hashes, and such.
   */
  public Block mine(Transaction transaction) {
    return new Block(size, transaction, tail.block.getHash(), validator);
  } // mine ()

  /**
   * Get the number of blocks currently in the chain.
   *
   * @return the number of blocks in the chain, including the initial block.
   */
  public int getSize() {
    return size;
  } // getSize()

  /**
   * Add a block to the end of the chain.
   *
   * @param blk The block to add to the end of the chain.
   * @throws IllegalArgumentException if the block is invalid.
   */
  public void append(Block blk) {
    if (!blk.getPrevHash().equals(tail.block.getHash())) {
      throw new IllegalArgumentException("Invalid previous hash in block.");
    } // if loop
    if (!validator.isValid(blk.getHash())) {
      throw new IllegalArgumentException("Invalid hash for block.");
    } // if loop
    Node newNode = new Node(blk);
    tail.next = newNode;
    tail = newNode;
    size++;
  } // append()

  /**
   * Attempt to remove the last block from the chain.
   *
   * @return false if the chain has only one block or true otherwise.
   */
  public boolean removeLast() {
    if (head == tail) {
      return false; // Cannot remove the genesis block.
    } // end of if
    Node current = head;
    while (current.next != tail) {
      current = current.next;
    } // while
    current.next = null;
    tail = current;
    size--;
    return true;
  } // removeLast()

  /**
   * Get the hash of the last block in the chain.
   *
   * @return the hash of the last block in the chain.
   */
  public Hash getHash() {
    return tail.block.getHash();
  } // getHash()

  /**
   * Determine if the blockchain is correct.
   *
   * @return true if the blockchain is valid; false otherwise.
   */
  public boolean isCorrect() {
    Node current = head;
    while (current.next != null) {
      Block currentBlock = current.block;
      Block nextBlock = current.next.block;
      if (!nextBlock.getPrevHash().equals(currentBlock.getHash())) {
        return false;
      } // end of if
      if (!validator.isValid(currentBlock.getHash())) {
        return false;
      } // end of if
      current = current.next;
    } // end of while
    return true;
  } // isCorrect()

  /**
   * Check if the blockchain is correct. Throws an exception if not.
   *
   * @throws Exception if the blockchain is invalid.
   */
  public void check() throws Exception {
    if (!isCorrect()) {
      throw new Exception("Blockchain is invalid.");
    } // end if
  } // check()

  /**
   * Return an iterator of all the people who participated in the system.
   *
   * @return an iterator of all the people in the system.
   */
  public Iterator<String> users() {
    return new Iterator<>() {
      Node current = head;

      @Override
      public boolean hasNext() {
        return current != null;
      } // hasNext()

      @Override
      public String next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        } // end of if
        Transaction t = current.block.getTransaction();
        current = current.next;
        return t.getTarget();
      } // next()
    };
  } // end of Iterator

  /**
   * Find one user's balance.
   *
   * @param user The user whose balance we want to find.
   * @return that user's balance (or 0, if the user is not in the system).
   */
  public int balance(String user) {
    int balance = 0;
    Node current = head;
    while (current != null) {
      Transaction t = current.block.getTransaction();
      if (t.getTarget().equals(user)) {
        balance += t.getAmount();
      } else if (t.getSource().equals(user)) {
        balance -= t.getAmount();
      } // end of if
      current = current.next;
    } // end of while
    return balance;
  } // balance()

  /**
   * Get an iterator for all the blocks in the chain.
   *
   * @return an iterator for all the blocks in the chain.
   */
  public Iterator<Block> blocks() {
    return new Iterator<>() {
      Node current = head;

      @Override
      public boolean hasNext() {
        return current != null;
      } // hasNext()

      @Override
      public Block next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        } // end of if
        Block blk = current.block;
        current = current.next;
        return blk;
      } // next()
    };
  } // end

  /**
   * Get an iterator for all the transactions in the chain.
   *
   * @return an iterator for all the transactions in the chain.
   */
  public Iterator<Transaction> iterator() {
    return new Iterator<>() {
      Node current = head;

      @Override
      public boolean hasNext() {
        return current != null;
      } // hasNext()

      @Override
      public Transaction next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        } // end of ig
        Transaction t = current.block.getTransaction();
        current = current.next;
        return t;
      } //
    };
  } //
} // class BlockChain
