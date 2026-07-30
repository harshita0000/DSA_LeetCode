class LRUCache {

    private class Node {
        int key, value;
        Node prev, next;

        Node(int k, int v) {
            key = k;
            value = v;
            prev = null;
            next = null;
        }
    }

    private int capacity;
    private HashMap<Integer, Node> cache;
    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;

        cache = new HashMap<>();

        head = new Node(-1, -1);
        tail = new Node(-1, -1);

        head.next = tail;
        tail.prev = head;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void insertToFront(Node node) {
        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    public int get(int key) {

        if (!cache.containsKey(key))
            return -1;

        Node node = cache.get(key);


        remove(node);
        insertToFront(node);

        return node.value;
    }

    public void put(int key, int value) {

        if (cache.containsKey(key)) {

            Node node = cache.get(key);

            node.value = value;

            remove(node);
            insertToFront(node);
        }

        else {

            if (cache.size() == capacity) {

                Node lru = tail.prev;

                cache.remove(lru.key);
                remove(lru);
            }

            Node node = new Node(key, value);

            cache.put(key, node);

            insertToFront(node);
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key, value);
 */