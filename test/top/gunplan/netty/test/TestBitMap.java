/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import top.gunplan.netty.common.GunNettyBitMap;

public class TestBitMap {
    @Test
    public void doTest() {
        GunNettyBitMap bmp = GunNettyBitMap.getInstance(200);
        for (int i = 0; i < 100; i++) {
            int k = (int) (Math.random() * 100);
            bmp.put(k, true);
            Assertions.assertTrue(bmp.get(k));
        }
        for (int i = 0; i < 100; i++) {
            int k = (int) (Math.random() * 100);
            bmp.put(k, false);
            Assertions.assertFalse(bmp.get(k));
        }
    }
}
