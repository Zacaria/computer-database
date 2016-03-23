package com.excilys.formation.computerdatabase.model;

import java.util.List;

public final class Page<T> {
  private final int total;
  private final List<T> elements;
  private final int currentOffset;
  private final int size;

  public Page(int offset, List<T> data, int total) {
    this.currentOffset = offset;
    this.elements = data;
    this.size = this.elements.size();
    this.total = total;
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

  public int getTotal() {
    return total;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + currentOffset;
    result = prime * result + ((elements == null) ? 0 : elements.hashCode());
    result = prime * result + size;
    result = prime * result + total;
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
    if (total != other.total) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Page [total=" + total + ", elements=" + elements + ", currentOffset=" + currentOffset
        + ", size=" + size + "]";
  }

}
