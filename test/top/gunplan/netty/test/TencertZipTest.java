/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import top.gunplan.netty.example.GunTencertZip;

public class TencertZipTest {

    @Test
    public void testzip() {
        Assert.assertEquals(GunTencertZip.doDecode("A[3|ABC]"), "AABCABCABC");
        Assert.assertEquals(GunTencertZip.doDecode("ABC"), "ABC");
    }
}
