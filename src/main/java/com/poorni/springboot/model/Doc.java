package com.poorni.springboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "docs")
public class Doc {
		@Id
		@GeneratedValue(generator = "uuid")
		@GenericGenerator(name = "uuid", strategy = "uuid2")
		private String id;

		private String docName;

		private String docType;

		@Lob
		private byte[] data;

		public Doc() {

		}

		public Doc(String docName, String docType, byte[] data) {
			this.docName = docName;
			this.docType = docType;
			this.data = data;
		}

		public String getId() {
			return id;
		}

		public String getFileName() {
			return docName;
		}

		public String getFileType() {
			return docType;
		}

		public byte[] getData() {
			return data;
		}

		public void setId(String id) {
			this.id = id;
		}

		public void setFileName(String docName) {
			this.docName = docName;
		}

		public void setFileType(String docType) {
			this.docType = docType;
		}

		public void setData(byte[] data) {
			this.data = data;
		}
}
