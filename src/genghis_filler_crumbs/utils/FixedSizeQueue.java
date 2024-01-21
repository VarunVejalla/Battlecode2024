package genghis_filler_crumbs.utils;

import java.util.LinkedList;
public class FixedSizeQueue<T> extends LinkedList<T> {
    private final int maxSize;

    public FixedSizeQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public boolean add(T element) {
        boolean added = super.add(element);
        if (size() > maxSize) {
            // Remove the oldest element if the size exceeds the limit
            super.removeFirst();
        }
        return added;
    }
}

// public class FixedSizeQueue<T> {
//     private int size;
//     private int cur_size;
//     private LLNode<T> head;
//     private LLNode<T> tail;

//     public FixedSizeQueue(int size) {
//         this.size = size;
//         this.cur_size = 0;
//         head = new LLNode<T>(null);
//         tail = new LLNode<T>(null);
//         head.setNext(tail);
//         tail.setPrev(head);
//     }

//     public int size() {
//         return cur_size;
//     }

//     public boolean add(T element) {
//         LLNode<T> node = new LLNode<T>(element);
//         node.setNext(head.getNext());
//         node.setPrev(head);
//         head.getNext().setPrev(node);
//         head.setNext(node);
//         cur_size++;
//         if (size() > size) {
//             poll();
//             cur_size--;
//         }
//         return true;
//     }

//     public T poll() {
//         if (cur_size == 0) {
//             return null;
//         }
//         LLNode<T> node = tail.getPrev();
//         node.getPrev().setNext(tail);
//         tail.setPrev(node.getPrev());
//         cur_size--;
//         return node.getValue();
//     }
// }

// class LLNode<T> {
//     private LLNode<T> next;
//     private LLNode<T> prev;
//     private T value;

//     public LLNode(T value) {
//         this.value = value;
//     }

//     public LLNode<T> getNext() {
//         return next;
//     }

//     public LLNode<T> getPrev() {
//         return prev;
//     }

//     public T getValue() {
//         return value;
//     }

//     public void setNext(LLNode<T> next) {
//         this.next = next;
//     }

//     public void setPrev(LLNode<T> prev) {
//         this.prev = prev;
//     }
// }
