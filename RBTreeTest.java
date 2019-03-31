package net.yeah.rbt;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="zipeng.dzp@alibaba-inc.com">daosu</a>
 * @version created on 2019/1/9 17:37.
 */
public class RBTreeTest {
  @Rule
  public OutputCapture capture = new OutputCapture();

  /**
   * 插入的是根节点,直接标识为黑色即可
   */
  @Test
  public void insertCase0() {
    RBTree tree = new RBTree();

    capture.reset();
    tree.insert(1);
    tree.inOrder();
    assertTrue(capture.toString().contains("[30m1\u001B[0m"));
  }

  /**
   * 插入的节点其parent是黑色,插入的是左孩子
   */
  @Test
  public void insertCase1L() {
    RBTree tree = new RBTree();
    tree.insert(2);
    tree.insert(1);

    capture.reset();
    tree.preOrder();
    assertTrue(capture.toString().contains("[30m2\u001B[0m,\u001B[31m1\u001B[0m"));

    capture.reset();
    tree.inOrder();
    assertTrue(capture.toString().contains("[31m1\u001B[0m,\u001B[30m2\u001B[0m"));

    capture.reset();
    tree.postOrder();
    assertTrue(capture.toString().contains("[31m1\u001B[0m,\u001B[30m2\u001B[0m"));
  }

  /**
   * 插入的节点其parent是黑色,插入的是右孩子
   */
  @Test
  public void insertCase1R() {
    RBTree tree = new RBTree();
    tree.insert(2);
    tree.insert(3);

    capture.reset();
    tree.preOrder();
    assertTrue(capture.toString().contains("[30m2\u001B[0m,\u001B[31m3\u001B[0m"));

    capture.reset();
    tree.inOrder();
    assertTrue(capture.toString().contains("[30m2\u001B[0m,\u001B[31m3\u001B[0m"));

    capture.reset();
    tree.postOrder();
    assertTrue(capture.toString().contains("[31m3\u001B[0m,\u001B[30m2\u001B[0m"));
  }

  /**
   * 插入的节点其parent是红色,uncle也是红色,插入的是左孩子
   */
  @Test
  public void insertCase2L() {
    RBTree tree = new RBTree();
    tree.insert(15);
    tree.insert(5);
    tree.insert(25);
    tree.insert(1);

    capture.reset();
    tree.preOrder();
    assertTrue(capture.toString().contains("[30m15\u001B[0m,\u001B[30m5\u001B[0m,\u001B[31m1\u001B[0m,\u001B[30m25\u001B[0m"));

    capture.reset();
    tree.inOrder();
    assertTrue(capture.toString().contains("[31m1\u001B[0m,\u001B[30m5\u001B[0m,\u001B[30m15\u001B[0m,\u001B[30m25\u001B[0m"));

    capture.reset();
    tree.postOrder();
    assertTrue(capture.toString().contains("[31m1\u001B[0m,\u001B[30m5\u001B[0m,\u001B[30m25\u001B[0m,\u001B[30m15\u001B[0m"));
  }

  /**
   * 插入的节点其parent是红色,uncle也是红色,插入的是右孩子
   */
  @Test
  public void insertCase2R() {
    RBTree tree = new RBTree();
    tree.insert(15);
    tree.insert(5);
    tree.insert(25);
    tree.insert(35);

    capture.reset();
    tree.preOrder();
    assertTrue(capture.toString().contains("[30m15\u001B[0m,\u001B[30m5\u001B[0m,\u001B[30m25\u001B[0m,\u001B[31m35\u001B[0m"));

    capture.reset();
    tree.inOrder();
    assertTrue(capture.toString().contains("[30m5\u001B[0m,\u001B[30m15\u001B[0m,\u001B[30m25\u001B[0m,\u001B[31m35\u001B[0m"));

    capture.reset();
    tree.postOrder();
    assertTrue(capture.toString().contains("[30m5\u001B[0m,\u001B[31m35\u001B[0m,\u001B[30m25\u001B[0m,\u001B[30m15\u001B[0m"));
  }

