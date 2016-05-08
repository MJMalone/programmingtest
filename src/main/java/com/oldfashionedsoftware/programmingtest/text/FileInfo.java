package com.oldfashionedsoftware.programmingtest.text;

import java.util.Objects;

public class FileInfo {
    private final String name;
    private final String contents;

    public FileInfo(final String name, final String contents) {
        super();
        this.name = name;
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public String getContents() {
        return contents;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contents == null) ? 0 : contents.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final FileInfo that = (FileInfo) obj;
        return Objects.equals(this.name, that.name) &&
        Objects.equals(this.contents, that.contents);
    }

}
