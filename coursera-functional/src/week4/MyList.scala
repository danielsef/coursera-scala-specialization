package week4

trait MyList[+T] { //We make List covariant here if we want to use Nothing as type of Nil2
  def isEmpty: Boolean
  def head: T
  def tail: MyList[T]
  
  //ERROR: covariant type T occurs in contravariant position in type T for value elem
  //violate the variance rule that the input type has to be a supertype of the output type
  //When the input type is Empty, and if your this is a NonEmpty, then new Cons(Empty, NonEmpty) 
  //is incompatible
  //def prependFails(elem: T): MyList[T] = new Cons(elem, this)
  
  def prepend[U >: T](elem: U): MyList[U] = new Cons(elem, this)
}
class Cons[T](val head: T, val tail: MyList[T]) extends MyList[T] {
  def isEmpty = false
  
  override def toString = {
    def foreach(l: MyList[T], str: String): String = {
      if(l.isEmpty) str 
      else foreach(l.tail, str + l.head)
    }
    foreach(this, "List")
  }
}
class Nil[T] extends MyList[T] {
  def isEmpty: Boolean = true
  def head: Nothing = throw new NoSuchElementException("Nil.head")
  def tail: Nothing = throw new NoSuchElementException("Nil.tail")

  override def toString = "List(Nil)"
}
object Nil2 extends MyList[Nothing] { //List is a bottom type (i.e., subtype of every type)
  def isEmpty: Boolean = true
  def head: Nothing = throw new NoSuchElementException("Nil.head")
  def tail: Nothing = throw new NoSuchElementException("Nil.tail")
  override def toString = "List(Nil)"
}

//Companion object
object MyList {
  //List(1,2) = List.apply(1,2)
  def apply[T](x1: T, x2: T): MyList[T] = new Cons(x1, new Cons(x2, new Nil))
  def apply[T](): MyList[T] = new Nil
  def apply[T](x1: T) = new Cons(x1, new Nil)
}

object ListMain {
  def main(args: Array[String]) = {
    println(MyList())
    println(MyList(1))
    println(MyList(1, 2))
  }
}