  /**
   * 插入的节点其parent是红色,uncle是黑色,插入的是左孩子,parent是grand的左孩子
   */
  @Test
  public void insertCase3LL() {
    RBTree tree = new RBTree();
    tree.insert(51);
    tree.insert(41);
    tree.insert(61);
    tree.insert(31);
    tree.insert(21);

    capture.reset();
    tree.preOrder();
    assertTrue(capture.toString().contains("[30m51\u001B[0m,\u001B[30m31\u001B[0m,\u001B[31m21\u001B[0m,\u001B[31m41\u001B[0m,\u001B[30m61\u001B[0m"));

    capture.reset();
    tree.inOrder();
    assertTrue(capture.toString().contains("[31m21\u001B[0m,\u001B[30m31\u001B[0m,\u001B[31m41\u001B[0m,\u001B[30m51\u001B[0m,\u001B[30m61\u001B[0m"));

    capture.reset();
    tree.postOrder();
    assertTrue(capture.toString().contains("[31m21\u001B[0m,\u001B[31m41\u001B[0m,\u001B[30m31\u001B[0m,\u001B[30m61\u001B[0m,\u001B[30m51\u001B[0m"));
  }

  /**
   * 插入的节点其parent是红色,uncle是黑色,插入的是右孩子,parent是grand的左孩子
   */
  @Test
  public void insertCase3LR() {
    RBTree tree = new RBTree();
    tree.insert(51);
    tree.insert(41);
    tree.insert(61);
    tree.insert(31);
    tree.insert(35);

    capture.reset();
    tree.preOrder();
    assertTrue(capture.toString().contains("[30m51\u001B[0m,\u001B[30m35\u001B[0m,\u001B[31m31\u001B[0m,\u001B[31m41\u001B[0m,\u001B[30m61\u001B[0m"));

    capture.reset();
    tree.inOrder();
    assertTrue(capture.toString().contains("[31m31\u001B[0m,\u001B[30m35\u001B[0m,\u001B[31m41\u001B[0m,\u001B[30m51\u001B[0m,\u001B[30m61\u001B[0m"));

    capture.reset();
    tree.postOrder();
    assertTrue(capture.toString().contains("[31m31\u001B[0m,\u001B[31m41\u001B[0m,\u001B[30m35\u001B[0m,\u001B[30m61\u001B[0m,\u001B[30m51\u001B[0m"));
  }

  /**
   * 插入的节点其parent是红色,uncle是黑色,插入的是左孩子,parent是grand的右孩子
   */
  @Test
  public void insertCase3RL() {
    RBTree tree = new RBTree();
    tree.insert(51);
    tree.insert(41);
    tree.insert(61);
    tree.insert(45);
    tree.insert(42);

    capture.reset();
    tree.preOrder();
    assertTrue(capture.toString().contains("[30m51\u001B[0m,\u001B[30m42\u001B[0m,\u001B[31m41\u001B[0m,\u001B[31m45\u001B[0m,\u001B[30m61\u001B[0m"));

    capture.reset();
    tree.inOrder();
    assertTrue(capture.toString().contains("[31m41\u001B[0m,\u001B[30m42\u001B[0m,\u001B[31m45\u001B[0m,\u001B[30m51\u001B[0m,\u001B[30m61\u001B[0m"));

    capture.reset();
    tree.postOrder();
    assertTrue(capture.toString().contains("[31m41\u001B[0m,\u001B[31m45\u001B[0m,\u001B[30m42\u001B[0m,\u001B[30m61\u001B[0m,\u001B[30m51\u001B[0m"));
  }

  /**
   * 插入的节点其parent是红色,uncle是黑色,插入的是右孩子,parent是grand的右孩子
   */
  @Test
  public void insertCase3RR() {
    RBTree tree = new RBTree();
    tree.insert(51);
    tree.insert(41);
    tree.insert(61);
    tree.insert(45);
    tree.insert(47);

    capture.reset();
    tree.preOrder();
    assertTrue(capture.toString().contains("[30m51\u001B[0m,\u001B[30m45\u001B[0m,\u001B[31m41\u001B[0m,\u001B[31m47\u001B[0m,\u001B[30m61\u001B[0m"));

    capture.reset();
    tree.inOrder();
    assertTrue(capture.toString().contains("[31m41\u001B[0m,\u001B[30m45\u001B[0m,\u001B[31m47\u001B[0m,\u001B[30m51\u001B[0m,\u001B[30m61\u001B[0m"));

    capture.reset();
    tree.postOrder();
    assertTrue(capture.toString().contains("[31m41\u001B[0m,\u001B[31m47\u001B[0m,\u001B[30m45\u001B[0m,\u001B[30m61\u001B[0m,\u001B[30m51\u001B[0m"));
  }

