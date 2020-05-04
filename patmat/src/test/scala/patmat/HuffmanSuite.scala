package patmat

import org.junit._
import org.junit.Assert.assertEquals

class HuffmanSuite {
  import Huffman._

  trait TestTrees {
    val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
    val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
  }


  @Test def `weight of a larger tree (10pts)`: Unit =
    new TestTrees {
      assertEquals(5, weight(t1))
    }


  @Test def `chars of a larger tree (10pts)`: Unit =
    new TestTrees {
      assertEquals(List('a','b','d'), chars(t2))
    }

  @Test def `string2chars hello world`: Unit =
    assertEquals(List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'), string2Chars("hello, world"))


  @Test def `make ordered leaf list for some frequency table (15pts)`: Unit =
    assertEquals(List(Leaf('e',1), Leaf('t',2), Leaf('x',3)), makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))))


  @Test def `combine of some leaf list (15pts)`: Unit = {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assertEquals(List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)), combine(leaflist))
  }


  @Test def `decode and encode a very short text should be identity (10pts)`: Unit =
    new TestTrees {
      assertEquals("ab".toList, decode(t1, encode(t1)("ab".toList)))
    }

//  https://github.com/ncolomer/coursera/blob/master/scala/week4-patmat/src/test/scala/patmat/HuffmanSuite.scala

  @Test def `weight of a larger tree`: Unit = {
    new TestTrees {
      assertEquals(weight(t1), 5)
    }
  }

  @Test def `chars of a larger tree`: Unit = {
    new TestTrees {
      assertEquals(chars(t2), List('a', 'b', 'd'))
    }
  }

  @Test def `string2chars(\"hello, world\")`: Unit = {
    assertEquals(string2Chars("hello, world"), List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  @Test def `makeOrderedLeafList for some frequency table`: Unit = {
    assertEquals(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))), List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 3)))
  }

  @Test def `combine of some leaf list`: Unit = {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assertEquals(combine(leaflist), List(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4)))
  }

  @Test def `combine of singleton leaf list`: Unit = {
    val leaflist = List(Leaf('e', 1))
    assertEquals(combine(leaflist), leaflist)
  }

  @Test def `check secret`: Unit = {
    val expected = List('h', 'u', 'f', 'f', 'm', 'a', 'n', 'e', 's', 't', 'c', 'o', 'o', 'l')
    val actual = decodedSecret
    assertEquals(expected, actual)
  }

  @Test def `create code tree 1`: Unit = {
    val codeTree = createCodeTree(string2Chars("ffrfl"))
    val expected = makeCodeTree(makeCodeTree(Leaf('l', 1), Leaf('r', 1)), Leaf('f', 3))
    assertEquals(expected, codeTree)
  }

  @Test def `create code tree 2`: Unit = {
    val codeTree = createCodeTree(string2Chars("eneneoue"))
    val expected = makeCodeTree(makeCodeTree(makeCodeTree(Leaf('o', 1), Leaf('u', 1)), Leaf('n', 2)), Leaf('e', 4))
    assertEquals(expected, codeTree)
  }

  @Test def `encode and decode a very short text, should be identity`: Unit = {
    new TestTrees {
      val input = "ab".toList
      val encoded = encode(t1)(input)
      val decoded = decode(t1, encoded)
      assertEquals(decoded, input)
    }
  }

  @Test def `encode and decode a short text, should be identity`: Unit = {
    new TestTrees {
      val input = "dba".toList
      val encoded = encode(t2)(input)
      val decoded = decode(t2, encoded)
      assertEquals(decoded, input)
    }
  }

  @Test def `quickEncode and decode a very short text, should be identity`: Unit = {
    new TestTrees {
      val input = "ab".toList
      val encoded = quickEncode(t1)(input)
      val decoded = decode(t1, encoded)
      assertEquals(decoded, input)
    }
  }

  @Test def `quickEncode and decode a short text, should be identity`: Unit = {
    new TestTrees {
      val input = "dba".toList
      val encoded = quickEncode(t2)(input)
      val decoded = decode(t2, encoded)
      assertEquals(decoded, input)
    }
  }

  @Test def `encode and decode using wikipedia sample`: Unit = {
    // Given
    val input = "this is an example of a huffman tree".toList
    val codeTree = createCodeTree("this is an example of a huffman tree".toList)
    // When
    val encoded = encode(codeTree)(input)
    val decoded = decode(codeTree, encoded)
    // Then
    assertEquals(decoded, input)
  }

  @Test def `encode and decode using wikipedia sample and another phrase`: Unit = {
    // Given
    val input = "this is an other phrase sample".toList
    val codeTree = createCodeTree("this is an example of a huffman tree".toList)
    // When
    val encoded = encode(codeTree)(input)
    val decoded = decode(codeTree, encoded)
    // Then
    assertEquals(decoded, input)
  }

  @Test def `quickEncode and decode using wikipedia sample`: Unit = {
    // Given
    val input = "this is an example of a huffman tree".toList
    val codeTree = createCodeTree("this is an example of a huffman tree".toList)
    // When
    val encoded = quickEncode(codeTree)(input)
    val decoded = decode(codeTree, encoded)
    // Then
    assertEquals(decoded, input)
  }

  @Test def `quickEncode and decode using wikipedia sample and another phrase`: Unit = {
    // Given
    val input = "this is an other phrase sample".toList
    val codeTree = createCodeTree("this is an example of a huffman tree".toList)
    // When
    val encoded = quickEncode(codeTree)(input)
    val decoded = decode(codeTree, encoded)
    // Then
    assertEquals(decoded, input)
  }


  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}
