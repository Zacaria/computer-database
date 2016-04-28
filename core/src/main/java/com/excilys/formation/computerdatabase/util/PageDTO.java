package com.excilys.formation.computerdatabase.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.excilys.formation.computerdatabase.util.DTOable;
import com.excilys.formation.computerdatabase.util.IDTO;
import com.excilys.formation.computerdatabase.model.Page;

/**
 * This holds other model classes.
 * This is made to hold some meta-datas. 
 * @author Zacaria
 *
 * @param <E>
 */
public class PageDTO<E> {
  private final int total;
  private final List<IDTO> elements;
  private final int currentOffset;
  private final int size;

  public PageDTO(Page<E> page, DTOable<E> dtoizer) {
    this.total = page.getTotal();
    this.currentOffset = page.getCurrentOffset();
    this.size = page.getSize();

    List<IDTO> elements = new ArrayList<>();
    Iterator<E> iterator = page.getElements().iterator();
    while (iterator.hasNext()) {
      elements.add(dtoizer.getDTO(iterator.next()));
    }

    this.elements = elements;
  }

  public List<IDTO> getElements() {
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
}
