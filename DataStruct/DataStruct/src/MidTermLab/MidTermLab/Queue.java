package AssignmentSpecial;

public class Queue {

    private int maxQueueSize = 100;
    private int queueFront = 0;
    private int queueRear = maxQueueSize - 1;
    private int count = 0;
    private Object[] list;

    public Queue() {
        list = new Object[maxQueueSize];
    }

    public Queue(int size) {
        list = new Object[size];
        maxQueueSize = size;
        queueRear = maxQueueSize - 1;
    }

    public void initializeQueue() {
        for (int i = queueFront; i < queueRear; i = (i + 1) % maxQueueSize) {
            list[i] = null;
        }
        queueFront = 0;
        queueRear = maxQueueSize - 1;
        count = 0;
    }

    public boolean isEmptyQueue() {
        return (count == 0);
    }

    public boolean isFullQueue() {
        return (count == maxQueueSize);
    }

    public Object front() throws Exception {
        if (isEmptyQueue()) {
            throw new Exception("QueueUnderflow");
        }
        return list[queueFront];
    }

    public Object back() throws Exception {
        if (isEmptyQueue()) {
            throw new Exception("QueueUnderflow");
        }
        return list[queueRear];
    }

    public void enQueue(Object newItem) throws Exception {
        if (isFullQueue()) {
            throw new Exception("QueueOverflow");
        }
        queueRear = (queueRear + 1) % maxQueueSize;
        count++;
        list[queueRear] = newItem;
    }

    public void deQueue() throws Exception {
        if (isEmptyQueue()) {
            throw new Exception("QueueUnderflow");
        }
        count--;
        list[queueFront] = null;
        queueFront = (queueFront + 1) % maxQueueSize;
    }

    public int getSize() {
        return count;
    }

    public int getMaxSize() {
        return maxQueueSize;
    }
}
