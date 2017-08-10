package org.alisle;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

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
public class PageTest {

    @Test
    public void testAddKeyInternalNode() {
        Page page = new Page( 0, false);
        try {
            page.add(1, null);
        } catch(RuntimeException ex) {
            return;
        }

        Assert.assertFalse("Shouldn't be able to add a key to an internal node", true);
    }

    @Test
    public void testAddPageExternalNode() {
        Page page = new Page(10, true);
        try {
            page.add(new Page(10, true));
        } catch(RuntimeException ex) {
            return;
        }

        Assert.assertFalse("Shouldn't be able to add a page to an external node", true);
    }

    @Test
    public void testAddKey() {
        Page page = new Page(10, true);

        page.add(1, null);
        page.add(4, null);
        page.add(3, null);
        page.add(0, null);
        page.add(9, null);
        page.add(2, null);
        page.add(6, null);
        page.add(5, null);
        page.add(7, null);
        page.add(8, null);

        Iterator iterator = page.keys().iterator();

        for(int num = 0; num < 10; num++) {
            int next = (int)iterator.next();
            Assert.assertEquals("They should of come out in order", next, num);
        }

    }

    @Test
    public void testFull() {
        Page page = new Page(3, true);
        page.add(0, null);
        page.add(1, null);
        page.add(2, null);

        Assert.assertTrue(page.isFull());

        try {
            page.add(3, null);
        } catch(RuntimeException ex) {
            return;
        }

        Assert.assertFalse("Shouldn't be able to add more to a full page", true);
    }

    @Test
    public void testContains() {
        Page page = new Page(5, true);
        for(int x = 0; x < 5; x++ ){
            page.add(x, null);
            Assert.assertTrue(page.contains(x));
        }

        Assert.assertFalse(page.contains(99));

    }

    @Test
    public void testExternalNext() {
        Page page = new Page(5, true);
        try {
            page.next(1);
        } catch (RuntimeException ex) {
            return;
        }

        Assert.assertFalse("Shouldn't be able get next from External page", true);
    }

    @Test
    public void testNext() {
        Page page = new Page(5, false);

        Page children[] = new Page[5];
        for(int x = 0; x < 10; x++) {
            if(x % 2 == 1) {
                children[x / 2] = new Page(5, true);
                children[x / 2].add(x, null);
            }
        }

        page.add(children[1]);
        page.add(children[0]);
        page.add(children[3]);
        page.add(children[2]);
        page.add(children[4]);

        Assert.assertEquals(page.next(2), children[0]);
        Assert.assertEquals(page.next(3), children[1]);

        Assert.assertEquals(page.next(100), children[4]);
    }

    @Test
    public void testSplitExternal() {
        Page page = new Page(4, true);
        for(int x = 0; x < 4; x++) {
            page.add(x, null);
        }

        Page top = page.split();

        Assert.assertEquals(2, top.size());
        Assert.assertEquals( 2, page.size());

        Iterator iterator = page.keys().iterator();
        Assert.assertEquals(0, iterator.next());
        Assert.assertEquals(1, iterator.next());

        Assert.assertFalse(iterator.hasNext());

        iterator = top.keys().iterator();
        Assert.assertEquals(2, iterator.next());
        Assert.assertEquals(3, iterator.next());

        Assert.assertFalse(iterator.hasNext());
    }


}
