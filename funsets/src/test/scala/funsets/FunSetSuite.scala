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
      assert(forall(s123, (x: Int) => x == 1))
    }
  }

  @Test def `map tests`: Unit = {
    new TestSets {
      val s246 = map(s123, (x: Int) => x * 2)
      assert(!contains(s246, 1))
      assert(!contains(s246, 2))
      assert(!contains(s246, 3))
      assert(contains(s246, 2))
      assert(contains(s246, 4))
      assert(contains(s246, 6))
    }
  }



  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}
