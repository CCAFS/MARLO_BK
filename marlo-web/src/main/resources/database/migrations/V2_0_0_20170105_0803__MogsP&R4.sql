delete from ip_relationships where child_id not in (select id from ip_elements)