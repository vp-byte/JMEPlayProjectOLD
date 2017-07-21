package com.jmeplay;

import org.junit.Test;

import java.util.UUID;

/**
 * Test class to generate UUID
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class GenerateUUID {

    /**
     * generate UUID
     */
    @Test
    public void generate() {
        System.out.println(UUID.randomUUID());
    }
}
