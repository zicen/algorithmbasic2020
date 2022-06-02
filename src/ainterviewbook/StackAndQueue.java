package ainterviewbook;

import java.util.Stack;

// 栈和队列
public class StackAndQueue {
    // 设计一个带有getMin功能的栈
    private class MyMinStack{
        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;
        public MyMinStack(){
            this.stackData = new Stack<>();
            this.stackMin = new Stack<>();
        }

        public void push(int newNum) {
            if (this.stackMin.isEmpty()) {
                this.stackMin.push(newNum);
            }else if (newNum <= this.getMin()){
                this.stackMin.push(newNum);
            }
            this.stackData.push(newNum);
        }

        public int getMin(){
           return stackMin.peek();
        }

        public int pop(){
            Integer pop = stackData.pop();
            if (pop == getMin()) {
                stackMin.pop();
            }
            return pop;
        }
    }
}
