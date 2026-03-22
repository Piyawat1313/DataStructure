public class Stack {
    private int maxStackSize = 100;
    private int stackTop = 0;
    private int[] liStacks;

    public Stack() {
        liStacks = new int[maxStackSize];
    }

    public Stack(int size) {
        liStacks = new int[size];
        maxStackSize = size;
    }

    public int getSize() {
        return stackTop;
    }

    public int getMaxSize() {
        return maxStackSize;
    }

    /*
     * initialize Stack
     */
    public void initializeStack() {
        for (int i = 0; i < stackTop; i++) {
            liStacks[i] = 0;
            stackTop = 0;
        }
    }

    // Empty Stack
    public boolean isEmptyStack() {
        return (stackTop == 0);
    }

    // full Stack
    public boolean isFullStack() {
        return (stackTop == maxStackSize);
    }

    // push: การเพิ่มข้อมูลเข้า Stack
    public void push(int newItem) throws Exception {
        if (isFullStack()) {
            throw new Exception("Stack Overflow");
        }
        liStacks[stackTop] = newItem;
        stackTop++;
    }

    // peek: ดูข้อมูลที่อยู่บนสุดของ Stack
    public int peek() throws Exception {
        if (isEmptyStack()) {
            throw new Exception("StackUnderflow");
        }
        return liStacks[stackTop - 1];
    }

    // pop: ลบข้อมูลออกจาก Stack
    public int pop() throws Exception {
        int ItemInfo;
        if (isEmptyStack()) {
            throw new Exception("StackUnderFlow");
        }
        stackTop--;
        ItemInfo = liStacks[stackTop];
       // liStacks[stackTop] = 0;
        return ItemInfo;
    }

}

