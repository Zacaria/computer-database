package com.excilys.formation.computerdatabase.model;

import java.util.List;

public final class Page<T> {
  private final List<T> elements;
  private final int currentOffset;
  private final int size;

  public Page(int offset, List<T> data) {
    this.currentOffset = offset;
    this.elements = data;
    this.size = this.elements.size();
  }

  public List<T> getElements() {
    return elements;
  }

  public int getCurrentOffset() {
    return currentOffset;
  }

  public int getSize() {
    return size;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + currentOffset;
    result = prime * result + ((elements == null) ? 0 : elements.hashCode());
    result = prime * result + size;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Page)) {
      return false;
    }
    Page other = (Page) obj;
    if (currentOffset != other.currentOffset) {
      return false;
    }
    if (elements == null) {
      if (other.elements != null) {
        return false;
      }
    } else if (!elements.equals(other.elements)) {
      return false;
    }
    if (size != other.size) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Page [elements=" + elements + ", currentOffset=" + currentOffset
        + ", size=" + size + "]";
  }

}
