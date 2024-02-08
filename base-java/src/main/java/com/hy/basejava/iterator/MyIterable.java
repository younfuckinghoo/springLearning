package com.hy.basejava.iterator;

import java.util.Iterator;

public class MyIterable implements Iterable<String>{

    private  Integer[] integers;


    public MyIterable(Integer... integers) {
        this.integers = integers;
    }

    public Integer[] getIntegers() {
        return integers;
    }

    public void setIntegers(Integer[] integers) {
        this.integers = integers;
    }

    @Override
    public Iterator<String> iterator() {
        return new IntegerToStrIterator(this);
    }


    static class IntegerToStrIterator implements Iterator<String>{

        private int currentIndex = 0;
        private int nextIndex = 0;

        private final MyIterable iterable;

        IntegerToStrIterator(MyIterable iterable) {
            this.iterable = iterable;
        }

        @Override
        public boolean hasNext() {
            try {
                // 尝试获取下一个 获取不到则表示数组越界了
                Integer integer = iterable.getIntegers()[nextIndex];
                return true;
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }

        @Override
        public String next() {
            // 当再次调用next时 当前索引才会真正+1 否则当前索引为最后一次调用next时的索引
            currentIndex = nextIndex;
            String s = iterable.getIntegers()[currentIndex].toString();
            nextIndex = currentIndex+1;
            return s;
        }

        @Override
        public void remove() {
            Integer[] integers = iterable.getIntegers();
            Integer[] newIntegers = new Integer[integers.length-1];
            // 当前索引的值正好是从0到当前索引所在数据长度-1
            System.arraycopy(integers,0,newIntegers,0,currentIndex);
            System.arraycopy(integers,currentIndex+1,newIntegers,currentIndex,newIntegers.length-currentIndex);
            currentIndex--;
            nextIndex--;
            iterable.setIntegers(newIntegers);
//            Iterator.super.remove();
        }
    }

}