  /**
   * 删除红色节点,删除左孩子
   */
  @Test
  public void deleteCase0L() {
    RBTree tree = new RBTree();
    tree.insert(1);
    tree.insert(11);
    tree.insert(21);
    tree.insert(31);
    tree.insert(41);
    tree.insert(2);
    tree.insert(4);
    tree.insert(12);
    tree.insert(14);
    tree.insert(22);
    tree.insert(24);

    tree.remove(21);

    capture.reset();
    tree.preOrder();
    assertTrue(capture.toString().contains("[30m14\u001B[0m,\u001B[31m11\u001B[0m,\u001B[30m2\u001B[0m,\u001B[31m1\u001B[0m,\u001B[31m4\u001B[0m,\u001B[30m12\u001B[0m,\u001B[31m31\u001B[0m,\u001B[30m22\u001B[0m,\u001B[31m24\u001B[0m,\u001B[30m41\u001B[0m"));

    capture.reset();
    tree.inOrder();
    assertTrue(capture.toString().contains("[31m1\u001B[0m,\u001B[30m2\u001B[0m,\u001B[31m4\u001B[0m,\u001B[31m11\u001B[0m,\u001B[30m12\u001B[0m,\u001B[30m14\u001B[0m,\u001B[30m22\u001B[0m,\u001B[31m24\u001B[0m,\u001B[31m31\u001B[0m,\u001B[30m41\u001B[0m"));

    capture.reset();
    tree.postOrder();
    assertTrue(capture.toString().contains("[31m1\u001B[0m,\u001B[31m4\u001B[0m,\u001B[30m2\u001B[0m,\u001B[30m12\u001B[0m,\u001B[31m11\u001B[0m,\u001B[31m24\u001B[0m,\u001B[30m22\u001B[0m,\u001B[30m41\u001B[0m,\u001B[31m31\u001B[0m,\u001B[30m14\u001B[0m"));
  }

  /**
   * 删除红色节点,删除右孩子
   */
  @Test
  public void deleteCase0R() {
    RBTree tree = new RBTree();
    tree.insert(1);
    tree.insert(11);
    tree.insert(21);
    tree.insert(31);
    tree.insert(41);
    tree.insert(2);
    tree.insert(4);
    tree.insert(12);
    tree.insert(14);
    tree.insert(22);
    tree.insert(24);

    tree.remove(24);

    capture.reset();
    tree.preOrder();
    assertTrue(capture.toString().contains("[30m14\u001B[0m,\u001B[31m11\u001B[0m,\u001B[30m2\u001B[0m,\u001B[31m1\u001B[0m,\u001B[31m4\u001B[0m,\u001B[30m12\u001B[0m,\u001B[31m31\u001B[0m,\u001B[30m22\u001B[0m,\u001B[31m21\u001B[0m,\u001B[30m41\u001B[0m"));

    capture.reset();
    tree.inOrder();
    assertTrue(capture.toString().contains("[31m1\u001B[0m,\u001B[30m2\u001B[0m,\u001B[31m4\u001B[0m,\u001B[31m11\u001B[0m,\u001B[30m12\u001B[0m,\u001B[30m14\u001B[0m,\u001B[31m21\u001B[0m,\u001B[30m22\u001B[0m,\u001B[31m31\u001B[0m,\u001B[30m41\u001B[0m"));

    capture.reset();
    tree.postOrder();
    assertTrue(capture.toString().contains("[31m1\u001B[0m,\u001B[31m4\u001B[0m,\u001B[30m2\u001B[0m,\u001B[30m12\u001B[0m,\u001B[31m11\u001B[0m,\u001B[31m21\u001B[0m,\u001B[30m22\u001B[0m,\u001B[30m41\u001B[0m,\u001B[31m31\u001B[0m,\u001B[30m14\u001B[0m"));
  }

  /**
   * 删除黑色节点,兄弟是红色,当前节点是左孩子
   */
  @Test
  public void deleteCase1L() {
    RBTree tree = new RBTree();
    tree.insert(1);
    tree.insert(11);
    tree.insert(23);
    tree.insert(25);
    tree.insert(27);
    tree.insert(33);
    tree.insert(35);
    tree.insert(36);
    tree.insert(41);
    tree.insert(46);

    tree.remove(27);

    capture.reset();
    tree.preOrder();
    assertTrue(capture.toString().contains("[30m25\u001B[0m,\u001B[30m11\u001B[0m,\u001B[30m1\u001B[0m,\u001B[30m23\u001B[0m,\u001B[30m36\u001B[0m,\u001B[30m33\u001B[0m,\u001B[31m35\u001B[0m,\u001B[30m41\u001B[0m,\u001B[31m46\u001B[0m"));

    capture.reset();
    tree.inOrder();
    assertTrue(capture.toString().contains("[30m1\u001B[0m,\u001B[30m11\u001B[0m,\u001B[30m23\u001B[0m,\u001B[30m25\u001B[0m,\u001B[30m33\u001B[0m,\u001B[31m35\u001B[0m,\u001B[30m36\u001B[0m,\u001B[30m41\u001B[0m,\u001B[31m46\u001B[0m"));

    capture.reset();
    tree.postOrder();
    assertTrue(capture.toString().contains("[30m1\u001B[0m,\u001B[30m23\u001B[0m,\u001B[30m11\u001B[0m,\u001B[31m35\u001B[0m,\u001B[30m33\u001B[0m,\u001B[31m46\u001B[0m,\u001B[30m41\u001B[0m,\u001B[30m36\u001B[0m,\u001B[30m25\u001B[0m"));
  }

