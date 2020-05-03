package funsets

import org.junit._

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite {

  import FunSets._

  @Test def `contains is implemented`: Unit = {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s12 = listToSet(List(1, 2))
    val s23 = listToSet(List(2, 3))
    val s123 = listToSet(List(1, 2, 3))
  }

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remvoe the
   * @Ignore annotation.
   */
  @Ignore("not ready yet") @Test def `singleton set one contains one`: Unit = {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")

      assert(contains(s12, 1), "Set 1, 2")
      assert(!contains(s23, 1), "Set 2, 3")
      assert(contains(s123, 1), "Set 1, 2, 3")
    }
  }

  @Test def `union contains all elements of each set`: Unit = {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")

      val s123_new = union(s12, s23)
      assert(contains(s123_new, 1))
      assert(contains(s123_new, 2))
      assert(contains(s123_new, 3))
      assert(!contains(s123_new, 4))
    }
  }

  @Test def `intersect tests`: Unit = {
    new TestSets {
      val s_int_1_2 = intersect(s1, s2)
      assert(!contains(s_int_1_2, 1))
      assert(!contains(s_int_1_2, 2))

      val s_int_12_23 = intersect(s12, s23)
      assert(!contains(s_int_12_23, 1))
      assert(contains(s_int_12_23, 2))
      assert(!contains(s_int_12_23, 3))
    }
  }

  @Test def `diff tests`: Unit = {
    new TestSets {
      val s_diff_12_23 = diff(s12, s23)
      assert(contains(s_diff_12_23, 1))
      assert(!contains(s_diff_12_23, 2))
      assert(!contains(s_diff_12_23, 3))
    }
  }

  @Test def `filter tests`: Unit = {
    new TestSets {
      val s_int_1_2 = filter(s1, s2)
      assert(!contains(s_int_1_2, 1))
      assert(!contains(s_int_1_2, 2))

      val s_int_12_23 = filter(s12, s23)
      assert(!contains(s_int_12_23, 1))
      assert(contains(s_int_12_23, 2))
      assert(!contains(s_int_12_23, 3))
    }
  }

  @Test def `forall tests`: Unit = {
    new TestSets {
      assert(forall(s1, (x: Int) => x == 1))
      assert(!forall(s2, (x: Int) => x == 1))
      assert(!forall(s12, (x: Int) => x == 1))
      assert(forall(s12, (x: Int) => x == 1 || x == 2))
    }
  }

  @Test def `map tests`: Unit = {
    new TestSets {
      val s246 = map(s123, x => x * 2)
      assert(!contains(s246, 1))
      assert(contains(s246, 2))
      assert(!contains(s246, 3))
      assert(contains(s246, 2))
      assert(contains(s246, 4))
      assert(contains(s246, 6))
    }
  }

//  tests from https://gist.github.com/santiagovazquez/c59fed8ad62df97189a1

  @Test def `filter of {1,3,4,5,7,1000} for _ < 5`: Unit = {
    val mySet = (x: Int) => List(1,3,4,5,7,1000).contains(x)

    val myTestSet = filter(mySet, (x) => x < 5)

    assert(contains(myTestSet, 1), "contains 1")
    assert(contains(myTestSet, 3), "contains 3")
    assert(contains(myTestSet, 4), "contains 4")
    assert(!contains(myTestSet, 5), "contains 5")
    assert(!contains(myTestSet, 7), "contains 7")
    assert(!contains(myTestSet, 1000), "contains 1000")
  }

  @Test def `forall: {1,2,3,4}`: Unit = {
    val mySet = (x: Int) => List(1,2,3,4).contains(x)

    assert(forall(mySet, (x) => x < 5), "All elements in the set are strictly less than 5.")
  }

  @Test def `forall: {-1000,0}`: Unit = {
    val mySet = (x: Int) => List(-1000,0).contains(x)

    assert(forall(mySet, (x) => x < 1000), "All elements in the set are strictly less than 1000.")
  }

  @Test def `forall & filter: even.`: Unit = {
    val evenNumbersSet = (x: Int) => x % 2 == 0

    assert(forall(evenNumbersSet, (x) => x % 2 ==0), "The set of all even numbers should contain only even numbers")
  }


  @Test def `exist: {1,2,3,4}`: Unit = {
    val mySet = (x: Int) => List(1,2,3,4).contains(x)

    assert(exists(mySet, (x) => x == 2), "the set contains at least a two")
  }

  @Test def `map: {1,3,4,5,7,1000}`: Unit = {
    val mySet = (x:Int) => List(1,3,4,5,7,1000).contains(x)
    val subOneSet = map(mySet, (x) => x - 1)
    val resultSet = (x: Int) => List(0,2,3,4,6,999).contains(x)

    assert(forall(subOneSet, (x: Int) => List(0,2,3,4,6,999).contains(x)), "{[2,4,5,6,8]} did not equal {[0,2,3,4,6,999]}")
    assert(contains(subOneSet, 999), "should contains 999")

  }

  @Test def `forall & map: doubling numbers`: Unit = {
    val evenNumbersSet = (x: Int) => x % 2 == 0

    assert(forall(map(evenNumbersSet, (x) => x * 2), evenNumbersSet), "The set obtained by doubling all numbers should contain only even numbers.")
  }



  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}
