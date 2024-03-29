package io.iyyel.game.of.life.logic

import com.raquo.airstream.ownership.ManualOwner
import com.raquo.airstream.state.{StrictSignal, Var}

final class State[A](val initial: A):
  private val stream: Var[A] = Var(initial)
  private val signal: StrictSignal[A] = stream.signal
  private val owner: ManualOwner = ManualOwner()

  def now(): A =
    signal.now()

  def update(mod: A => A): Unit =
    stream.update(mod)

  def set(value: A): Unit =
    stream.set(value)

  def observe(onNext: A => Unit): Unit =
    signal.foreach(onNext)(owner)

  def observeAfter(onNext: A => Unit): Unit =
    signal.changes.foreach(onNext)(owner)
