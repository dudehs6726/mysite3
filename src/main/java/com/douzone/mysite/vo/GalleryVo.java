package com.douzone.mysite.vo;

public class GalleryVo {
	
	private int no;
	private String comment;
	private String imageUrl;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@Override
	public String toString() {
		return "GallryVo [no=" + no + ", comment=" + comment + ", imageUrl=" + imageUrl + "]";
	}

}
