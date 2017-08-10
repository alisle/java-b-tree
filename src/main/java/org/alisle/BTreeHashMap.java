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
 * Created by alisle on 6/13/17.
 */


import java.util.Optional;

/**
 * This code has been taken and modified from the Algorithms Fourth Edition book.
 */
public class BTreeHashMap<T extends  Comparable<T>, E> {
    protected final int pageSize;
    protected Page root;

    /**
     * Creates a new HashMap.
     * @param pageSize The maximum number of instances a page can have.
     * @param sentinel The lowest possible value the tree set can contain.
     */
    public BTreeHashMap(int pageSize, T sentinel) {
        this.pageSize = pageSize;
        root = new Page(pageSize, true);
        add(sentinel, null);
    }

    /**
     * Checks to see if key is contained within this set
     * @param key Key to look for
     * @return True if the key exists
     */
    public boolean contains(T key) {
        return contains(root, key);
    }

    /**
     * Helper function for the contains, this is recursively called through the tree to
     * the external nodes looking for the Key
     * @param page Page to look at
     * @param key Key to check for
     * @return True if the key exists
     */
    private boolean contains(Page page, T key) {
        return (page.isExternal())  ?  page.contains(key) : contains(page.next(key), key);
    }


    //TODO: This should be combined with the code above.

    /**
     * Helper function for the get, this is similar (i.e. exactly the bloody same) as
     * the contains helper function.
     * @param page Page to look at
     * @param key Key for the value we're searching
     * @return Optional for the value.
     */
    private Optional<E> get(Page page, T key) {
        return (page.isExternal()) ? Optional.ofNullable((E)page.get(key)) : get(page.next(key), key);
    }




    /**
     * Gets the value
     * @param key
     * @return Optional of the value.
     */
    public Optional<E> get(T key) {
        return get(root, key);
    }

    /**
     * Adds Key Value pair  to the Map
     * @param key Key to add
     * @param value Value to add
     */
    public void add(T key, E value) {
        add(root, key, value);
        if(root.isFull()) {
            Page leftHalf = root;
            Page rightHalf = root.split();
            root = new Page(pageSize, false);
            root.add(leftHalf);
            root.add(rightHalf);
        }
    }

    /**
     * Helper function for the add method, adding and splitting pages to accomidate
     * the new Key
     * @param page Current Page we're adding too
     * @param key Key to add.
     * @param value value to add.
     */
    private void add(Page page, T key, E value) {
        if(page.isExternal()) {
            page.add(key, value);
        } else {
            Page next = page.next(key);
            add(next, key, value);
            if(next.isFull()) {
                page.add(next.split());
            }

            next.close();
        }
    }
}
