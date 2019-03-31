package net.yeah.rbt;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="zipeng.dzp@alibaba-inc.com">daosu</a>
 * @version created on 2019/1/9 15:25.
 */
public class RBTree {
  private static final boolean RED = false;
  private static final boolean BLACK = true;

  private enum Order {
    PREORDER, INORDER, POSTORDER;
  }

  // 代表所有的末端叶子节点,可以节省空间;  叶子节点一定是黑色的
  private static final Node NIL = new Node(null, null);
  private transient Node root;

  public void insert(int value) {
    // 插入的是根节点
    Node t = root;
    if (t == null) {
      root = new Node(value, null);
      return;
    }

    // 找到插入节点的parent
    Node parent;
    do {
      parent = t;
      if (value < t.value) {
        t = t.left;
      } else {
        t = t.right;
      }
    } while (t != NIL);

    Node n = new Node(value, parent);
    if (value < parent.value) {
      parent.left = n;
    } else {
      parent.right = n;
    }
    fixAfterInsertion(n);
  }

  public void remove(int value) {
    // 首先找到待删除的节点
    Node t = root;
    while (t != NIL) {
      if (value < t.value) {
        t = t.left;
      } else if (value > t.value) {
        t = t.right;
      } else {
        break;
      }
    }

    // 如果待删除的节点不存在,则退出
    if (t == NIL) {
      return;
    }

    // 如果待删除节点有左右孩子,则找到后继节点作为替身
    if (t.left != NIL && t.right != NIL) {
      Node s = successor(t);
      t.value = s.value;
      t = s;
    }

    // Start fixup at replacement node, if it exists.
    Node replacement = (t.left != NIL ? t.left : t.right);

    if (replacement != NIL) {
      // Link replacement to parent
      replacement.parent = t.parent;
      if (t.parent == null) {
        root = replacement;
      } else if (t == t.parent.left) {
        t.parent.left = replacement;
      } else {
        t.parent.right = replacement;
      }

      // Null out links so they are OK to use by fixAfterDeletion.
      t.left = t.right = t.parent = null;

      // Fix replacement
      if (t.color == BLACK) {
        fixAfterDeletion(replacement);
      }
    } else if (t.parent == null) { // return if we are the only node.
      root = null;
    } else {
      //  No children. Use self as phantom replacement and unlink.
      if (t.color == BLACK) {
        fixAfterDeletion(t);
      }

      // case: 删除红色节点,直接删除即可
      if (t.parent != null) {
        if (t == t.parent.left) {
          t.parent.left = NIL;
        } else if (t == t.parent.right) {
          t.parent.right = NIL;
        }
        t.parent = null;
      }
    }
  }

  public void preOrder() {
    print(Order.PREORDER);
  }

  public void inOrder() {
    print(Order.INORDER);
  }

  public void postOrder() {
    print(Order.POSTORDER);
  }

  private void print(Order order) {
    List<String> list = new ArrayList<>();
    inStack(list, root, order);
    System.out.println(StringUtils.join(list, ','));
  }

  private void inStack(List<String> stack, Node node, Order order) {
    if (node == NIL) {
      return;
    }
    switch (order) {
      case PREORDER:
        stack.add(node.toString());
        inStack(stack, node.left, order);
        inStack(stack, node.right, order);
        break;
      case INORDER:
        inStack(stack, node.left, order);
        stack.add(node.toString());
        inStack(stack, node.right, order);
        break;
      case POSTORDER:
        inStack(stack, node.left, order);
        inStack(stack, node.right, order);
        stack.add(node.toString());
        break;
    }
  }

  private void fixAfterDeletion(Node x) {
    while (x != root && colorOf(x) == BLACK) {
      if (x == leftOf(parentOf(x))) {
        Node sib = rightOf(parentOf(x));

        if (colorOf(sib) == RED) {
          setColor(sib, BLACK);
          setColor(parentOf(x), RED);
          rotateLeft(parentOf(x));
          sib = rightOf(parentOf(x));
        }

        if (colorOf(leftOf(sib)) == BLACK &&
                colorOf(rightOf(sib)) == BLACK) {
          setColor(sib, RED);
          x = parentOf(x);
        } else {
          if (colorOf(rightOf(sib)) == BLACK) {
            setColor(leftOf(sib), BLACK);
            setColor(sib, RED);
            rotateRight(sib);
            sib = rightOf(parentOf(x));
          }
          setColor(sib, colorOf(parentOf(x)));
          setColor(parentOf(x), BLACK);
          setColor(rightOf(sib), BLACK);
          rotateLeft(parentOf(x));
          x = root;
        }
      } else { // symmetric
        Node sib = leftOf(parentOf(x));

        if (colorOf(sib) == RED) {
          setColor(sib, BLACK);
          setColor(parentOf(x), RED);
          rotateRight(parentOf(x));
          sib = leftOf(parentOf(x));
        }

        if (colorOf(rightOf(sib)) == BLACK &&
                colorOf(leftOf(sib)) == BLACK) {
          setColor(sib, RED);
          x = parentOf(x);
        } else {
          if (colorOf(leftOf(sib)) == BLACK) {
            setColor(rightOf(sib), BLACK);
            setColor(sib, RED);
            rotateLeft(sib);
            sib = leftOf(parentOf(x));
          }
          setColor(sib, colorOf(parentOf(x)));
          setColor(parentOf(x), BLACK);
          setColor(leftOf(sib), BLACK);
          rotateRight(parentOf(x));
          x = root;
        }
      }
    }

    setColor(x, BLACK);
  }

