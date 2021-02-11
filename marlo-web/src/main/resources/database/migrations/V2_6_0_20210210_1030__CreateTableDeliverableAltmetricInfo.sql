create table deliverable_altmetric_info(
	id bigint not null auto_increment,
	deliverable_id bigint default null,
	phase_id bigint default null,
	title text,
	doi text,
	uri text,
	altmetric_jid text,
	journal text,
	authors text,
	type text,
	handle text,
	altmetric_id text,
	is_oa text,
	cited_by_posts int,
	cited_by_delicious int,
	cited_by_facebook_pages int,
	cited_by_blogs int,
	cited_by_forum_users int,
	cited_by_google_plus_users int,
	cited_by_linkedin_users int,
	cited_by_news_outlets int,
	cited_by_peer_review_sites int,
	cited_by_pinterest_users int,
	cited_by_policies int,
	cited_by_stack_exchange_resources int,
	cited_by_reddit_users int,
	cited_by_research_highlight_platforms int,
	cited_by_twitter_users int,
	cited_by_youtube_channels int,
	cited_by_weibo_users int,
	cited_by_wikipedia_pages int,
	last_updated timestamp,
	score text,
	url text,
	added_on timestamp,
	published_on timestamp,
	image_small text,
	image_medium text,
	image_large text,
	details_url text,
	last_sync timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
	is_active tinyint(1) NOT NULL DEFAULT '1',
	active_since timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	created_by bigint(20) DEFAULT NULL,
	modified_by bigint(20) DEFAULT NULL,
	modification_justification text,
	
	primary key (id),

	constraint dai_ibfk_1 foreign key (deliverable_id) references deliverables (id),
	constraint dai_ibfk_2 foreign key (phase_id) references phases (id),
	CONSTRAINT dai_ibfk_3 FOREIGN KEY (created_by) REFERENCES users (id),
	CONSTRAINT dai_ibfk_4 FOREIGN KEY (modified_by) REFERENCES users (id)
);