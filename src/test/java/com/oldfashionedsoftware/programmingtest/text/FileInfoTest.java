package com.oldfashionedsoftware.programmingtest.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FileInfoTest {

    @Test
    public void testGetName() throws Exception {
        final FileInfo fileInfo = new FileInfo("name", "contents");
        assertEquals("name", fileInfo.getName());
    }

    @Test
    public void testGetContents() throws Exception {
        final FileInfo fileInfo = new FileInfo("name", "contents");
        assertEquals("contents", fileInfo.getContents());
    }

    @Test
    public void testHashCodeAndEquals() throws Exception {
        final FileInfo fileInfo1 = new FileInfo("name", "contents");
        final FileInfo fileInfo2 = new FileInfo("name", "contents");
        final FileInfo fileInfo3 = new FileInfo("name", "XXXXXXXX");
        final FileInfo fileInfo4 = new FileInfo("XXXX", "contents");
        final FileInfo fileInfo5 = new FileInfo(null, "contents");
        final FileInfo fileInfo6 = new FileInfo("name", null);

        // HashCode
        assertEquals(fileInfo1.hashCode(), fileInfo2.hashCode());
        assertNotEquals(fileInfo1.hashCode(), fileInfo3.hashCode());
        assertNotEquals(fileInfo1.hashCode(), fileInfo4.hashCode());
        assertNotEquals(fileInfo1.hashCode(), fileInfo5.hashCode());
        assertNotEquals(fileInfo1.hashCode(), fileInfo6.hashCode());
        assertNotEquals(fileInfo3.hashCode(), fileInfo4.hashCode());
        assertNotEquals(fileInfo5.hashCode(), fileInfo1.hashCode());
        assertNotEquals(fileInfo6.hashCode(), fileInfo1.hashCode());

        // Equals
        assertTrue(fileInfo1.equals(fileInfo2));
        assertTrue(fileInfo2.equals(fileInfo1));

        assertFalse(fileInfo1.equals(fileInfo3));
        assertFalse(fileInfo1.equals(fileInfo4));
        assertFalse(fileInfo3.equals(fileInfo1));
        assertFalse(fileInfo4.equals(fileInfo1));

        // Trivial cases
        assertTrue(fileInfo1.equals(fileInfo1));
        assertFalse(fileInfo1.equals(null));
        assertFalse(fileInfo1.equals("String"));
    }

}