  /**
   * 删除黑色节点,兄弟是红色,当前节点是右孩子
   */
  @Test
  public void deleteCase1R() {
    // 1,11,15,21,27,33,22,23,24,25
    RBTree tree = new RBTree();
    tree.insert(1);
    tree.insert(11);
    tree.insert(15);
    tree.insert(21);
    tree.insert(27);
    tree.insert(33);
    tree.insert(22);
    tree.insert(23);
    tree.insert(24);
    tree.insert(25);

    tree.remove(33);

    capture.reset();
    tree.preOrder();
    assertTrue(capture.toString().contains("[30m21\u001B[0m,\u001B[30m11\u001B[0m,\u001B[30m1\u001B[0m,\u001B[30m15\u001B[0m,\u001B[30m23\u001B[0m,\u001B[30m22\u001B[0m,\u001B[31m25\u001B[0m,\u001B[30m24\u001B[0m,\u001B[30m27\u001B[0m"));

    capture.reset();
    tree.inOrder();
    assertTrue(capture.toString().contains("[30m1\u001B[0m,\u001B[30m11\u001B[0m,\u001B[30m15\u001B[0m,\u001B[30m21\u001B[0m,\u001B[30m22\u001B[0m,\u001B[30m23\u001B[0m,\u001B[30m24\u001B[0m,\u001B[31m25\u001B[0m,\u001B[30m27\u001B[0m"));

    capture.reset();
    tree.postOrder();
    assertTrue(capture.toString().contains("[30m1\u001B[0m,\u001B[30m15\u001B[0m,\u001B[30m11\u001B[0m,\u001B[30m22\u001B[0m,\u001B[30m24\u001B[0m,\u001B[30m27\u001B[0m,\u001B[31m25\u001B[0m,\u001B[30m23\u001B[0m,\u001B[30m21\u001B[0m"));
  }

  /**
   * 删除黑色节点,兄弟是黑色,左右侄子都是黑色,当前节点是左孩子
   */
  @Test
  public void deleteCase2L() {
    // 5,15,25,35,45,55,65,75
    RBTree tree = new RBTree();
    tree.insert(5);
    tree.insert(15);
    tree.insert(25);
    tree.insert(35);
    tree.insert(45);
    tree.insert(55);
    tree.insert(65);
    tree.insert(75);

    tree.remove(5);

    capture.reset();
    tree.preOrder();
    assertTrue(capture.toString().contains("[30m35\u001B[0m,\u001B[30m15\u001B[0m,\u001B[31m25\u001B[0m,\u001B[31m55\u001B[0m,\u001B[30m45\u001B[0m,\u001B[30m65\u001B[0m,\u001B[31m75\u001B[0m"));

    capture.reset();
    tree.inOrder();
    assertTrue(capture.toString().contains("[30m15\u001B[0m,\u001B[31m25\u001B[0m,\u001B[30m35\u001B[0m,\u001B[30m45\u001B[0m,\u001B[31m55\u001B[0m,\u001B[30m65\u001B[0m,\u001B[31m75\u001B[0m"));

    capture.reset();
    tree.postOrder();
    assertTrue(capture.toString().contains("[31m25\u001B[0m,\u001B[30m15\u001B[0m,\u001B[30m45\u001B[0m,\u001B[31m75\u001B[0m,\u001B[30m65\u001B[0m,\u001B[31m55\u001B[0m,\u001B[30m35\u001B[0m"));
  }

