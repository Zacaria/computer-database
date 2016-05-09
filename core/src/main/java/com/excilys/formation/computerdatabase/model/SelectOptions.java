package com.excilys.formation.computerdatabase.model;

/**
 * This class is designed to transfer options to a select query.
 * It is placed in the model package only because it's only role is to hold data.
 * @author Zacaria
 *
 */
public final class SelectOptions {
  private int offset = 0;
  private int range = 100;
  private int page = 1;
  private String orderBy = "id";
  private String asc = "asc";
  private String search = "%";

  public SelectOptions() {
  }

  public static Builder builder() {
    return new SelectOptions.Builder();
  }

  public static class Builder {
    private SelectOptions instance = new SelectOptions();

    public Builder range(int range) {
      instance.range = range;
      instance.computeOffset();
      return this;
    }

    public Builder page(int page) {
      instance.page = page < 1 ? 1 : page;
      return this;
    }

    public Builder orderBy(String orderBy) {
      if (orderBy != null) {
        instance.orderBy = orderBy;
      }
      return this;
    }

    public Builder asc(String asc) {
      if (asc != null && asc.equals("0")) {
        instance.asc = "desc";
      }
      return this;
    }

    public Builder search(String search) {
      if (search != null && !search.trim()
        .isEmpty()) {
        instance.search = "%" + search + "%";
      }
      return this;
    }

    public SelectOptions build() {
      return instance;
    }
  }

  public int getOffset() {
    return offset;
  }

  /**
   * Computes the offset with page and range
   */
  private void computeOffset() {
    this.offset = (this.page * this.range) - this.range;
    this.offset = this.offset < 0 ? 0 : this.offset;
  }

  public int getRange() {
    return range;
  }

  public String getOrderBy() {
    return orderBy;
  }

  public String getAsc() {
    return asc;
  }

  public boolean isAsc() {
    return asc == "desc" ? false : true;
  }

  public String getSearch() {
    return search;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
    this.computeOffset();
  }

  @Override
  public String toString() {
    return "SelectOptions [offset=" + offset + ", range=" + range + ", page=" + page + ", orderBy="
        + orderBy + ", asc=" + asc + ", search=" + search + "]";
  }
}