  private void fixAfterInsertion(Node x) {
    // 新节点设置为红色,可以减少对规则的冲突
    x.color = RED;
    // 防止出现连续两个红色节点
    while (x != NIL && x != root && x.parent.color == RED) {
      // case: parent是grand的左孩子
      if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
        // uncle节点
        Node y = rightOf(parentOf(parentOf(x)));
        // case: uncle是红色,向上传递hongse
        if (colorOf(y) == RED) {
          setColor(parentOf(x), BLACK);
          setColor(y, BLACK);
          setColor(parentOf(parentOf(x)), RED);
          x = parentOf(parentOf(x));
        } else {
          // case: uncle是黑色
          // 变成left-left的情况
          if (x == rightOf(parentOf(x))) {
            x = parentOf(x);
            rotateLeft(x);
          }
          setColor(parentOf(x), BLACK);
          setColor(parentOf(parentOf(x)), RED);
          rotateRight(parentOf(parentOf(x)));
        }
      } else {
        // case: parent是grand的右孩子
        // uncle节点
        Node y = leftOf(parentOf(parentOf(x)));
        // case: uncle是红色
        if (colorOf(y) == RED) {
          setColor(parentOf(x), BLACK);
          setColor(y, BLACK);
          setColor(parentOf(parentOf(x)), RED);
          x = parentOf(parentOf(x));
        } else {
          // case: uncle是黑色
          // 变成right-right的情况
          if (x == leftOf(parentOf(x))) {
            x = parentOf(x);
            rotateRight(x);
          }
          setColor(parentOf(x), BLACK);
          setColor(parentOf(parentOf(x)), RED);
          rotateLeft(parentOf(parentOf(x)));
        }
      }
    }
    root.color = BLACK;
  }

  // 左旋就是把当前节点变成其right child的左孩子
  private void rotateLeft(Node n) {
    if (n != NIL) {
      Node r = n.right;
      n.right = r.left;
      if (r.left != NIL) {
        r.left.parent = n;
      }
      r.parent = n.parent;
      if (n.parent == null) {
        root = r;
      } else if (n.parent.left == n) {
        n.parent.left = r;
      } else {
        n.parent.right = r;
      }
      r.left = n;
      n.parent = r;
    }
  }

  /**
   * 右旋就是把当前节点变成其left child的右孩子
   * @param n
   */
  private void rotateRight(Node n) {
    if (n != NIL) {
      Node l = n.left;
      n.left = l.right;
      if (l.right != NIL) {
        l.right.parent = n;
      }
      l.parent = n.parent;
      if (n.parent == null) {
        root = l;
      } else if (n.parent.right == n) {
        n.parent.right = l;
      } else {
        n.parent.left = l;
      }
      l.right = n;
      n.parent = l;
    }
  }

  private Node parentOf(Node n) {
    return (n == null ? null : n.parent);
  }

  private Node leftOf(Node n) {
    return (n == null) ? NIL : n.left;
  }

  private Node rightOf(Node n) {
    return (n == null) ? NIL : n.right;
  }

  private boolean colorOf(Node n) {
    return (n == null ? BLACK : n.color);
  }

  private void setColor(Node n, boolean c) {
    if (n != null) {
      n.color = c;
    }
  }

  /**
   * 寻找替身
   */
  private Node successor(Node n) {
    if (n == NIL) {
      return null;
    } else if (n.right != null) {
      // 右孩子的最小值
      Node p = n.right;
      while (p.left != NIL) {
        p = p.left;
      }
      return p;
    } else {
      // TODO ????
      Node p = n.parent;
      Node ch = n;
      while (p != null && ch == p.right) {
        ch = p;
        p = p.parent;
      }
      return p;
    }
  }

  @Getter
  @Setter
  public static class Node {
    private Integer value;
    private Node left;
    private Node right;
    private Node parent;
    private boolean color = BLACK;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";

    public Node(Integer value, Node parent) {
      this.value = value;
      this.left = NIL;
      this.right = NIL;
      this.parent = parent;
    }

    public boolean isRed() {
      return color == RED;
    }

    @Override
    public String toString() {
      return (isRed() ? ANSI_RED : ANSI_BLACK) + value + ANSI_RESET;
    }
  }
}
