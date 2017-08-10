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
 * Created by alisle on 6/16/17.
 */

/**
 * Class used to store an entry within the B-Tree
 * If the entry is an external node then the value will be populated but not the next
 * If the entry is an internal node then the next will be populated but not the value.
 * @param <T> The Key Type
 * @param <E> The Value Type
 */
public class Entry<T extends Comparable<T>, E> {
    private final T key;
    private final Page next;
    private final E value;

    /**
     * Creates an entry with value. This would be an external entry
     * @param key Key to set.
     * @param value Value to set.
     */
    public Entry(T key, E value) {
        this.key = key;
        this.next = null;
        this.value = value;
    }

    /**
     * Creates an entry without a value. This would be an internal entry
     * @param key Key to use.
     * @param next The next page this points to.
     */
    public Entry(T key, Page next) {
        this.key = key;
        this.next = next;
        this.value = null;
    }


    @Override
    public String toString() {
        return "Entry{" +
                "key=" + key +
                ", next=" + next +
                ", value= " + value +
                '}';
    }


    /**
     * Gets the Key for this entry.
     * @return Key
     */
    public T getKey() {
        return key;
    }

    /**
     * Gets the next page for this entry. Internal Only.
     * @return Next Page
     */
    public Page getNext() {
        return next;
    }

    /**
     * Gets the next value for this entry. External Only.
     * @return Value
     */
    public E getValue() {
        return value;
    }
}