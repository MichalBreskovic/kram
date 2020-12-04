package kram.storage;

public class Option {
	private Long idOption;
	private String title;
	public Option(Long idOption, String title) {
		this.idOption = idOption;
		this.title = title;
	}
	public Option(String title) {
		this.title = title;
	}
	public Long getIdOption() {
		return idOption;
	}
	public void setIdOption(Long idOption) {
		this.idOption = idOption;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "Option [idOption=" + idOption + ", title=" + title + "]";
	}
	
	
}
