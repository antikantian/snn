package snn.internal

trait Storable[A] {

  /** Computes the storage requirements (in bytes) */
  def sizeOf: Int

  /** Computes the alignment constraint.  An alignment constraint x is fulfilled by any address divisible by x. */
  def alignment: Int

  /** Read a value from a memory area regarded as an array of values of the same kind.
   * The first argument specifies the start address of the array and the second the index
   * into the array (the first element of the array has index 0). */
  def peekElemOff(addr: Ptr[A], idx: Int): IO[A]

  def pokeElemOff(addr: Ptr[A], idx: Int, x: A): IO[Unit]

  def peekByteOff[B](addr: Ptr[B], offset: Int): IO[A]

  def pokeByteOff[B](addr: Ptr[B], offset: Int, x: A): IO[Unit]

  def peek(addr: Ptr[A]): IO[A]

  def poke(addr: Ptr[A], x: A): IO[Unit]

}