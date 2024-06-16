package com.example.example16;

public class ProblematicInventory {

    public static void main(String[] args) throws InterruptedException {
        var inventoryCounter = new InventoryCounter();
        var ascUpdateThread = new UpdateThread(inventoryCounter, true);
        var descUpdateThread = new UpdateThread(inventoryCounter, false);

        ascUpdateThread.start();
        descUpdateThread.start();

        ascUpdateThread.join();
        descUpdateThread.join();

        System.out.println(inventoryCounter.getItems());
    }

    private static class UpdateThread extends Thread {
        private final InventoryCounter inventoryCounter;
        private final boolean ascending;

        public UpdateThread(InventoryCounter inventoryCounter, boolean ascending) {
            this.inventoryCounter = inventoryCounter;
            this.ascending = ascending;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                if (ascending) {
                    inventoryCounter.increment();
                } else {
                    inventoryCounter.decrement();
                }
            }
        }
    }

    static class InventoryCounter {
        private int items = 0;

        public void increment() {
            items++;
        }

        public void decrement() {
            items--;
        }

        public int getItems() {
            return items;
        }
    }
}
