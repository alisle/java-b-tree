package org.alisle;

import org.junit.Assert;
import org.junit.Test;

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
 * Created by alisle on 8/10/17.
 */
public class EntryTest {
    @Test
    public void testExternalEntryGetters() {
        Entry<String, String> entry = new Entry<String, String>("Key", "Value");
        Assert.assertEquals(entry.getKey(), "Key");
        Assert.assertEquals(entry.getValue(), "Value");
        Assert.assertNull(entry.getNext());
    }

    @Test
    public void testInternalEntryGetters() {
        Page page = new Page(10, false);
        Entry<String, String> entry = new Entry<String, String>("Key", page);
        Assert.assertEquals(entry.getKey(), "Key");
        Assert.assertEquals(entry.getNext(), page);
        Assert.assertNull(entry.getValue());
    }
}
