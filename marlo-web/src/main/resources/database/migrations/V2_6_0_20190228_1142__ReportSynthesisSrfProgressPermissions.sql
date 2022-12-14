INSERT INTO permissions (permission, description, type)
VALUES ('crp:{0}:reportSynthesis:{1}:srfProgress', 'Can edit in Annual Report Synthesis Srf Progress', 3);
--
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p
WHERE 
r.acronym in( 'FPL','FPM','PMU')
AND
p.permission = 'crp:{0}:reportSynthesis:{1}:srfProgress';

