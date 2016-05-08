package com.oldfashionedsoftware.programmingtest.model;

import java.util.Objects;

public final class NamedEntity {
    private final String name;
    private final String regex;

    public NamedEntity(final String name) {
        this.name = name;

        // TODO : If the name contains regex-affecting characters then we should escape them.

        // Allow any amount of whitespace in multi-part names
        this.regex = name.replaceAll("\\s+", "\\\\s+");
    }

    public String getName() {
        return name;
    }

    public String getRegex() {
        return regex;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final NamedEntity that = (NamedEntity) obj;
        return Objects.equals(this.name, that.name);
    }

}
