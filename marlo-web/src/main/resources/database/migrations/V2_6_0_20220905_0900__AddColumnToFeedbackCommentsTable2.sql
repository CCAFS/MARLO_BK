ALTER TABLE feedback_qa_comments ADD CONSTRAINT feedback_qa_comments_FK_7 FOREIGN KEY (status_id) REFERENCES feedback_statuses(id);