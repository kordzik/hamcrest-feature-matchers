package com.github.kordzik.hamcrest.mock;

import javax.annotation.Nonnull;
import javax.lang.model.element.Name;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public final class MockName implements Name {

    private final String name;

    public MockName(String name) {
        this.name = requireNonNull(name, "name");
    }

    public static Name name(String name) {
        return new MockName(name);
    }

    @Override
    public boolean contentEquals(CharSequence cs) {
        return name.contentEquals(cs);
    }

    @Override
    public int length() {
        return name.length();
    }

    @Override
    public char charAt(int index) {
        return name.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return name.subSequence(start, end);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MockName)) {
            return false;
        }
        MockName mockName = (MockName) o;
        return name.equals(mockName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    @Nonnull
    public String toString() {
        return name;
    }
}
