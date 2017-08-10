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

public class BTreeSet<T extends Comparable<T>> {
    private final BTreeHashMap<T, Object> map;

    public BTreeSet(int pageSize, T sentinel) {
        map = new BTreeHashMap<>(pageSize, sentinel);
    }

    /**
     * Checks to see if the key exists within the set
     * @param key Key to check
     * @return True if the key exists
     */
    public boolean contains(T key) {
        return  map.contains(key);
    }

    /**
     * Adds the key to the set
     * @param key Key to add.
     */
    public void add(T key) {
        map.add(key, null);
    }

}
