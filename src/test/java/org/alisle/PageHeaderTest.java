package org.alisle;

import org.junit.Assert;
import org.junit.Test;
import java.util.UUID;

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
 * Created by alisle on 6/17/17.
 */
public class PageHeaderTest {

    @Test
    public void testGetters() {
        UUID uuid = UUID.randomUUID();
        int maxPageSize = 1024;
        boolean external = true;

        PageHeader header = new PageHeader(uuid, maxPageSize, external);

        Assert.assertEquals(uuid, header.getUuid());
        Assert.assertEquals(maxPageSize, header.getMaxPageSize());
        Assert.assertEquals(external, header.isExternal());
    }

    @Test
    public void testIncrement() {
        UUID uuid = UUID.randomUUID();
        int maxPageSize = 1024;
        boolean external = true;

        PageHeader header = new PageHeader(uuid, maxPageSize, external);

        Assert.assertEquals(0, header.getCurrentSize());
        header.incrementCurrentSize();

        Assert.assertEquals(1, header.getCurrentSize());
    }

    @Test
    public void testSetCurrentSize() {
        UUID uuid = UUID.randomUUID();
        int maxPageSize = 1024;
        boolean external = true;

        PageHeader header = new PageHeader(uuid, maxPageSize, external);

        Assert.assertEquals(0, header.getCurrentSize());
        header.setCurrentSize(99);

        Assert.assertEquals(99, header.getCurrentSize());
    }
}
