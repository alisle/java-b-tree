package org.alisle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

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
public class BTreeSetTest {
    private static int COUNT = 1000000;
    private int array[] = new int[COUNT];
    private Random rand = new Random(System.currentTimeMillis());

    @Before
    public void setup() {
        for(int x = 0; x < COUNT; x++) {
            array[x] = rand.nextInt();
        }
    }

    @Test
    public void testContains() {
        BTreeSet<Integer> set = new BTreeSet<>(10, -1);
        for(int x = 0; x < 1000; x++) {
            set.add(x);
        }

        Assert.assertTrue(set.contains(1));
        Assert.assertTrue(set.contains(99));
        Assert.assertTrue(set.contains(923));
    }

    @Test
    public void testRandomInsertion() {
        BTreeSet<Integer> set = new BTreeSet<>(1000, Integer.MIN_VALUE);
        Arrays.stream(array).forEach(x -> set.add(x));
        Arrays.stream(array).forEach(x -> Assert.assertTrue(set.contains(x)));
    }

}
