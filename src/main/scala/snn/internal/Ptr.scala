package snn.internal

trait Ptr[A] {
  /** The constant nullPtr contains a distinguished value of Ptr that is not associated with a valid memory location. */
  def nullPtr: Ptr[A]

  /** The castPtr function casts a pointer from one type to another. */
  def castPtr[B]: Ptr[B]

  /** Advances the given address by the given offset in bytes. */
  def plusPtr[B](offset: Int): Ptr[B]

  /** Given an arbitrary address and an alignment constraint, alignPtr yields the next higher address that
   * fulfills the alignment constraint. An alignment constraint x is fulfilled by any address divisible by x.
   * This operation is idempotent. */
  def alignPtr(offset: Int): Ptr[A]

  /** Computes the offset required to get from the second to the first argument. */
  def minusPtr[B](addr: Ptr[B]): Int
}

trait FuncPtr[A] {
  /** The constant nullFuncPtr contains a distinguished value of FuncPtr that is not associated with a valid memory location. */
  def nullFuncPtr: FuncPtr[A]

  /** Casts a FuncPtr to a FuncPtr of a different type. */
  def castFuncPtr[B]: FuncPtr[B]

  /** Casts a FunPtr to a Ptr.
   *  Note: this is valid only on architectures where data and function pointers range over the same set of addresses,
   *  and should only be used for bindings to external libraries whose interface already relies on this assumption. */
  def castFuncPtrToPtr[B]: Ptr[B]

  /** Casts a Ptr to a FunPtr.
   *  Note: this is valid only on architectures where data and function pointers range over the same set of addresses,
   *  and should only be used for bindings to external libraries whose interface already relies on this assumption. */
  def castPtrToFuncPtr[B](addr: Ptr[A]): FuncPtr[B]

  /** Release the storage associated with the given FuncPtr, which must have been obtained from a wrapper stub.
   * This should be called whenever the return value from a foreign import wrapper function is no longer required;
   * otherwise, the storage it uses will leak. */
  def freeScalaFuncPtr: IO[Unit]
}

trait IntPtr {
  /** Casts a Ptr to an IntPtr. */
  def ptrToIntPtr[A](addr: Ptr[A]): IntPtr

  /** Casts an IntPtr to a Ptr. */
  def intPtrToPtr[A]: Ptr[A]
}

