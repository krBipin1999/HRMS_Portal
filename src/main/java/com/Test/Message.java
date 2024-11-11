package com.Test;

import java.sql.Timestamp;

public class Message {
	 private int senderId;
	    private int receiverId;
	    private String content;
	    private Timestamp timestamp;

	    public Message(int senderId, int receiverId, String content, Timestamp timestamp) {
	        this.senderId = senderId;
	        this.receiverId = receiverId;
	        this.content = content;
	        this.timestamp = timestamp;
	    }

		public int getSenderId() {
			return senderId;
		}

		public void setSenderId(int senderId) {
			this.senderId = senderId;
		}

		public int getReceiverId() {
			return receiverId;
		}

		public void setReceiverId(int receiverId) {
			this.receiverId = receiverId;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public Timestamp getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(Timestamp timestamp) {
			this.timestamp = timestamp;
		}

		@Override
		public String toString() {
			return "Message [senderId=" + senderId + ", receiverId=" + receiverId + ", content=" + content
					+ ", timestamp=" + timestamp + "]";
		}
	    
	    
	    
}
