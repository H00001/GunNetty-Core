/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.example;

public final class GunTencertZip {

    public static String doDecode(String in) {
        return doDecode(in, 0, in.length()).str;
    }

    private static Help doDecode(String in, int sta, int end) {
        char[] cs = in.toCharArray();
        Help hp = new Help();
        StringBuilder base = new StringBuilder();
        for (int i = sta; i < end; i++) {
            if (cs[i] <= 'Z' && cs[i] >= 'A') {
                base.append(cs[i]);
            } else if (cs[i] == '[') {
                int r = i + 1;
                int loop = 0;
                while (cs[r] != '|') {
                    loop = loop * 10 + (cs[r] - '0');
                    r++;
                }
                Help next = doDecode(in, r + 1, in.length());
                base.append(String.valueOf(next.str).repeat(Math.max(0, loop)));
                i = next.pos;
            } else if (cs[i] == ']') {
                hp.pos = i;
                break;
            }
        }
        hp.str = base.toString();
        return hp;
    }

    public static class Help {
        String str;
        int pos;
    }

}

