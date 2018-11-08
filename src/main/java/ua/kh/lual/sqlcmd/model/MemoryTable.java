package ua.kh.lual.sqlcmd.model;

import java.util.*;

public class MemoryTable {
    private String name;
    private Set<String> header;
    private List<List> content;

    public MemoryTable(String name, Set<String> header) {
        this.name = name;
        this.header = new LinkedHashSet<>(header);
        content = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public Set<String> getHeader() {
        return new  LinkedHashSet<>(header);
    }

    public List<List> getContent() {
        return new LinkedList<>(content);
    }

    public List<List> getFilteredContent(Map<String, Object> key) {
        List<List> data = new LinkedList<>();

        return null;
    }

}
