package snn
package layers

trait Layer[A] {
  def setup: Unit
  def call: A
}