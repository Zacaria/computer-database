package com.excilys.formation.computerdatabase.model;

/**
 * This class is designed to transfer options to a select query.
 * @author Zacaria
 *
 */
public final class SelectOptions {
	private int offset = 0;
	private int range = 100;
	private int page = 1;
	//FIXME : OMG this is dirty !
	private String orderBy = "computer_id";
	private String asc = "asc";
	private String search = "%";
	
	private SelectOptions(){
		
	}
	
	public static Builder builder(){
		return new SelectOptions.Builder();
	}
	
	public static class Builder{
		private SelectOptions instance = new SelectOptions();
		
		public Builder range(int range){
			instance.range = range;
			return this;
		}
		
		public Builder page(int page){
			instance.page = page < 1 ? 1 : page;
			return this;
		}
		
		public Builder orderBy(String orderBy){
			if(orderBy != null){
				if (!ComputerFields.contains(orderBy)) {
					instance.orderBy = ComputerFields.ID.getValue();
				} else {
					instance.orderBy = orderBy;
				}
			}
			return this;
		}
		
		public Builder asc(boolean asc){
			instance.asc = asc ? "asc" : "desc";
			return this;
		}
		
		public Builder search(String search){
			if(search != null && !search.trim().isEmpty()){
				instance.search = "%" + search + "%";
			}
			return this;
		}
		
		public SelectOptions build(){
			instance.offset = (instance.page * instance.range) - instance.range;
			return instance;
		}
	}

	public int getOffset() {
		return offset;
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

	public String getSearch() {
		return search;
	}

	public int getPage() {
		return page;
	}
	
	public void setPage(int page){
		this.page = page;
	}

	@Override
	public String toString() {
		return "SelectOptions [offset=" + offset + ", range=" + range + ", page=" + page + ", orderBy=" + orderBy
				+ ", asc=" + asc + ", search=" + search + "]";
	}
}