  /**
   * 删除黑色节点,兄弟是黑色,左右侄子都是黑色,当前节点是右孩子
   */
  @Test
  public void deleteCase2R() {
    // 5,15,25,35,45,55,65,75
    RBTree tree = new RBTree();
    tree.insert(5);
    tree.insert(15);
    tree.insert(25);
    tree.insert(35);
    tree.insert(45);
    tree.insert(55);
    tree.insert(65);
    tree.insert(75);

    tree.remove(25);

    capture.reset();
    tree.preOrder();
    assertTrue(capture.toString().contains("[30m35\u001B[0m,\u001B[30m15\u001B[0m,\u001B[31m5\u001B[0m,\u001B[31m55\u001B[0m,\u001B[30m45\u001B[0m,\u001B[30m65\u001B[0m,\u001B[31m75\u001B[0m"));

    capture.reset();
    tree.inOrder();
    assertTrue(capture.toString().contains("[31m5\u001B[0m,\u001B[30m15\u001B[0m,\u001B[30m35\u001B[0m,\u001B[30m45\u001B[0m,\u001B[31m55\u001B[0m,\u001B[30m65\u001B[0m,\u001B[31m75\u001B[0m"));

    capture.reset();
    tree.postOrder();
    assertTrue(capture.toString().contains("[31m5\u001B[0m,\u001B[30m15\u001B[0m,\u001B[30m45\u001B[0m,\u001B[31m75\u001B[0m,\u001B[30m65\u001B[0m,\u001B[31m55\u001B[0m,\u001B[30m35\u001B[0m"));
  }

  /**
   * 删除黑色,兄弟是黑色,左侄子是黑色,右侄子是红色,当前节点是左孩子
   */
  @Test
  public void deleteCase3L() {
    // 1,11,21,31,41,51
    RBTree tree = new RBTree();
    tree.insert(1);
    tree.insert(11);
    tree.insert(21);
    tree.insert(31);
    tree.insert(41);
    tree.insert(51);

    tree.remove(21);

    tree.preOrder();
    tree.inOrder();
    tree.postOrder();
  }

  /**
   * 删除黑色,兄弟是黑色,左侄子是黑色,右侄子是红色,当前节点是右孩子
   */
  @Test
  public void deleteCase3R() {
    // 1,11,21,31,41,25
    RBTree tree = new RBTree();
    tree.insert(1);
    tree.insert(11);
    tree.insert(21);
    tree.insert(31);
    tree.insert(41);
    tree.insert(25);

    tree.remove(41);

    tree.preOrder();
    tree.inOrder();
    tree.postOrder();
  }

  /**
   * 删除黑色,兄弟是黑色,右侄子是黑色,左侄子是红色,当前是左孩子
   */
  @Test
  public void deleteCase4L() {
    // 1,11,21,31,41,35
    RBTree tree = new RBTree();
    tree.insert(1);
    tree.insert(11);
    tree.insert(21);
    tree.insert(31);
    tree.insert(41);
    tree.insert(35);

    tree.remove(21);

    tree.preOrder();
    tree.inOrder();
    tree.postOrder();
  }

  /**
   * 删除黑色,兄弟是黑色,右侄子是黑色,左侄子是红色,当前是右孩子
   */
  @Test
  public void deleteCase4R() {
    // 1,11,21,31,41,15
    RBTree tree = new RBTree();
    tree.insert(1);
    tree.insert(11);
    tree.insert(21);
    tree.insert(31);
    tree.insert(41);
    tree.insert(15);

    tree.remove(41);

    tree.preOrder();
    tree.inOrder();
    tree.postOrder();
  }


  /**
   * 删除黑色,兄弟是黑色,右侄子是红色,左侄子颜色任意,当前是左孩子
   */
  @Test
  public void deleteCase5L() {
    // 1,11,21,31,41,35,51
    RBTree tree = new RBTree();
    tree.insert(1);
    tree.insert(11);
    tree.insert(21);
    tree.insert(31);
    tree.insert(41);
    tree.insert(35);
    tree.insert(51);

    tree.remove(21);

    tree.preOrder();
    tree.inOrder();
    tree.postOrder();
  }

  /**
   * 删除黑色,兄弟是黑色,右侄子是红色,左侄子颜色任意,当前是右孩子
   */
  @Test
  public void deleteCase5R() {
    // 1,11,21,31,41,15,25
    RBTree tree = new RBTree();
    tree.insert(1);
    tree.insert(11);
    tree.insert(21);
    tree.insert(31);
    tree.insert(41);
    tree.insert(15);
    tree.insert(25);

    tree.remove(41);

    tree.preOrder();
    tree.inOrder();
    tree.postOrder();
  }
}
