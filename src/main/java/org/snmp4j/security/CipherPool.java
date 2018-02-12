/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.security;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

/**
 * The CipherPool class provides service to share and reuse Cipher instances, across
 * different threads. The maximum number of Ciphers in the pool might temporarily
 * exceed the {@link #maxPoolSize} to minimize waiting time.
 *
 * @author Frank Fock
 * @since 2.2.2
 */
public class CipherPool {

  private LinkedList<Cipher> availableCiphers;

  private int maxPoolSize;
  private int currentPoolSize;


  /**
   * Creates a new cipher pool with a pool size of {@link Runtime#availableProcessors()}.
   */
  public CipherPool() {
    this(Runtime.getRuntime().availableProcessors());
  }

  /**
   * Creates a new cipher pool with a given pool size.
   * @param maxPoolSize
   *   the maximum number of ciphers in the pool.
   */
  public CipherPool(int maxPoolSize) {
    this.currentPoolSize = 0;
    if (maxPoolSize < 0) {
      throw new IllegalArgumentException("Pool size must be >= 0");
    }
    this.maxPoolSize = maxPoolSize;
    this.availableCiphers = new LinkedList<Cipher>();
  }

  public int getMaxPoolSize() {
    return maxPoolSize;
  }

  /**
   * Gets a Cipher from the pool. It must be returned to the pool by calling
   * {@link #offerCipher(Cipher)} when one of its {@link Cipher#doFinal()}
   * methods have been called and it is not needed anymore.
   * @return
   *    a Cipher from the pool, or <code>null</code> if the pool currently does not contain any
   *    cipher.
   */
  public synchronized Cipher reuseCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
    Cipher cipher = availableCiphers.poll();
    if (cipher == null) {
      currentPoolSize = 0;
    }
    else {
      currentPoolSize--;
    }
    return cipher;
  }

  /**
   * Offers a Cipher to the pool (thus returns it to the pool).
   * @param cipher
   *    a Cipher instance previously acquired by {@link #reuseCipher()} or created externally.
   */
  public synchronized void offerCipher(Cipher cipher) {
    if (currentPoolSize < maxPoolSize) {
      currentPoolSize++;
      availableCiphers.offer(cipher);
    }
  }
}
