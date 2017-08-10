package org.alisle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

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
public class BTreeHashMapTest {
    private static final Logger log = LoggerFactory.getLogger(BTreeHashMapTest.class);
    private static int COUNT = 1000000;
    private int array[] = new int[COUNT];
    private Random rand = new Random(System.currentTimeMillis());

    @Before
    public void setup() {
        Set<Integer> set = new HashSet<>();
        while(set.size() < COUNT) {
            set.add(rand.nextInt());
        }

        int index = 0;
        for(Integer i : set) {
            array[index++] = i;
        }
    }

    @Test
    public void testContains() {
        BTreeHashMap<Integer, String> set = new BTreeHashMap<>(10, -1);
        for(int x = 0; x < 1000; x++) {
            set.add(x, "I like sauce");
        }

        Assert.assertTrue(set.contains(1));
        Assert.assertTrue(set.contains(99));
        Assert.assertTrue(set.contains(923));
    }

    @Test
    public void testRandomInsertion() {
        BTreeHashMap<Integer, String> set = new BTreeHashMap<>(1000, Integer.MIN_VALUE);
        log.info("Starting to insert");
        long start = System.currentTimeMillis();
        Arrays.stream(array).forEach(x -> set.add(x, "Random - " + x));
        long interval = System.currentTimeMillis() - start;
        log.info("Inserted " + COUNT + " records in " + interval / 1000 + " secs");

        Arrays.stream(array).forEach(x -> Assert.assertTrue(set.contains(x)));
        start = System.currentTimeMillis();
        Arrays.stream(array).forEach(x -> {
            Optional<String> returnValue = set.get(x);
            Assert.assertTrue("Asserting we have a return value", returnValue.isPresent());
            Assert.assertEquals("Testing that the return value is what we expected", "Random - " + x, returnValue.get());
        });
        interval = System.currentTimeMillis() - start;
        log.info("Retrieved " + COUNT + " records in " + interval / 1000 + " secs");



        Assert.assertFalse("Testing to see if contains is sane", set.contains(COUNT + 100));
        Assert.assertFalse("Testing to see if get is sane", set.get(COUNT + 100).isPresent());
    }

}
