package com.SpringBoot.main.pojo.admin;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class News {
	private Long id;
	private Long categoryId;//����id
	private NewsCategory newsCategory;//����ʵ��

	private String title;//���ű���
	private String abstrs;//����ժҪ
	private String tags;//���ű�ǩ
	private String photo;//���ŷ���ͼƬ
	private String author;//����
	private String content;//��������
	private Integer viewNumber = 0;//�����
	private Integer commentNumber = 0;//��������
	private Date createTime;//����ʱ��
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public NewsCategory getNewsCategory() {
		return newsCategory;
	}
	public void setNewsCategory(NewsCategory newsCategory) {
		this.newsCategory = newsCategory;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAbstrs() {
		return abstrs;
	}
	public void setAbstrs(String abstrs) {
		this.abstrs = abstrs;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getViewNumber() {
		return viewNumber;
	}
	public void setViewNumber(Integer viewNumber) {
		this.viewNumber = viewNumber;
	}
	public Integer getCommentNumber() {
		return commentNumber;
	}
	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "News [id=" + id + ", categoryId=" + categoryId + ", newsCategory=" + newsCategory + ", title=" + title
				+ ", abstrs=" + abstrs + ", tags=" + tags + ", photo=" + photo + ", author=" + author + ", content="
				+ content + ", viewNumber=" + viewNumber + ", commentNumber=" + commentNumber + ", createTime="
				+ createTime + "]";
	}
}
