package com.oldfashionedsoftware.programmingtest.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NamedEntityTest {

    @Test
    public void testSimple() {
        final String original = "Test";
        final NamedEntity namedEntity = new NamedEntity(original);
        assertEquals(original, namedEntity.getName());
        assertEquals(original, namedEntity.getRegex());
    }

    @Test
    public void testWithSpace() {
        final String original = "With Space";
        final NamedEntity namedEntity = new NamedEntity(original);
        assertEquals(original, namedEntity.getName());
        assertEquals("With\\s+Space", namedEntity.getRegex());
    }

    @Test
    public void testWithMultipleSpaces() {
        final String original = "With  Several Different\tSpaces";
        final NamedEntity namedEntity = new NamedEntity(original);
        assertEquals(original, namedEntity.getName());
        assertEquals("With\\s+Several\\s+Different\\s+Spaces", namedEntity.getRegex());
    }

    @Test
    public void testHashCodeAndEquals() {
        final NamedEntity ne1 = new NamedEntity("Test1");
        final NamedEntity ne2 = new NamedEntity("Test1");
        final NamedEntity ne3 = new NamedEntity("Test2");

        // Hashcode
        assertEquals(ne1.hashCode(), ne2.hashCode());
        assertNotEquals(ne1.hashCode(), ne3.hashCode());
        assertNotEquals(ne2.hashCode(), ne3.hashCode());

        // Equals
        assertTrue(ne1.equals(ne2));
        assertTrue(ne2.equals(ne1));

        assertFalse(ne1.equals(ne3));
        assertFalse(ne3.equals(ne1));
        assertFalse(ne2.equals(ne3));
        assertFalse(ne3.equals(ne2));

        // Trivial cases
        assertTrue(ne1.equals(ne1));
        assertFalse(ne1.equals(null));
        assertFalse(ne1.equals("String"));
    }
}
