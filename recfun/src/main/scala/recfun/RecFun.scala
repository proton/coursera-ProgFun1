package recfun

object RecFun extends RecFunInterface {

  def main(args: Array[String]): Unit = {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(s"${pascal(col, row)} ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r)
      1
    else
      pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {

    def balanceCounter(cnt: Int, chars: List[Char]): Boolean =
      if (cnt < 0) false
      else if (chars.isEmpty) cnt == 0
      else if (chars.head == '(') balanceCounter(cnt + 1, chars.tail)
      else if (chars.head == ')') balanceCounter(cnt - 1, chars.tail)
      else balanceCounter(cnt, chars.tail)

    balanceCounter(0, chars)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money < 0 || coins.isEmpty)
      0
    else if (money == 0)
      1
    else
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
  }
}
