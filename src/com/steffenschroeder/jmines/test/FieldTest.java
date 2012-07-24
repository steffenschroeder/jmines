package com.steffenschroeder.jmines.test;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.steffenschroeder.jmines.Field;
import com.steffenschroeder.jmines.MineField;
import com.steffenschroeder.jmines.NoMineField;

public class FieldTest {

    private Field mineField;
    private Field noneMineField;

    @Before
    public void init() {
        mineField = new MineField();
        noneMineField = new NoMineField();
    }

    @Test
    public void testGetNumerOfMinesAround() {
        List<Field> fields = new ArrayList<Field>();
        assertEquals(0, mineField.getNumerOfMinesAround());
        fields.add(new NoMineField());
        mineField.setNeighborhood(fields);
        assertEquals(0, mineField.getNumerOfMinesAround());
        
        fields.add(new MineField());
        mineField.setNeighborhood(fields);
        assertEquals(1, mineField.getNumerOfMinesAround());
        
        fields.add(new MineField());
        mineField.setNeighborhood(fields);
        assertEquals(2, mineField.getNumerOfMinesAround());

        fields.add(new NoMineField());
        mineField.setNeighborhood(fields);
        assertEquals(2, mineField.getNumerOfMinesAround());
    }

    @Test
    public void testIsMine() {
        assertTrue(mineField.isMine());
        assertEquals(false, noneMineField.isMine());
    }

    @Test
    public void testIsOpended() {
        assertEquals(false, noneMineField.isOpen());
        noneMineField.open();
        assertEquals(true, noneMineField.isOpen());

        assertEquals(false, mineField.isOpen());
        mineField.open();
        assertEquals(true, mineField.isOpen());

    }

    @Test
    public void testRecureTouch() {
        Field a = new NoMineField();
        Field b = new NoMineField();

        List<Field> fields = new LinkedList<Field>();
        fields.add(a);
        fields.add(b);

        noneMineField.setNeighborhood(fields);

        noneMineField.open();
        
        assertEquals(true, a.isOpen());
        assertEquals(true, b.isOpen());
    }
    
    @Test
    public void testDontOpenFlaggedFields() {
        Field a = new NoMineField();
        Field b = new NoMineField();
        b.toggleFlag();

        List<Field> fields = new LinkedList<Field>();
        fields.add(a);
        fields.add(b);

        noneMineField.setNeighborhood(fields);

        noneMineField.open();
        assertEquals(true, a.isOpen());
        assertEquals(false, b.isOpen());
    }

    @Test
    public void testTouch() {

    	assertThatNeighborsAreNotOpenRecursively(mineField);
    	assertThatNeighborsAreNotOpenRecursively(noneMineField);
    }
    
    public void assertThatNeighborsAreNotOpenRecursively(Field field)
    {
        Field mine = new MineField();
        Field nonMine = new NoMineField();
        List<Field> neighbors = new LinkedList<Field>();
        neighbors.add(mine);
        neighbors.add(nonMine);

        mineField.setNeighborhood(neighbors);
        mineField.open();
        assertTrue(mineField.isOpen());
        
        assertFalse(mine.isOpen());
        assertFalse(nonMine.isOpen());
    }
    
    @Test public void notFlaggedInitially()
    {
    	assertEquals(false, noneMineField.isFlagged());
    	assertEquals(false, mineField.isFlagged());
    }
    
    @Test public void flag()
    {
    	noneMineField.toggleFlag();
    	mineField.toggleFlag();
    	
    	assertEquals(true, noneMineField.isFlagged());
    	assertEquals(true, mineField.isFlagged());
    }
    
    @Test public void unflag()
    {
    	noneMineField.toggleFlag();
    	noneMineField.toggleFlag();
    	assertEquals(false, noneMineField.isFlagged());
    }

}
