package org.alisle;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

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
 * Created by alisle on 6/15/17.
 */
public class PageHeader {
    /**
     * UUID of this page, used for caching.
     */
    private final UUID uuid;

    /**
     * Maximum number of items within a Page.
     */
    private final int maxPageSize;

    /**
     * Determines if this is an external node or not.
     */
    private final boolean external;

    /**
     * The current indexSize of the page.
     */
    private int currentSize;


    /**
     * Creates a new page header.
     * @param uuid UUID of the Page
     * @param maxPageSize The Maximum Page indexSize
     * @param external If this page is external
     */
    public PageHeader(UUID uuid, int maxPageSize, boolean external) {
        this.uuid = uuid;
        this.maxPageSize = maxPageSize;
        this.external = external;
    }

    /**
     * Returns the UUID of the Page.
     * @return UUID
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Returns the Max indexSize of the Page (in number of entries).
     * @return Maximum Number of entries
     */
    public int getMaxPageSize() {
        return maxPageSize;
    }

    /**
     * If the page is external
     * @return true if the page is external.
     */
    public boolean isExternal() {
        return external;
    }

    /**
     * Current indexSize of the page.
     * @return Current indexSize of the page.
     */
    public int getCurrentSize() {
        return currentSize;
    }


    /**
     * Sets the current indexSize of the page.
     * @param currentSize Current indexSize of the page.
     */
    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    /**
     * Increments the currnet indexSize
     */
    public void incrementCurrentSize() {
        this.currentSize += 1;
    }

}
