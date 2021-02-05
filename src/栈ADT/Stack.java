package 栈ADT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 栈是限制插入和删除只能在一个末位位置上进行的表
 * 表的末端被称为栈顶
 * <p>
 * LIFO
 *
 * @author asus
 */
public class Stack<T> {

    private ArrayList<T> list = new ArrayList<>();

    /**
     * 入栈（插入新元素到末尾）
     */
    public void push(T t) {
        list.add(t);
    }

    /**
     * 出栈（删除最后插入的元素）
     */
    public T pop() {
        int size = list.size();
        if (size < 1) {
            throw new NullPointerException("栈中没有数据");
        }
        T t = list.get(size - 1);
        list.remove(size - 1);
        return t;
    }

    static class StackUtil {

        public static List<String> SYMBOL = Arrays.asList(new String[]{"+", "-", "*", "/"}.clone());

        /**
         * 用于测试的后缀表达式计算
         *
         * @param list 数据集合
         * @return 表达式的值
         */
        public static Stack<String> calcPostfixExpression(List<String> list) {

            Stack<String> stack = new Stack<>();

            if (list.size() < 1) {
                return stack;
            }
            for (String element :
                    list) {
                if (SYMBOL.contains(element)) {
                    Integer num1 = Integer.parseInt(stack.pop());
                    Integer num2 = Integer.parseInt(stack.pop());
                    int result;

                    if ("+".equals(element)) {
                        result = num1 + num2;
                    } else if ("-".equals(element)) {
                        result = num1 - num2;
                    } else if ("/".equals(element)) {
                        result = num1 / num2;
                    } else {
                        result = num1 * num2;
                    }

                    stack.push(result + "");
                } else {
                    stack.push(element);
                }
            }
            return stack;
        }

        /**
         * 中缀表达式转后缀表达式
         *
         * @return 后缀表达式
         */
        /*public static String middleToLast(char[] arr) {

            boolean firstSymbol = true;
            Stack<String> stack = new Stack<>();
            StringBuilder result = new StringBuilder();
            for (char c :
                    arr) {
                if (SYMBOL.contains(c)) {
                    if ("+".equals(c) && firstSymbol) {
                        firstSymbol = false;
                    } else if ("+".equals(c)) {
                        Iterator<String> it = stack.list.iterator();
                        while (it.hasNext()) {
                            it.next();
                        }
                    } else {
                    }
                    stack.push(c + "");
                }
            } else{
                result.append(c);
            }
        }*/
    }

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("6");
        list.add("5");
        list.add("2");
        list.add("3");
        list.add("+");
        list.add("8");
        list.add("*");
        list.add("+");
        list.add("3");
        list.add("+");
        list.add("*");

        Stack<String> stack = StackUtil.calcPostfixExpression(list);
        System.out.println(stack);
    }
}
