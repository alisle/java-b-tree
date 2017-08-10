package org.alisle;

/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by alisle on 6/9/17.
 */


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * This code has been taken / modified from the Algorithms 4th Edition Book.
 */
public  class Page<T extends Comparable<T>, E> {
    private static Logger log = LoggerFactory.getLogger(Page.class);

    /**
     * Internal Class used to get Results
     * @param <E> The Value Type
     */
    private final static class SearchResults<E> {
        private final int position;
        private final boolean found;
        private final E value;

        /**
         * Constructs the Search Results
         * @param found True if the key is within this Page
         * @param position Position within the Page if found, otherwise the place to insert a page.
         * @param value If found the value found at that key.
         */
        public SearchResults(boolean found, int position, E value) {
            this.position = position;
            this.found = found;
            this.value = value;
        }

        @Override
        public String toString() {
            return "SearchResults{" +
                    "position=" + position +
                    ", found=" + found +
                    ", value=" + value +
                    '}';
        }
    }

    /**
     * The Page Header.
     */
    private final PageHeader header;

    /**
     * The entries.
     */
    private Entry[] entries;

    /**
     * Helper constructor to pass in the already optional'd IPageHandler
     * @param maxPageSize The maximum number of elements within this page.
     * @param external defines if this page should be external or not.
     */
    public Page(int maxPageSize, boolean external) {
        this.header = new PageHeader(UUID.randomUUID(), maxPageSize,  external);
        this.entries = new Entry[maxPageSize + 1];
    }

    /**
     * Checks to see if this page is External or not.
     * @return True if this page is external
     */
    public boolean isExternal() {
        return header.isExternal();
    }

    /**
     * Gets the value associated with the Key
     * @param key The key used for look up
     * @return The value stored at the key.
     */
    // TODO: This should be an optional
    public E get(T key) {
        SearchResults results = position(key);
        return (E)results.value;
    }

    /**
     * Checks to see if this page contains that key.
     * @return True if this page contains the key.
     */
    public boolean contains(T key) {
        return position(key).found;
    }

    /**
     * Finds the position within the Array the key should be placed.
     * @param key Key to compare again
     * @return Position within the Array
     */
    private SearchResults position(T key) {
        int x;
        //TODO: Change this to do a binary search.
        loop: for(x = 0; x < header.getCurrentSize(); x++) {
            if(key == null || entries == null || entries[x] == null) {
                log.error("Oh Dear!");
            }

            switch (key.compareTo((T)entries[x].getKey())) {
                case 0: {
                    if(log.isTraceEnabled()) { log.trace("Found Key: " + key); }
                    return new SearchResults(true, x, entries[x].getValue());
                }
                case -1: {
                    break loop;
                }
            }
        }

        return new SearchResults(false, x, null);
    }

    /**
     * Returns the Page that could contain the key
     * @param key Key that is being looked for
     * @return Page which could contain the key.
     */
    public Page next(T key) {
        if(header.isExternal()) {
            throw new RuntimeException("Unable to get a page at an external node");
        }

        SearchResults<E> results = position(key);
        return results.found ? entries[results.position].getNext(): entries[results.position - 1].getNext();
    }


    /**
     * Checks to see if the page is full.
     * @return True if the page is full.
     */
    public boolean isFull() {
        return header.getCurrentSize() == header.getMaxPageSize();
    }

    /**
     * States the current indexSize of the page
     * @return The current page indexSize
     */
    public int size() {
        return header.getCurrentSize();
    }

    /**
     * Adds the child page to this page
     * @param page Page to add as a child
     */
    public void add(Page page) {
        if(header.isExternal()) {
            throw new RuntimeException("Unable to insert page at external level!");
        }

        if(isFull()) {
            throw new RuntimeException("This node is already full and needs to be split");
        }

        T smallest = (T)page.keys().iterator().next();
        int position = position(smallest).position;

        for(int x = header.getCurrentSize() + 1; x  > position; x--) {
            entries[x] = entries[x - 1];
        }

        entries[position] = new Entry(smallest, page);
        header.incrementCurrentSize();
    }


    /**
     * Adds a key to the page
     * @param key Key to add to the page
     */
    public void add(T key, E value) {
        if(!header.isExternal()) {
            throw new RuntimeException("Unable to insert key at internal level!");
        }

        if(isFull()) {
            throw new RuntimeException("This node is already full and needs to be split");
        }

        int position = position(key).position;
        for(int x = header.getCurrentSize() + 1; x  > position; x--) {
            entries[x] = entries[x - 1];
        }

        entries[position] = new Entry(key,  value);
        header.incrementCurrentSize();

    }

    /**
     * Splits this page returning a new page with the higher values entries.
     * @return Page containing the higher values.
     */
    public Page split() {
        if(log.isTraceEnabled()) { log.trace("Splitting Page:" + header.getUuid()); }

        Page page = new Page(header.getMaxPageSize(), this.isExternal());
        for(int x = header.getCurrentSize() / 2; x < header.getCurrentSize(); x++) {
            if(isExternal()) {
                page.add(this.entries[x].getKey(), this.entries[x].getValue());
            } else {
                page.add(this.entries[x].getNext());
            }
            this.entries[x] = null;
        }

        header.setCurrentSize( header.getCurrentSize() / 2);
        return page;
    }

    /**
     * Returns an interator over the keys
     * @return
     */
    public Iterable<T> keys() {
        return () -> new Iterator<T>() {
            int next = 0;

            @Override
            public boolean hasNext() {
                return next < header.getCurrentSize();
            }

            @Override
            public T next() {
                T returnValue = (T)entries[next].getKey();

                next = next + 1  ;
                return returnValue;
            }
        };
    }

    /**
     * Returns an iterator over the entries
     * @return
     */
    public Iterable<Entry<T, E>> entries() {
        return () -> new Iterator<Entry<T, E>>() {
            int next = 0;
            @Override
            public boolean hasNext() {
                return next < header.getCurrentSize();
            }

            @Override
            public Entry<T, E> next() {
                Entry<T, E> entry = entries[next];
                next += 1;

                return entry;
            }
        };
    }

    /**
     * Gets the Page Header
     * @return The Header
     */
    public PageHeader getHeader() {
        return header;
    }

    /**
     * Closes this page.
     */
    public void close() {

    }


    @Override
    public String toString() {
        return "Page{" +
                "external=" + header.isExternal() +
                ", currentSize=" + header.getCurrentSize() +
                ", entries=" + Arrays.toString(entries) +
                '}';
    }
}
