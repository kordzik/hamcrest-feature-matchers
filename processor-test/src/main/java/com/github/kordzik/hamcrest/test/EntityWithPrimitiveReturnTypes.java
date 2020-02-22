package com.github.kordzik.hamcrest.test;

import com.github.kordzik.hamcrest.Features;

@Features
public class EntityWithPrimitiveReturnTypes {

    public byte getByte() {
        return 1;
    }

    public short getShort() {
        return 1;
    }

    public char getChar() {
        return 'a';
    }

    public int getInt() {
        return 1;
    }

    public long getLong() {
        return 1;
    }

    public boolean getBoolean() {
        return true;
    }

    public float getFloat() {
        return 1;
    }

    public double getDouble() {
        return 1;
    }
}
